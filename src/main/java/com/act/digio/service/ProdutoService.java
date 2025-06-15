package com.act.digio.service;

import com.act.digio.entity.Produto;
import com.act.digio.entity.Venda;
import com.act.digio.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> getProdutos(){
        return produtoRepository.getProdutos();
    }
    Map<String, Produto> getMapByCodigo(){
        return getProdutos().stream().collect(Collectors.toMap(Produto::getCodigo, produto -> produto));
    }
    Map<String, Produto> getMapByCodigoAndFilterByAnoCompra(Integer anoCompra){
        return getProdutos().stream()
                .filter(produto -> Objects.equals(produto.getAnoCompra(), anoCompra))
                .collect(Collectors.toMap(Produto::getCodigo, produto -> produto));
    }
}
