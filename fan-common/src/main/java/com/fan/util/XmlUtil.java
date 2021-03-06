package com.fan.util;

import com.google.common.collect.Maps;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * xml转换工具
 *
 * @author fan
 */
@Slf4j
public class XmlUtil {
    /**
     * 对象转xml
     *
     * @return
     * @throws JAXBException
     */
    public static String beanToXml(Object o) {
        StringWriter writer = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(o.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, ConstantFan.CHARSET);
            marshaller.marshal(o, writer);
            return writer.toString();
        } catch (JAXBException e) {
            log.error("beanToXml error:{}", e);
            return null;
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

    public static <T> T xmlToBean(String xml) {
        T t;
        StringReader sr = null;
        try {
            JAXBContext context = JAXBContext.newInstance();
            Unmarshaller unmarshaller = context.createUnmarshaller();
            sr = new StringReader(xml);
            t = (T) unmarshaller.unmarshal(sr);
        } catch (Exception e) {
            log.error("xmlToBean error:{}", e);
            return null;
        } finally {
            sr.close();
        }
        return t;
    }

    /**
     * 接收request对象，读取xml内容，转换成map对象
     *
     * @param request
     * @return
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) {
        InputStream ins = null;
        Map<String, String> map = Maps.newHashMap();
        try {
            SAXReader reader = new SAXReader();
            ins = request.getInputStream();
            Document doc = reader.read(ins);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }
        } catch (Exception e) {
            log.error("parseXmlToMap error:{}", e);
        } finally {
            IOUtils.closeQuietly(ins);
        }
        return map;
    }

    /**
     * 将xml字符串解析为map集合，兼容性高
     *
     * @param xml
     * @return
     */
    public static Map<String, String> xmlToMap(String xml) {
        Map<String, String> map = Maps.newHashMap();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            InputStream stream = new ByteArrayInputStream(xml.getBytes(ConstantFan.CHARSET));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                org.w3c.dom.Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    map.put(element.getNodeName(), element.getTextContent());
                }
            }
        } catch (Exception e) {
            log.error("parseXmlToMap error:{}", e);
        }
        return map;
    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) {
        StringWriter writer = new StringWriter();
        try {
            org.w3c.dom.Document document = WeChatUtil.newDocument();
            org.w3c.dom.Element root = document.createElement(ConstantFan.XML);
            document.appendChild(root);
            for (String key : data.keySet()) {
                String value = data.get(key);
                if (value == null) {
                    value = "";
                }
                value = value.trim();
                org.w3c.dom.Element filed = document.createElement(key);
                filed.appendChild(document.createTextNode(value));
                root.appendChild(filed);
            }
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, ConstantFan.CHARSET);
            transformer.setOutputProperty(OutputKeys.INDENT, ConstantFan.YES);
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            //TODO replaceAll("\n|\r", "");
            String output = writer.getBuffer().toString();
            return output;
        } catch (Exception e) {
            log.error("mapToXml error:{}", e);
            return null;
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }


    /**
     * 将消息转换成xml格式的字符串
     *
     * @param o 各种信息类，如文本信息类，图片信息类，音频信息类等。（都是WxMessage的子类）
     * @param c 用来确定到底是哪一种子类
     * @return
     */
    public static String beanToXml(Object o, Class c) {
        XStream xstream = new XStream();
        xstream.alias(ConstantFan.XML, c);
        return xstream.toXML(o);
    }

    /**
     * 将字符串格式的xml内容转化为对象
     * 注意：该方法存在一个不可避免风险，即：当微信官方文档反馈字段增加或改变时，该方法将不能映射进pojo里。
     * 因为本地pojo(OrderResult)可能没有做对应调整。
     *
     * @param xml
     * @return
     */
    public static <T> T xmlToBean(Class<T> c, String xml) {
        //将返回结果的<xml>标签替换为返回结果类
//        xml = xml.replaceAll("xml", "OrderResult");
        XStream xstream = new XStream();
        xstream.alias(ConstantFan.XML, c);
        return (T) xstream.fromXML(xml, c);
    }
}

