package com.github.vspro.dmsprovider.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringWriter;

@Slf4j
public abstract class JacksonUtil {


    public static String toJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter writer = new StringWriter();
            objectMapper.writeValue(writer, obj);
            return writer.toString();
        } catch (IOException e) {
            throw new RuntimeException("对象序列化失败", e);
        }
    }

    public static <T> T toBean(Class<T> clz, String json) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, clz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("对象反序列化失败", e);
        }
    }

}
