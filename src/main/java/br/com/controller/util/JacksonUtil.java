package br.com.controller.util;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {
	private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T fromJson(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }
}
