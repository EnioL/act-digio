package com.act.digio.service;

import com.act.digio.dto.ComprasDTO;
import com.act.digio.entity.Produto;
import com.act.digio.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

//    List<ComprasDTO> getCompras(){
//        List<Produto> produtos = produtoRepository.getProdutos();
//        return produtos;
//    }
}
