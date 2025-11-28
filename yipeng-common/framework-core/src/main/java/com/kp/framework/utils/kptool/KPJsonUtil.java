package com.kp.framework.utils.kptool;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.kp.framework.exception.KPServiceException;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/* *
 * @Author 李鹏
 * @Description //
 * @Date 2020/5/31 0:26
 * @Param
 * @return
 **/
@UtilityClass
public final class KPJsonUtil {

    private static Logger log = LoggerFactory.getLogger(KPJsonUtil.class);

    /**
     * @param obj
     * @param clazz
     * @return T
     * @Author lipeng
     * @Description 把一个对象转成另一个对象
     * @Date 2025/1/3 15:13
     **/
    public static final <T> T toJavaObject(Object obj, Class<T> clazz) {
        try {
            return JSON.toJavaObject(obj, clazz);
        } catch (Exception ex) {
            try {
                return JSON.toJavaObject(JSON.toJSONString(obj), clazz);
            }catch (Exception e){
                log.error("json转Java对象失败：" + e.getMessage());
                throw new KPServiceException("数据转换异常！");
            }
        }
    }

    /**
     * @Author lipeng
     * @Description 把JSONObject 转成对象
     * @Date 2025/11/20 11:23
     * @param jsonObject
     * @param clazz
     * @return T
     **/
    public static final <T> T toJavaObject(JSONObject jsonObject, Class<T> clazz) {
        try {
            cleanJsonObject(jsonObject);
            return JSON.toJavaObject(jsonObject, clazz);
        } catch (Exception e) {
            log.error("json转Java对象失败：" + e.getMessage());
            throw new KPServiceException("数据转换异常！");
        }
    }

    /**
     * @Author lipeng
     * @Description 把一个对象转成另一个对象并且去掉值数null的内容
     * @Date 2025/11/20 11:23
     * @param obj
     * @param clazz
     * @return T
     **/
    public static final <T> T toJavaObjectNotEmpty(Object obj, Class<T> clazz) {
        return toJavaObject(KPJsonUtil.toJson(obj), clazz);
    }


    /**
     * @return java.util.List<T>
     * @Author lipeng
     * @Description json 转 Javalist
     * @Date 2025/1/3 14:58
     **/
    public static <T> List<T> toJavaObjectList(Object obj, Class<T> clazz) {
        if (isJson(obj.toString())){
            return toJavaObjectList(obj.toString(), clazz);
        }else{
            return toJavaObjectList(JSON.toJSONString(obj), clazz);
        }
    }


    /**
     * @Author lipeng
     * @Description 把一个list 转成另一个list
     * @Date 2025/11/20 11:24
     * @param list
     * @param clazz
     * @return java.util.List<T>
     **/
    public static <T> List<T> toJavaObjectList(List list, Class<T> clazz) {
        return toJavaObjectList(JSON.toJSONString(list), clazz);
    }


    /**
     * @Author lipeng
     * @Description 把一个josn字符串转成 list
     * @Date 2025/11/20
     * @param jsonString
     * @param clazz
     * @return java.util.List<T>
     **/
    public static final <T> List<T> toJavaObjectList(String jsonString, Class<T> clazz) {
        try {
            return JSON.parseArray(jsonString, clazz);
        } catch (Exception e) {
            log.error("json转Java对象列表失败：" + e.getMessage());
            throw new KPServiceException("数据转换异常！");
        }
    }


    /**
     * @return com.alibaba.fastjson.JSONObject
     * @Author lipeng
     * @Description 把 对象转 json
     * @Date 2020/9/10
     * @Param [obj]
     **/
    public static final JSONObject toJson(Object obj) {
        try {
            // 配置 JSONWriter 特性
            JSONWriter.Feature[] features = {
                    JSONWriter.Feature.WriteBigDecimalAsPlain, //将 BigDecimal 值序列化为普通字符串，而不是科学计数法。
//                    JSONWriter.Feature.WriteNullStringAsEmpty, //将 null 字符串序列化为空字符串 ""。
                    JSONWriter.Feature.WriteMapNullValue, //序列化映射中的 null 值
                    JSONWriter.Feature.IgnoreErrorGetter
            };

            // 将对象转换为 JSON 字符串，并应用配置
            String jsonString = JSON.toJSONString(obj, "yyyy-MM-dd HH:mm:ss", features);

            // 将 JSON 字符串中的 "null" 替换为 ""
            //jsonString = jsonString.replaceAll("null", "\"\"");

            // 将 JSON 字符串转换为 JSONObject
            return JSON.parseObject(jsonString);
        } catch (Exception e) {
//            log.error("对象转JSONObject失败：" + e.getMessage());
            throw new KPServiceException("数据转换异常！");
        }
    }


    /**
     * @Author lipeng
     * @Description  那个json字符串转成jsonObject
     * @Date 2025/11/20
     * @param jsonStr
     * @return com.alibaba.fastjson2.JSONObject
     **/
    public static final JSONObject toJson(String jsonStr) {
        return JSONObject.parseObject(jsonStr);
    }

