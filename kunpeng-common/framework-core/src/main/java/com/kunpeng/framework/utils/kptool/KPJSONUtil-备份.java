package com.kunpeng.framework.utils.kptool;//package com.kunpeng.framework.utils.kptool;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson2.JSONObject;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.kunpeng.framework.exception.KPServiceException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//
///* *
// * @Author 李鹏
// * @Description //
// * @Date 2020/5/31 0:26
// * @Param
// * @return
// **/
//public final class KPJSONUtil {
//
//	private static Logger log = LoggerFactory.getLogger(KPJSONUtil.class);
//
//	private KPJSONUtil(){}
//
//	/**
//	 * @Author lipeng
//	 * @Description json 转 Java对象
//	 * @Date 2020/9/10 12:16
//	 * @Param [json, clazz]
//	 * @return T
//	 **/
//	// TODO: 2024/11/28 进行转换 去空
//	public static final <T> T toJavaObject(JSON json, Class<T> clazz){
//		try {
//			return JSONObject.toJavaObject(json, clazz);
//		} catch (Exception e) {
//			log.error("json转Java对象失败：" + e.getMessage());
//			throw new KPServiceException("数据转换异常！");
//		}
//	}
//
//
////	public static final <T> T toJavaObjectNotNull(JSON json, Class<T> clazz){
////		try {
////			Set<String> values = ((JSONObject) json).keySet();
////			Iterator iterator = values.iterator();
////			while (iterator.hasNext()){
////				String key = (String) iterator.next();
////				if (KPStringUtil.isEmpty(((JSONObject) json).getString(key))){
////					iterator.remove();
////				}
////
////			}
////			return JSONObject.toJavaObject(json, clazz);
////		} catch (Exception e) {
////			log.error("json转Java对象失败：" + e.getMessage());
////			throw new KPServiceException("数据转换异常！");
////		}
////	}
//
//
//	/**
//	 * @Author lipeng
//	 * @Description json 转 Javalist
//	 * @Date 2021/10/13 16:54
//	 * @param json
//	 * @param clazz
//	 * @return java.util.List<T>
//	 **/
//	public static final <T> List<T> toJavaObjectList(JSON json, Class<T> clazz){
//		return JSON.parseArray(json.toJSONString(), clazz);
//	}
//
//	public static final <T> List<T> toJavaObjectList(String jsonString, Class<T> clazz){
//		return JSON.parseArray(jsonString, clazz);
//	}
//
//
//	/**
//	 * @Author lipeng
//	 * @Description 吧一个list 转成 另一个list
//	 * @Date 2021/5/10 16:04
//	 * @param list
//	 * @param clazz
//	 * @return java.util.List<T>
//	 **/
//	public static <T> List<T> toJavaObjectList(List<?> list,Class<T> clazz){
//		String oldOb = JSON.toJSONString(list);
//		return JSON.parseArray(oldOb, clazz);
//	}
//
//	/**
//	 * @Author lipeng
//	 * @Description String转Java对象
//	 * @Date 2020/9/10 12:15
//	 * @Param [jsonString, clazz]
//	 * @return T
//	 **/
//	public static final <T> T toJavaObject(String jsonString, Class<T> clazz){
//		return  JSONObject.toJavaObject(KPJSONUtil.toJson(jsonString), clazz);
//	}
//
//
//
//
//	/**
//	 * @Author lipeng
//	 * @Description 把 对象转 json
//	 * @Date 2020/9/10 15:28
//	 * @Param [obj]
//	 * @return com.alibaba.fastjson.JSONObject
//	 **/
//	public static final JSONObject toJson(Object obj){
//		return JSONObject.parseObject(JSONObject.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue).replaceAll("null", "\"\""), JSONObject.class);
//	}
//
//
//	/**
//	 * @Author lipeng
//	 * @Description 把 对象转 json
//	 * @Date 2022/3/22 14:06
//	 * @param obj
//	 * @param serializerFeature
//	 * @return com.alibaba.fastjson.JSONObject
//	 **/
//	public static final JSONObject toJson(Object obj, SerializerFeature serializerFeature){
//		return JSONObject.parseObject(JSONObject.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss", serializerFeature).replaceAll("null", "\"\""), JSONObject.class);
//	}
//
//
//	/**
//	 * @Author lipeng
//	 * @Description 把对象转JSONObject null 保留成""
//	 * @Date 2024/2/23 10:56
//	 * @param obj
//	 * @return com.alibaba.fastjson.JSONObject
//	 **/
//	public static final JSONObject toJsonRetainEmpty(Object obj){
//		return JSONObject.parseObject(JSONObject.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue).replaceAll("null", "\"\""));
//	}
//
//	public static final String toJsonString(Object obj){
////		return JSONObject.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue);
//		return JSONObject.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss", SerializerFeature.IgnoreErrorGetter,SerializerFeature.WriteMapNullValue);
//	}
//
//	public static final String toJsonStringNotNull(Object obj){
//		// 将 JSON 字符串转换为 JSONObject
////		JSONObject jsonObject = KPJSONUtil.toJavaObject(obj, JSONObject.class);
////		for (Object key : jsonObject.keySet().toArray()) {
////			Object value = jsonObject.get(key);
////			if (value == null || "".equals(value)) {
////				jsonObject.remove(key);
////			}
////		}
////
////		return JSONObject.toJSONStringWithDateFormat(jsonObject, "yyyy-MM-dd HH:mm:ss", SerializerFeature.IgnoreErrorGetter);
//		// 将 JSON 字符串转换为 JSONObject
//		JSONObject jsonObject = KPJSONUtil.toJavaObject(obj, JSONObject.class);
//		// 清理 JSONObject 中的 null 和 ""
//		cleanJsonObject(jsonObject);
//		// 将清理后的 JSONObject 转换回格式化的 JSON 字符串
//		return JSONObject.toJSONStringWithDateFormat(jsonObject, "yyyy-MM-dd HH:mm:ss", SerializerFeature.IgnoreErrorGetter);
//	}
//
//
//
//	/**
//	 * @Author lipeng
//	 * @Description 把一个对象复制给另一个
//	 * @Date 2020/9/10 15:29
//	 * @Param [oldClass, newClass]
//	 * @return T
//	 **/
//	public static final <T> T toJavaObject(Object obj, Class<T> newClass){
//		try {
//			String jsonString = KPJSONUtil.toJsonStringNotNull(obj);
//			return JSONObject.toJavaObject(KPJSONUtil.toJson(jsonString), newClass);
//		}catch (Exception ex){
//			return JSONObject.parseObject(KPJSONUtil.toJsonString(obj), newClass);
//		}
//
//
//
//	}
//
//	/**
//	 * @Author lipeng
//	 * @Description String转Json
//	 * @Date 2020/9/10 12:15
//	 * @Param [jsonString]
//	 * @return com.alibaba.fastjson.JSON
//	 **/
//	public static final JSONObject toJson(String jsonString){
//		return JSONObject.parseObject(jsonString);
//	}
//
////	public static final <T> T toJavaObjectNotNull(Object obj, Class<T> newClass){
//////		return KPJSONUtil.toJavaObjectNotNull(KPJSONUtil.toJson(obj), newClass);
////		String jsonString = KPJSONUtil.toJsonStringNotNull(obj);
////		return JSONObject.toJavaObject(KPJSONUtil.toJson(jsonString), newClass);
////	}
//
//
//
//
//	/**
//	 * @Author lipeng
//	 * @Description 合并json
//	 * @Date 2020/9/14 18:15
//	 * @Param [json1, json2]
//	 * @return com.alibaba.fastjson.JSONObject
//	 **/
//	public static final JSONObject mergeJson(JSONObject json1,JSONObject json2){
//		JSONObject jsonThree = new JSONObject();
//
//		jsonThree.putAll(json1);
//		jsonThree.putAll(json2);
//		return jsonThree;
//	}
//
//
//
//
//
//	public static List<Map<String, Object>> convertPeer(List<?> list){
//		List<Map<String, Object>> mapList = new ArrayList<>();
//
//		for (int i=0 ;i<list.size(); i++) {
//			Map<String, Object> map = new HashMap<>();
//			analysisJson(KPJSONUtil.toJson(list.get(i)), map);
//			mapList.add(map);
//		}
//		return mapList;
//	}
//
//
//	/**
//	 * @Author lipeng
//	 * @Description 把json转HashMap
//	 * @Date 2024/11/8 17:20
//	 * @param json
//	 * @return java.util.HashMap
//	 **/
//	public static HashMap toHashMap(JSONObject json) {
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		for (Map.Entry<String, Object> entry : json.entrySet()) {
//			String key = entry.getKey();
//			Object value = entry.getValue();
//			map.put(key, value);
//		}
//		return map;
//	}
//
//	/**
//	 *
//	 * @Title: analysisJson
//	 * @Description: 遍历多级json 返回map
//	 * @param: @param objJson
//	 * @param: @param map
//	 * @return: void
//	 * @throws
//	 */
//	public static void analysisJson(Object objJson, Map<String, Object> map){
//		//如果obj为json数组
//		if(objJson instanceof JSONArray){
//			JSONArray objArray = (JSONArray)objJson;
//			for (int i = 0; i < objArray.size(); i++) {
//				analysisJson(objArray.get(i),map);
//			}
//		}else if(objJson instanceof JSONObject){
//			//如果为json对象
//			JSONObject jsonObject = (JSONObject)objJson;
//			Iterator<String> it = jsonObject.keySet().iterator();
//			while(it.hasNext()){
//				String key = it.next().toString();
//				Object object = jsonObject.get(key);
//				//如果得到的是数组
//				if(object instanceof JSONArray){
//					JSONArray objArray = (JSONArray)object;
//					if(objArray.size()==0){
//						map.put(key, object);
//					}else{
//						map.put(key, objArray.get(0));
//					}
//					analysisJson(objArray,map);
//
//				}
//				//如果key中是一个json对象
//				else if(object instanceof JSONObject){
//					analysisJson((JSONObject)object,map);
//				}
//				//如果key中是其他
//				else{
//					if(key.indexOf("_")!=-1)
//					{
//						String[] str = key.split("_");
//						key = "";
//						for(int i=0; i<str.length; i++){
//							if(i == 0 ){
//								key+= str[i];
//							}else{
//								key+= str[i].substring(0,1).toUpperCase().concat(str[i].substring(1,str[i].length()));
//							}
//						}
//					}
//					if(map.get(key)==null) {
//						map.put(key, object);
//					}else {
//						try {
//							for(int i=1; i<100; i++){
//								String keys = key+i;
//								if(map.get(keys)==null) {
//									map.put(keys, object);
//									break;
//								}
//							}
//						} catch (Exception e) {
//							map.put(key.concat("_1"), object);
//						}
//						//map.put(key.concat("_").concat(UUID.randomUUID().toString()), object);
//					}
//					//System.out.println("["+key+"]:"+object.toString()+" ");
//				}
//			}
//		}
//	}
//
//
////	public static Map<String,Object> IteratorHash(JSONObject JsonToMap){
////		Iterator<?> it = JsonToMap.keys();
////		HashMap<String, Object> RMap = new HashMap<String, Object>();
////
////		while(it.hasNext()){
////			String key = String.valueOf(it.next());
////			if(JsonToMap.get(key).getClass() == net.sf.json.JSONArray.class){//判是否为列表
////				if(JsonToMap.getJSONArray(key).isEmpty()){//判列表是否为空
////					RMap.put(key,null);
////				}else{
////
////					List<Map<String,Object>> MapListObj=new ArrayList<Map<String,Object>>();
////					for(Object JsonArray : JsonToMap.getJSONArray(key)){
////						HashMap<String, Object> TempMap = new HashMap<String, Object>();
////						if(JsonArray.getClass() == String.class){
////							TempMap.put(key, JsonArray);
////						}else{
////							TempMap.putAll(IteratorHash(net.sf.json.JSONObject.fromObject(JsonArray)));
////						}
////						MapListObj.add(TempMap);
////					}
////					RMap.put(key, (Object) MapListObj);
////				}
////			}else if(JsonToMap.get(key).getClass() == net.sf.json.JSONObject.class){
////
////				RMap.put(key,JsonToMap.getJSONObject(key));
////
////			}else if(JsonToMap.get(key).getClass() == String.class || JsonToMap.get(key).getClass() == Integer.class || JsonToMap.get(key).getClass() == Long.class){
////
////				RMap.put(key, JsonToMap.get(key));
////
////			}
////		}
////		return RMap;
////
////	}
//
//
//	private static void cleanJsonObject(JSONObject jsonObject) {
//		for (Object key : jsonObject.keySet().toArray()) {
//			Object value = jsonObject.get(key);
//			if (value instanceof JSONObject) {
//				// 如果值是 JSONObject，则递归清理
//				cleanJsonObject((JSONObject) value);
//			} else if (value instanceof JSONArray) {
//				// 如果值是 JSONArray，则清理数组内的元素
//				cleanJsonArray((JSONArray) value);
//			} else if (value == null || "".equals(value)) {
//				// 移除 null 和 ""
//				jsonObject.remove(key);
//			}
//		}
//	}
//
//	private static void cleanJsonArray(JSONArray jsonArray) {
//		for (int i = jsonArray.size() - 1; i >= 0; i--) {
//			Object value = jsonArray.get(i);
//			if (value instanceof JSONObject) {
//				// 如果值是 JSONObject，则递归清理
//				cleanJsonObject((JSONObject) value);
//			} else if (value instanceof JSONArray) {
//				// 如果值是 JSONArray，则递归清理
//				cleanJsonArray((JSONArray) value);
//			} else if (value == null || "".equals(value)) {
//				// 移除 null 和 ""
//				jsonArray.remove(i);
//			}
//		}
//	}
//
//}
