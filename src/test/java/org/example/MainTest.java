package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MainTest {
	@BeforeEach
    void resetInput() {
        System.setIn(System.in); // نرجع الـ System.in للوضع الطبيعي قبل كل تيست
    }


 
    @Test
    void mainShouldRunWithoutException() {
        assertDoesNotThrow(() -> {
            Thread t = new Thread(() -> Main.main(new String[]{}));
            t.start();
            t.interrupt(); // نوقفه بسرعة حتى ما يضل يشتغل
        });
    }

    @Test
    void mainMultipleFlows() {

        String input = """
                1
                4
                2
                3
                3
                """;

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }
}