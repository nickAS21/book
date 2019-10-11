package com.lardi.phonebook.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lardi.phonebook.model.Contact;
import com.lardi.phonebook.model.User;
import org.springframework.boot.json.JsonParseException;

import java.io.IOException;
import java.util.Map;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public class Util {


    public static User getUserValidOk(String id) {
        User user = new User();
        user.setId(id);
        user.setLogin("nickF");
        user.setPib("nickNew");
        user.setPassword("123456");
        user.setConfirmPassword(user.getPassword());
        return user;
    }

    public static User getUserValidBad4() {
        User user = new User();
        user.setLogin("ni");
        user.setPib("pi");
        user.setPassword("1234");
        user.setConfirmPassword("123456");
        return user;
    }

    public static Contact getContactValidOk(String id) {
        Contact contact = new Contact();
        contact.setId(id);
        contact.setFirstName("ContactFirst1");
        contact.setLastName("ContactLast1");
        contact.setMiddleName("ContactMiddle1");
        contact.setMobilePhone("+380(68)4356789");
        return contact;
    }

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule());

    public static String requestBody(Object request) {
        try {
            return MAPPER.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T  linkedHashMapToPojo(Map map, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(map, clazz);
    }

    public static  <T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }


}
