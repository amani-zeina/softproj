package org.example.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ServiceTypeTest {

    @Test
    void serviceTypeValuesShouldExist() {

        ServiceType[] types = ServiceType.values();

        assertNotNull(types);
        assertTrue(types.length > 0);
    }

    @Test
    void fromChoiceShouldReturnCorrectType() {

        ServiceType type = ServiceType.fromChoice(1);

        assertNotNull(type);
    }

    @Test
    void printOptionsShouldNotThrow() {

        assertDoesNotThrow(() ->
                ServiceType.printOptions()
        );
    }
}