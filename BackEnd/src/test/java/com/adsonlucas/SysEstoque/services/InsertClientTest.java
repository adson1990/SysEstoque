package com.adsonlucas.SysEstoque.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.adsonlucas.SysEstoque.entitiesDTO.ClientDTO;

@SpringBootTest
public class InsertClientTest {

	@Autowired
    private ClientService clientService;

    @Test
    public void testSaveClient() {
        ClientDTO client = new ClientDTO();
        client.setCpf("12345678900");
        client.setName("Test Client");
        LocalDateTime localDateTime = LocalDateTime.of(2023, 6, 13, 12, 0); // ano, mês, dia, hora, minuto
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
        client.setBirthDate(instant);
        client.setChildren(2);
        client.setIncome(5000.00);
        
        ClientDTO savedClient = clientService.insClient(client);
        assertNotNull(savedClient.getID());
    }
}
