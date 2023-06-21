package dev.projects.backend.service;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenerateUUIDServiceTest {

    @Test
    void getUUIDServiceTest() {
        // given
        GenerateUUIDService generateUUIDService = new GenerateUUIDService();

        // when
        String newUUID = generateUUIDService.getUUID();

        // then
        assertNotNull(newUUID);
        assertTrue(isValidUUID(newUUID));
    }

    private boolean isValidUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

}