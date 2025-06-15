package com.act.digio.service;

import com.act.digio.entity.Produto;
import com.act.digio.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    Map<String, Produto> getProdutosByCodigo(){
        return produtoRepository.getProdutos().stream().collect(Collectors.toMap(Produto::getCodigo, produto -> produto));
    }
}
