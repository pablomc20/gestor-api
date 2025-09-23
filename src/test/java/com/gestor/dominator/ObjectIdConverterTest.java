package com.gestor.dominator;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import com.gestor.dominator.components.ObjectIdConverter;

import static org.junit.jupiter.api.Assertions.*;

class ObjectIdConverterTest {

    private final ObjectIdConverter converter = new ObjectIdConverter();

    @Test
    void testStringToObjectId() {
        String id = "507f1f77bcf86cd799439011";
        ObjectId objectId = converter.stringToObjectId(id);
        assertNotNull(objectId);
        assertEquals(id, objectId.toHexString());
    }

    @Test
    void testStringToObjectIdNull() {
        ObjectId objectId = converter.stringToObjectId(null);
        assertNull(objectId);
    }

    @Test
    void testStringToObjectIdEmpty() {
        ObjectId objectId = converter.stringToObjectId("");
        assertNull(objectId);
    }

    @Test
    void testObjectIdToString() {
        ObjectId objectId = new ObjectId();
        String id = converter.objectIdToString(objectId);
        assertNotNull(id);
        assertEquals(objectId.toHexString(), id);
    }

    @Test
    void testObjectIdToStringNull() {
        String id = converter.objectIdToString(null);
        assertNull(id);
    }

    @Test
    void testIsValidObjectId() {
        assertTrue(converter.isValidObjectId("507f1f77bcf86cd799439011"));
        assertFalse(converter.isValidObjectId("invalid"));
        assertFalse(converter.isValidObjectId(null));
        assertFalse(converter.isValidObjectId(""));
    }
}