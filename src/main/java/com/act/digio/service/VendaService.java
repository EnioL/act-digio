package com.act.digio.service;

import com.act.digio.dto.ComprasDTO;
import com.act.digio.entity.Produto;
import com.act.digio.entity.Venda;
import com.act.digio.repository.ProdutoRepository;
import com.act.digio.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendaService {
    @Autowired
    private VendaRepository vendaRepository;

    public List<Venda> getCompras(){
        List<Venda> produtos = vendaRepository.getCompras();
        return produtos;
    }
}
