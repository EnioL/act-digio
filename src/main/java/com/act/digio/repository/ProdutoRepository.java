package com.act.digio.repository;

import com.act.digio.entity.Produto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

@Repository
public class ProdutoRepository {

    public List<Produto> getProdutos() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("src/main/java/com/act/digio/dados/produtos");

            List<Produto> produtos = mapper.readValue(file, new TypeReference<>() {});

            return produtos;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
