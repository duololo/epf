package com.zfer.kit;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;

/**
 * JavaBean and map converter.
 * 
 * 
 */
public final class BeanKit {
	
	private Map<String,String> beanNameMap = null;
	
	//-----------------------------------------------------------------------------------
	/**
	 * Converts a map to a JavaBean.
	 * 
	 * @param type type to convert
	 * @param map map to convert
	 * @return JavaBean converted
	 * @throws IntrospectionException failed to get class fields
	 * @throws IllegalAccessException failed to instant JavaBean
	 * @throws InstantiationException failed to instant JavaBean
	 * @throws InvocationTargetException failed to call setters
	 */
	public static final Object toBean(Class<?> type, Map<String, ? extends Object> map) 
			throws IntrospectionException, IllegalAccessException,	InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		Object obj = type.newInstance();
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i< propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (map.containsKey(propertyName)) {
				Object value = map.get(propertyName);
				Object[] args = new Object[1];
				args[0] = value;
				descriptor.getWriteMethod().invoke(obj, args);
			}
		}
		return obj;
	}
	
	/**
	 * Converts a JavaBean to a map.
	 * 
	 * @param bean JavaBean to convert
	 * @return map converted
	 * @throws IntrospectionException failed to get class fields
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException failed to instant JavaBean
	 * @throws InvocationTargetException failed to call setters
	 */
	public static final Map<String, Object> toMap(Object bean) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i< propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}
	
	//-----------------------------------------------------------------------------------
	
	/**
	 * 把一个Map循环放成 对象simpleName.属性,对象的value
	 * @param map
	 * @param superObjStr
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @throws Exception
	 */
	private void beanMapToDotLoopMap(Map<String, Object> map,String superObjStr) throws IntrospectionException, IllegalAccessException, InvocationTargetException{
		for(Map.Entry<String, Object> entry: map.entrySet()){
			if(null != entry.getValue() && entry.getValue().toString().contains("@")){
				beanMapToDotLoopMap(toMap(entry.getValue()),entry.getKey());//entry的对象然后转成map
			}else if("class".equals(entry.getKey())){
				//nothing
			}else{
				beanNameMap.put(superObjStr+"."+entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
	}
	
	/**
	 * 把一个List<Bean>生成 List<带.的Map> 
	 * 比如Form:id name DataSet(dataSet:id name) 则生成{"form.id":"","form.name":"","dataSet.id":"","dataSet.name":""}
	 * @param list
	 * @param superObjStr
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @throws IllegalArgumentException 
	 * @throws Exception
	 */
	private <T> List<Map<String,String>> getBeanListToListMap(List<T> list,String superObjStr) throws IllegalArgumentException, IntrospectionException, IllegalAccessException, InvocationTargetException{
		List<Map<String,String>> rsList = new ArrayList<Map<String,String>>();
		for(T t : list){
			Map<String, Object> map = toMap(t);//PropertyUtils.describe(t);把bean生成map
			beanNameMap = new HashMap<String,String>();
			beanMapToDotLoopMap(map,superObjStr);
			rsList.add(beanNameMap);
		}
		return rsList;
	}
	
	//-----------------------------------------------------------------------------------
	
	/**
	 * 把一个List<Bean>生成 List<带.的Map> 转成jsonString
	 * 比如Form:id name DataSet(dataSet:id name) 则生成{"form.id":"","form.name":"","dataSet.id":"","dataSet.name":""}
	 * @param list
	 * @param superObjStr
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @throws IllegalArgumentException 
	 * @throws Exception
	 */
	public <T> String getBeanListToListMapJsonStr(List<T> list,String superObjStr) throws IllegalArgumentException, IntrospectionException, IllegalAccessException, InvocationTargetException{
		List<Map<String,String>> rsList = getBeanListToListMap(list,superObjStr);
		String str = JSON.toJSONString(rsList);
		return str;
	}
	
	/**
	 * 初始化bean属性初始默认值
	 * WidgetSet view = new WidgetSet();
	   view = initBean.initBeanValue(view,WidgetSet.class);
	   System.out.println(view);
	 * 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws InstantiationException 
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T initBeanValue(Object bean,Class<?> beanClass) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, SecurityException, NoSuchFieldException {
		Method[] methods = beanClass.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			
			//getList方法 设置值 List中添加一个对象
			if("java.util.List".equals(method.getReturnType().getName()) 
					&& methodName.startsWith("get")){
				String attrName = methodName.substring(3);
				Field field = beanClass.getDeclaredField(StrKit.firstCharToLowerCase(attrName));
				
				if(List.class.isAssignableFrom(field.getType())){ //如果字段是List类型
		            List<Object> arraylist = new ArrayList<Object>();
		            Type type = field.getGenericType();
		            if(type instanceof ParameterizedType){//这样判断type是不是参数化类型。
		                Class<?> clazz = (Class<?>)((ParameterizedType) type).getActualTypeArguments()[0];//获取类型的类型参数类型。
		                Object obj = clazz.newInstance();
		                obj = initBeanValue(obj,obj.getClass());//对这个List中对象进行初始化
		                arraylist.add(obj);
		            }
		            field.setAccessible(true);
		            field.set(bean, arraylist);
		        }
			}
			
			Class<?>[] types = method.getParameterTypes();
			if (types.length != 1)						// only one parameter
				continue;
			
			if (!methodName.startsWith("set"))	// only setter method
				continue;
			
			
			if(JavaTypeKit.judgeBasicType(types[0])){ //判断是否基本类型
				method.invoke(bean, JavaTypeKit.getBasicTypeInitValue(types[0]));
			}else{
				Object subBean = types[0].newInstance();
				subBean = initBeanValue(subBean,subBean.getClass());
				if (subBean != null) {
					method.invoke(bean, subBean);
				}
			}
		}
		return (T)bean;
	}
	
	
	public static void main(String[] args) throws Exception{}
}
