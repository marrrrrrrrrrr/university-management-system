package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.Student;

public class JsonUtil {

    private static final ObjectMapper objectMapper = createObjectMapper();

    private static ObjectMapper createObjectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }

    public static Student studentFromJson(String json) {
        try {
            return objectMapper.readValue(json, Student.class);
        } catch (Exception e) {
            System.err.println("Error parsing Student from JSON: " + e.getMessage());
            return null;
        }
    }
}