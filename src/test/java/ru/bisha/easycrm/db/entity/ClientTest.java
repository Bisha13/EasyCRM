package ru.bisha.easycrm.db.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @DisplayName("Test phone number")
    @ParameterizedTest
    @CsvSource({"89153332211,+7(915)333-22-11", "9153332211,+7(915)333-22-11", "123456789,123456789"})
    void testPhoneNumber(String input, String expected) {
        Client client = new Client();
        client.setPhoneNumber(input);

        assertEquals(expected, client.getPhoneNumber());
    }
}