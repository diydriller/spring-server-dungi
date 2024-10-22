package com.dungi.redis.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectMapperTest {

    static ObjectMapper objectMapper;

    @BeforeEach
    void initEach(){
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName(value = "직렬화 역직렬화 테스트")
    void deserializeObjectTest() throws IOException {
        var beforeObject = new TestObject(30.0, 40.0);
        Writer writer = new StringWriter();
        objectMapper.writeValue(writer, beforeObject);
        String jsonString = writer.toString();

        Reader reader = new StringReader(jsonString);
        var afterObject = objectMapper.readValue(reader, TestObject.class);

        assertEquals(beforeObject.getXPosition(), afterObject.getXPosition());
        assertEquals(beforeObject.getYPosition(), afterObject.getYPosition());
    }

    public static class TestObject{
        private Double xPosition;
        private Double yPosition;
        public TestObject() {
        }
        public TestObject(Double xPosition, Double yPosition) {
            this.xPosition = xPosition;
            this.yPosition = yPosition;
        }
        public Double getXPosition() {
            return xPosition;
        }
        public Double getYPosition() {
            return yPosition;
        }
        public void setXPosition(Double xPosition) {
            this.xPosition = xPosition;
        }
        public void setYPosition(Double yPosition) {
            this.yPosition = yPosition;
        }
    }
}
