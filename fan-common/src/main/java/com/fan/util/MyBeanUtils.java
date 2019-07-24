package com.fan.util;

import org.apache.commons.beanutils.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;


public class MyBeanUtils extends PropertyUtilsBean {

  private static void convert(Object dest, Object orig) throws
      IllegalAccessException, InvocationTargetException {

      // Validate existence of the specified beans
      if (dest == null) {
          throw new IllegalArgumentException
              ("No destination bean specified");
      }
      if (orig == null) {
          throw new IllegalArgumentException("No origin bean specified");
      }

      // Copy the config, converting as necessary
      if (orig instanceof DynaBean) {
          DynaProperty origDescriptors[] =
              ( (DynaBean) orig).getDynaClass().getDynaProperties();
          for (int i = 0; i < origDescriptors.length; i++) {
              String name = origDescriptors[i].getName();
              if (PropertyUtils.isWriteable(dest, name)) {
                  Object value = ( (DynaBean) orig).get(name);
                  try {
                	  getInstance().setSimpleProperty(dest, name, value);
                  }
                  catch (Exception e) {
                      ; // Should not happen
                  }

              }
          }
      }
      else if (orig instanceof Map) {
          Iterator names = ( (Map) orig).keySet().iterator();
          while (names.hasNext()) {
              String name = (String) names.next();
              if (PropertyUtils.isWriteable(dest, name)) {
                  Object value = ( (Map) orig).get(name);
                  try {
                	  getInstance().setSimpleProperty(dest, name, value);
                  }
                  catch (Exception e) {
                      ; // Should not happen
                  }

              }
          }
      }
      else
      /* if (orig is a standard JavaBean) */
      {
          PropertyDescriptor origDescriptors[] =
              PropertyUtils.getPropertyDescriptors(orig);
          for (int i = 0; i < origDescriptors.length; i++) {
              String name = origDescriptors[i].getName();
//              String type = origDescriptors[i].getPropertyType().toString();
              if ("class".equals(name)) {
                  continue; // No point in trying to set an object's class
              }
              if (PropertyUtils.isReadable(orig, name) &&
                  PropertyUtils.isWriteable(dest, name)) {
                  try {
                      Object value = PropertyUtils.getSimpleProperty(orig, name);
                      getInstance().setSimpleProperty(dest, name, value);
                  }
                  catch (IllegalArgumentException ie) {
                      ; // Should not happen
                  }
                  catch (Exception e) {
                      ; // Should not happen
                  }

              }
          }
      }

  }


  /**
	 * 对象拷贝
	 * 数据对象空值不拷贝到目标对象
	 *
	 * @throws NoSuchMethodException
	 * copy
	 */
  public static void copyBeanNotNull2Bean(Object databean,Object tobean)throws Exception
  {
	  PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(databean);
      for (int i = 0; i < origDescriptors.length; i++) {
          String name = origDescriptors[i].getName();
//          String type = origDescriptors[i].getPropertyType().toString();
          if ("class".equals(name)) {
              continue; // No point in trying to set an object's class
          }
          if (PropertyUtils.isReadable(databean, name) &&PropertyUtils.isWriteable(tobean, name)) {
              try {
                  Object value = PropertyUtils.getSimpleProperty(databean, name);
                  if(value!=null){
                	  getInstance().setSimpleProperty(tobean, name, value);
                  }
              }
              catch (IllegalArgumentException ie) {
                  ; // Should not happen
              }
              catch (Exception e) {
                  ; // Should not happen
              }

          }
      }
  }


  /**
   * 把orig和dest相同属性的value复制到dest中
   * @param dest
   * @param orig
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   */
  public static void copyBean2Bean(Object dest, Object orig) throws Exception {
      convert(dest, orig);
  }

  public static void copyBean2Map(Map map, Object bean){
	PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(bean);
	for (int i =0;i<pds.length;i++)
	{
		PropertyDescriptor pd = pds[i];
		String propname = pd.getName();
		try {
			Object propvalue = PropertyUtils.getSimpleProperty(bean,propname);
			map.put(propname, propvalue);
		} catch (IllegalAccessException e) {
			//e.printStackTrace();
		} catch (InvocationTargetException e) {
			//e.printStackTrace();
		} catch (NoSuchMethodException e) {
			//e.printStackTrace();
		}
	}
  }

    public static void copyBean2Map(Map map, Object bean,String pattern){
        PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(bean);
        for (int i =0;i<pds.length;i++)
        {
            PropertyDescriptor pd = pds[i];
            String propname = pd.getName();
            try {
                Object propvalue = PropertyUtils.getSimpleProperty(bean,propname);
                map.put(propname, propvalue);
                try {
                    Class clazz = PropertyUtils.getPropertyType(bean, propname);
                    if (null == clazz) {
                        continue;
                    }
                    String className = clazz.getName();
                    if (propvalue == null || "".equals(propvalue)) {
                        continue;
                    }else{
                        if (className.equalsIgnoreCase("java.sql.Timestamp")) {
                            Timestamp timestamp = (Timestamp)propvalue;
                            String dateStr = DateFormatUtils.format(timestamp.getTime(),pattern);
                            map.put(propname, dateStr);
                        }else if(className.equalsIgnoreCase("java.util.Date")){
                            Date timestamp = (Date)propvalue;
                            String dateStr = DateFormatUtils.format(timestamp.getTime(),pattern);
                            map.put(propname, dateStr);
                        }
                    }
                }
                catch (NoSuchMethodException e) {
                    continue;
                }
            } catch (IllegalAccessException e) {
                //e.printStackTrace();
            } catch (InvocationTargetException e) {
                //e.printStackTrace();
            } catch (NoSuchMethodException e) {
                //e.printStackTrace();
            }
        }
    }

