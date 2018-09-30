package com.fan.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * xml转换工具
 * @author Achievo
 *
 */
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
		marshaller.setProperty(Marshaller.JAXB_ENCODING, ConstantAiot.CHARSET);
		StringWriter writer = new StringWriter();
		marshaller.marshal(obj, writer);
		return writer.toString();
	}

	@SuppressWarnings("unchecked")
	public static <T> T xmlTobean(Class<T> c, String xml) {
		T t = null;
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
}
