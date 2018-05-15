package com.iisigroup.colabase.service.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.iisigroup.colabase.annotation.JsonTemp;
import com.iisigroup.colabase.util.RequestFactory;
import com.iisigroup.colabase.annotation.ApiRequest;
import com.iisigroup.colabase.model.RequestContent;
import com.iisigroup.colabase.service.JsonDataService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author AndyChen
 * @version <ul>
 *          <li>2018/5/14 AndyChen,new
 *          </ul>
 * @since 2018/5/14
 */
@Service
public class JsonDataServiceImpl implements JsonDataService {

    private final String ARRAY_MARK = "[]";
    private final String LAST_PROCESS_KEY = "lastProcess";
    private final String ARRAY_MAP_KEY = "arrayMap";

    public JsonDataServiceImpl() {
    }

    @Override
    public <T extends RequestContent> void setParamToJsonContent(T requestContent, String fieldName, String value) {
        Map<String, Object> arrayMap;
        try {
            arrayMap =  (Map<String, Object>)RequestFactory.getFieldObject(requestContent, ARRAY_MAP_KEY);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException("can not found arrayMap in requestContent");
        }
        JsonObject requestJson = requestContent.getRequestContent();
        ApiRequest fieldAnnotation = getFieldAnnotation(requestContent.getClass(), fieldName);
        if (fieldAnnotation == null)
            return;
        String path = fieldAnnotation.path();
        String[] paths = path.split("\\.");
        this.countArray(path, arrayMap);
        JsonObject jsonElement = getJsonElement(requestJson, path, arrayMap);
        jsonElement.addProperty(paths[paths.length - 1], value);

    }

    /**
     * count set field times
     * 當array路徑次數加一時，底下的array節點必須歸零次數，避免產生多於空元素
     * @param path path chain
     * @param arrayMap count each setting method times
     */
    private void countArray(String path, Map<String, Object> arrayMap) {
        arrayMap.put(LAST_PROCESS_KEY, path);
        Object object = arrayMap.get(path);
        if(object == null) {
            arrayMap.put(path, 0);
        } else {
            arrayMap.put(path, (Integer)object + 1);
            //上層array擴增，底下所有的array計數要歸零
            String parentArrayName = path.substring(0, path.lastIndexOf("."));
            parentArrayName = parentArrayName.replaceAll("\\[\\]", "\\\\[\\\\]");
            for (String key : arrayMap.keySet()) {
                String regExpStr = parentArrayName + "\\..*\\[\\]\\..*";
                Pattern pattern = Pattern.compile(regExpStr);
                Matcher matcher = pattern.matcher(key);
                if (matcher.matches()) {
                    try {
                        Integer.parseInt(String.valueOf(arrayMap.get(key)));
                        arrayMap.put(key, null);
                    } catch (NumberFormatException e) {
                    }
                }
            }
        }
    }

    /**
     * 經由每次遞迴最後取出路徑chain中最後一個節點JsonObject物件. ex: person.company.name -> 取出company的jsonObject
     * 基線為路徑chain只有一個節點(表示為最後)
     * @param jsonObject 父節點
     * @param eleNameChain 路徑節點
     * @param arrayMap 紀錄設值次數map
     * @return 包含最後一個節點的jsonObject
     */
    private JsonObject getJsonElement(JsonObject jsonObject, String eleNameChain, Map<String, Object> arrayMap) {
        String[] paths = eleNameChain.split("\\.");
        String path = paths[0];
        if(path.contains(ARRAY_MARK) && paths.length > 1) {
            path = path.replace(ARRAY_MARK, "");
            String newPath = eleNameChain.substring(eleNameChain.indexOf(ARRAY_MARK) + 3);
            jsonObject = this.getJsonArrayElement(jsonObject, path, arrayMap);
            return this.getJsonElement(jsonObject, newPath, arrayMap);
        }
        if(paths.length == 1)
            return jsonObject;
        String newPath = eleNameChain.substring(eleNameChain.indexOf(".") + 1);
        JsonElement jsonElement = jsonObject.get(path);
        return this.getJsonElement((JsonObject)jsonElement, newPath, arrayMap);
    }