  /**
   * 将Map内的key与Bean中属性相同的内容复制到BEAN中
   * @param bean Object
   * @param properties Map
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   */
  public static void copyMap2Bean(Object bean, Map properties) throws
      IllegalAccessException, InvocationTargetException {
      // Do nothing unless both arguments have been specified
      if ( (bean == null) || (properties == null)) {
          return;
      }

      // Loop through the property name/value pairs to be set
      Iterator names = properties.keySet().iterator();
      while (names.hasNext()) {
          String name = (String) names.next();
          // Identify the property name and value(s) to be assigned
          if (name == null) {
              continue;
          }
          Object value = properties.get(name);
          try {
              Class clazz = PropertyUtils.getPropertyType(bean, name);
              if (null == clazz) {
                  continue;
              }
              String className = clazz.getName();
              if (className.equalsIgnoreCase("java.sql.Timestamp")) {
                  if (value == null || value.equals("")) {
                      continue;
                  }
              }
              getInstance().setSimpleProperty(bean, name, value);
          }
          catch (NoSuchMethodException e) {
              continue;
          }
      }
  }


  /**
   * 自动转Map key值大写
   * 将Map内的key与Bean中属性相同的内容复制到BEAN中
   * @param bean Object
   * @param properties Map
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   */
  public static void copyMap2Bean_Nobig(Object bean, Map properties) throws
      IllegalAccessException, InvocationTargetException {
      // Do nothing unless both arguments have been specified
      if ( (bean == null) || (properties == null)) {
          return;
      }
      // Loop through the property name/value pairs to be set
      Iterator names = properties.keySet().iterator();
      while (names.hasNext()) {
          String name = (String) names.next();
          // Identify the property name and value(s) to be assigned
          if (name == null) {
              continue;
          }
          Object value = properties.get(name);
          // 命名应该大小写应该敏感(否则取不到对象的属性)
          //name = name.toLowerCase();
          try {
        	  if (value == null) {	// 不光Date类型，好多类型在null时会出错
                  continue;	// 如果为null不用设 (对象如果有特殊初始值也可以保留？)
              }
              Class clazz = PropertyUtils.getPropertyType(bean, name);
              if (null == clazz) {	// 在bean中这个属性不存在
                  continue;
              }
              String className = clazz.getName();
              // 临时对策（如果不处理默认的类型转换时会出错）
              if (className.equalsIgnoreCase("java.util.Date")) {
                  value = new Date(((Timestamp)value).getTime());// wait to do：貌似有时区问题, 待进一步确认
              }
//              if (className.equalsIgnoreCase("java.sql.Timestamp")) {
//                  if (value == null || value.equals("")) {
//                      continue;
//                  }
//              }
              getInstance().setSimpleProperty(bean, name, value);
          }
          catch (NoSuchMethodException e) {
              continue;
          }
      }
  }

  /**
   * Map内的key与Bean中属性相同的内容复制到BEAN中
   * 对于存在空值的取默认值
   * @param bean Object
   * @param properties Map
   * @param defaultValue String
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   */
  public static void copyMap2Bean(Object bean, Map properties, String defaultValue) throws
      IllegalAccessException, InvocationTargetException {
      // Do nothing unless both arguments have been specified
      if ( (bean == null) || (properties == null)) {
          return;
      }
      // Loop through the property name/value pairs to be set
      Iterator names = properties.keySet().iterator();
      while (names.hasNext()) {
          String name = (String) names.next();
          // Identify the property name and value(s) to be assigned
          if (name == null) {
              continue;
          }
          Object value = properties.get(name);
          try {
              Class clazz = PropertyUtils.getPropertyType(bean, name);
              if (null == clazz) {
                  continue;
              }
              String className = clazz.getName();
              if (className.equalsIgnoreCase("java.sql.Timestamp")) {
                  if (value == null || value.equals("")) {
                      continue;
                  }
              }
              if (className.equalsIgnoreCase("java.lang.String")) {
                  if (value == null) {
                      value = defaultValue;
                  }
              }
              getInstance().setSimpleProperty(bean, name, value);
          }
          catch (NoSuchMethodException e) {
              continue;
          }
      }
  }


  public static void copyMapString2Bean(Object beanObj,Map<String,Object> dataMap) throws InvocationTargetException, IllegalAccessException {
      if(beanObj == null){
          return ;
      }
      if(dataMap==null || dataMap.isEmpty()){
          return ;
      }
      ConvertUtils.register(new Converter() {

          public Object convert(Class type, Object value) {
              Date date = null;
              if(value != null){
                  try {
                      //日期格式 yyyy-MM-dd HH:mm:ss
                      date = DateUtils.parseDate(value.toString(), "yyyy-MM-dd HH:mm:ss");
                  } catch (ParseException e) {
                      try {
                          //日期格式 yyyy/MM/dd HH:mm:ss
                          date = DateUtils.parseDate(value.toString(), "yyyy/MM/dd HH:mm:ss");
                      } catch (ParseException e1) {
                          try {
                              //日期格式 yyyyMMddhhmmss
                              date = DateUtils.parseDate(value.toString(), "yyyyMMddHHmmss");
                          } catch (ParseException e2) {

                          }
                      }
                  }
              }
              return date;
          }
      }, Date.class);
      BeanUtils.populate(beanObj, dataMap);
  }

  public MyBeanUtils() {
    super();
  }
}