    /**
     * @Author lipeng
     * @Description 把对象转成json字符串
     * @Date 2023/11/20
     * @param obj
     * @return java.lang.String
     **/
    public static final String toJsonString(Object obj) {
        try {
            // 配置 JSONWriter 特性
            JSONWriter.Feature[] features = {
                    JSONWriter.Feature.IgnoreErrorGetter,
                    JSONWriter.Feature.WriteMapNullValue
            };
            // 将对象转换为 JSON 字符串，并应用配置
            return JSON.toJSONString(obj, "yyyy-MM-dd HH:mm:ss", features);
        } catch (Exception e) {
            log.error("对象转JSON字符串失败：" + e.getMessage());
            throw new KPServiceException("数据转换异常！");
        }
    }

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
    public static final JSONObject mergeJson(JSONObject... json) {
        JSONObject jsonThree = new JSONObject();
        Arrays.stream(json).iterator().forEachRemaining(jsonObject -> {
            jsonThree.putAll(jsonObject);
        });
        return jsonThree;
    }


    /**
     * @param json
     * @return java.util.HashMap
     * @Author lipeng
     * @Description 把json转HashMap
     * @Date 2024/11/8 17:20
     **/
    public static HashMap toHashMap(JSONObject json) {
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		for (Map.Entry<String, Object> entry : json.entrySet()) {
//			String key = entry.getKey();
//			Object value = entry.getValue();
//			map.put(key, value);
//		}
//        return map;
        return JSON.toJavaObject(json, HashMap.class);
    }


    /**
     * @Author lipeng
     * @Description 字典顺序
     * @Date 2025/4/15 11:50
     * @param json
     * @return java.util.TreeMap
     **/
    public static TreeMap toTreeMap(JSONObject json) {
        TreeMap<String, Object> map = new TreeMap<String, Object>();
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            map.put(key, value);
        }
        return map;
    }
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


//	public static Map<String,Object> IteratorHash(JSONObject JsonToMap){
//		Iterator<?> it = JsonToMap.keys();
//		HashMap<String, Object> RMap = new HashMap<String, Object>();
//
//		while(it.hasNext()){
//			String key = String.valueOf(it.next());
//			if(JsonToMap.get(key).getClass() == net.sf.json.JSONArray.class){//判是否为列表
//				if(JsonToMap.getJSONArray(key).isEmpty()){//判列表是否为空
//					RMap.put(key,null);
//				}else{
//
//					List<Map<String,Object>> MapListObj=new ArrayList<Map<String,Object>>();
//					for(Object JsonArray : JsonToMap.getJSONArray(key)){
//						HashMap<String, Object> TempMap = new HashMap<String, Object>();
//						if(JsonArray.getClass() == String.class){
//							TempMap.put(key, JsonArray);
//						}else{
//							TempMap.putAll(IteratorHash(net.sf.json.JSONObject.fromObject(JsonArray)));
//						}
//						MapListObj.add(TempMap);
//					}
//					RMap.put(key, (Object) MapListObj);
//				}
//			}else if(JsonToMap.get(key).getClass() == net.sf.json.JSONObject.class){
//
//				RMap.put(key,JsonToMap.getJSONObject(key));
//
//			}else if(JsonToMap.get(key).getClass() == String.class || JsonToMap.get(key).getClass() == Integer.class || JsonToMap.get(key).getClass() == Long.class){
//
//				RMap.put(key, JsonToMap.get(key));
//
//			}
//		}
//		return RMap;
//
//	}


    /**
     * @param jsonObject
     * @return void
     * @Author lipeng
     * @Description 把 “” 改成null
     * @Date 2025/1/3 17:09
     **/
    private static void cleanJsonObject(JSONObject jsonObject) {
        for (Object key : jsonObject.keySet().toArray()) {
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                // 如果值是 JSONObject，则递归清理
                cleanJsonObject((JSONObject) value);
            } else if (value instanceof JSONArray) {
                // 如果值是 JSONArray，则清理数组内的元素
                cleanJsonArray((JSONArray) value);
            } else if (value == null || "".equals(value)) {
                // 移除 null 和 ""
                jsonObject.remove(key);
            }
        }
    }

    private static void cleanJsonArray(JSONArray jsonArray) {
        for (int i = jsonArray.size() - 1; i >= 0; i--) {
            Object value = jsonArray.get(i);
            if (value instanceof JSONObject) {
                // 如果值是 JSONObject，则递归清理
                cleanJsonObject((JSONObject) value);
            } else if (value instanceof JSONArray) {
                // 如果值是 JSONArray，则递归清理
                cleanJsonArray((JSONArray) value);
            } else if (value == null || "".equals(value)) {
                // 移除 null 和 ""
                jsonArray.remove(i);
            }
        }
    }


    /**
     * @Author lipeng
     * @Description 判断字符串是不是json
     * @Date 2025/4/17 15:06
     * @param jsonStr
     * @return boolean
     **/
    public static boolean isJson(String jsonStr) {
        try {
            JSON.parse(jsonStr);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }


    public static void main(String[] args) {

        System.out.println(toJson(new KPJSONFactoryUtil().put("asdsad",null).put("asdsadsdasdd","asds").build()));
    }
}
