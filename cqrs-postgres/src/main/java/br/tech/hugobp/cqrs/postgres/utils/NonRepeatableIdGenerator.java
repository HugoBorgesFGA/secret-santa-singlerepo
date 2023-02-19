package br.tech.hugobp.cqrs.postgres.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NonNull;
import org.apache.commons.codec.digest.DigestUtils;


public class NonRepeatableIdGenerator {
    private static final ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule());

    public static void stampAsNonRepeatable(@NonNull NonRepeatableEntity nonRepeatableEntity) {
        try {
            final String contentAsJson = objectMapper.writeValueAsString(nonRepeatableEntity);
            final String hashOfContent = DigestUtils.sha256Hex(contentAsJson);
            nonRepeatableEntity.setNonRepeatableId(hashOfContent);
        } catch (JsonProcessingException e) {
            final String className = nonRepeatableEntity.getClass().getName();
            throw new RuntimeException("Could assign a non repeatable id to an entity of type " + className, e);
        }
    }
}