    /**
     * 取出Array裡的JsonObject，並判斷是否要新增一個JsonObject in array
     * @param jsonObject 當前JsonObject
     * @param arrayName array name in jsonObject
     * @param arrayMap 紀錄設值次數map
     * @return 所要用的JsonObject
     */
    private JsonObject getJsonArrayElement(JsonObject jsonObject, String arrayName, Map<String, Object> arrayMap) {
        JsonArray jsonArray = jsonObject.getAsJsonArray(arrayName);
        if(jsonArray == null)
            throw new IllegalArgumentException("eleName did not found any json array");

        String lastProcess = (String)arrayMap.get(LAST_PROCESS_KEY);
        String[] paths = lastProcess.split("\\.");
        Integer count = (Integer) arrayMap.get(lastProcess);

        //目標array
        String markPath = paths[paths.length - 2].replace(ARRAY_MARK, "");
        if(arrayName.equals(markPath)) {
            String arrayKey = arrayName + ARRAY_MARK;
            if(count == 0) {
                arrayMap.putIfAbsent(arrayKey, deepCopy(jsonArray.get(0)));
                return (JsonObject) jsonArray.get(0);
            } else {
                if(jsonArray.size() - 1 == count)
                    return (JsonObject) jsonArray.get(count);
                JsonObject temp = (JsonObject) arrayMap.get(arrayKey);
                JsonElement element = (JsonElement) deepCopy(temp);
                jsonArray.add(element);
                return (JsonObject) element;
            }
        } else {
            //中途array
            return (JsonObject)jsonArray.get(jsonArray.size() - 1);
        }
    }

    private Object deepCopy(Object jsonElement) {
        if(jsonElement instanceof JsonPrimitive) {
            String value = ((JsonPrimitive) jsonElement).getAsString();
            return new JsonPrimitive(value);
        }
        if(jsonElement instanceof JsonArray) {
            JsonArray jsonArray = new JsonArray();
            for (JsonElement element : (JsonArray)jsonElement) {
                jsonArray.add((JsonElement) deepCopy(element));
            }
            return jsonArray;
        }
        JsonObject jsonObject = (JsonObject)jsonElement;
        JsonObject result = new JsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            result.add(entry.getKey(), (JsonElement) deepCopy(entry.getValue()));
        }
        return result;
    }

    /**
     * get annotation mark on field, will lookup extends chain while to RequestContent.class(father class)
     * @param tClass target class
     * @param fieldName field name defined in child class
     * @return ApiRequest annotation
     */
    private <T extends RequestContent> ApiRequest getFieldAnnotation(Class<T> tClass, String fieldName) {
        Field field;
        try {
            field = tClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if(RequestContent.class == tClass)
                throw new IllegalArgumentException("any class extends RequestContent, not have this fieldName: " + fieldName);
            return getFieldAnnotation((Class<T>) tClass.getSuperclass(), fieldName);
        }
        return field.getAnnotation(ApiRequest.class);
    }

    @Override
    public <T extends RequestContent> void cleanJsonObjectData(T reqInstance) {
        this.cleanJsonObject(reqInstance.getRequestContent());
    }

    private void cleanJsonObject(JsonObject jsonObject) {
        Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            JsonElement value = entry.getValue();
            if(value instanceof JsonPrimitive) {
                jsonObject.add(entry.getKey(), new JsonPrimitive(""));
            } else if(value instanceof JsonArray){
                for (JsonElement jsonElement : ((JsonArray) value)) {
                    this.cleanJsonObject((JsonObject) jsonElement);
                }
            } else {
                this.cleanJsonObject((JsonObject) value);
            }
        }
    }

    private <T extends RequestContent> Class<T> getMainClass(Class<?> reqClass) {
        if(RequestContent.class == reqClass)
            throw new IllegalArgumentException("can not found any with JsonTemp annotation class");

        Field[] fields = reqClass.getDeclaredFields();
        for (Field field : fields) {
            if(field.getAnnotation(JsonTemp.class) != null) { //main class must have @JsonTemp for jsonTempStr
                return (Class<T>) reqClass;
            }
        }
        return this.getMainClass(reqClass.getSuperclass());
    }

    @Override
    public <T extends RequestContent> void setDefaultValue(T reqInstance, Map<String, String> valueMap) {
        for (String key : valueMap.keySet()) {
            String value = valueMap.get(key).trim();
            if("".equals(value))
                continue;
            this.setParamToJsonContent(reqInstance, key, value);
        }
        //must clean arrayMap
        try {
            Field field = RequestContent.class.getDeclaredField(ARRAY_MAP_KEY);
            field.setAccessible(true);
            Map<String, Object> arrayMap = (Map<String, Object>) field.get(reqInstance);
            arrayMap.clear();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException("can not found array map while set default value");
        }
    }


    @Override
    public <T extends RequestContent> void removeUnnecessaryNode(T requestContent) {

    }

    @Override
    public JsonObject getJsonStr(RequestContent requestContent) {
        return null;
    }
}
