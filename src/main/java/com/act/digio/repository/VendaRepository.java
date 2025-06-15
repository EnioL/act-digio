package com.act.digio.repository;

import com.act.digio.entity.Venda;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Repository
public class VendaRepository {

    public List<Venda> getVendas() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("src/main/java/com/act/digio/dados/compras");

            List<Venda> vendas = mapper.readValue(file, new TypeReference<>() {});

            return vendas;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
