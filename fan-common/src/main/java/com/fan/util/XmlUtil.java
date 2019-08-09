package com.fan.util;

import com.google.common.collect.Maps;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
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
     * @param obj
     * @return
     * @throws JAXBException
     */
    public static String beanToXml(Object obj) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, ConstantFan.CHARSET);
        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);
        return writer.toString();
    }

    @SuppressWarnings("unchecked")
    public static <T> T xmlTobean(Class<T> c, String xml) {
        T t;
        StringReader sr = null;
        try {
            JAXBContext context = JAXBContext.newInstance();
            Unmarshaller unmarshaller = context.createUnmarshaller();
            sr = new StringReader(xml);
            t = (T) unmarshaller.unmarshal(sr);
        } catch (Exception e) {
            return null;
        } finally {
            sr.close();
        }
        return t;
    }

    /**
     * 21      *  接收request对象，读取xml内容，转换成map对象
     * 22      * @param request
     * 23      * @return
     * 24
     */
    public static Map<String, String> parseXmlToMap(HttpServletRequest request) {
        InputStream ins = null;
        try {
            Map<String, String> map = Maps.newHashMap();
            SAXReader reader = new SAXReader();
            ins = request.getInputStream();
            Document doc = reader.read(ins);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }
            return map;
        } catch (Exception e) {
            log.error("{}", e);
        } finally {
            IOUtils.closeQuietly(ins);
        }
        return null;
    }

    /**
     * 将消息转换成xml格式的字符串
     *
     * @param o     各种信息类，如文本信息类，图片信息类，音频信息类等。（都是WxMessage的子类）
     * @param c 用来确定到底是哪一种子类
     * @return
     */
    public static String beanToXml(Object o, Class c) {
        XStream xstream = new XStream();
        xstream.alias(ConstantFan.XML, c);
        return xstream.toXML(o);
    }
}

