package com.act.digio.service;

import com.act.digio.dto.CompraDTO;
import com.act.digio.entity.Compra;
import com.act.digio.entity.Produto;
import com.act.digio.entity.Venda;
import com.act.digio.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VendaService {
    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoService produtoService;

    public List<Venda> getVendas(){
        return vendaRepository.getVendas();
    }

    public List<CompraDTO> getCompras(){
        List<CompraDTO> comprasDTOS = new ArrayList<>();

        Map<String, Produto> produtosByCodigo = produtoService.getProdutosByCodigo();
        List<Venda> vendas = getVendas();

        vendas.forEach(venda -> comprasDTOS.addAll(
                getComprasDTO(
                        venda.getNome(),
                        venda.getCpf(),
                        venda.getCompras(),
                        produtosByCodigo)
                )
        );

        return comprasDTOS.stream().sorted(Comparator.comparing(CompraDTO::getValorTotal)).collect(Collectors.toList());
    }

    private List<CompraDTO> getComprasDTO(String nome, String cpf, List<Compra> compras, Map<String, Produto> produtosByCodigo){
        return compras.stream().map(compra -> new CompraDTO(
                    nome,
                    cpf,
                    produtosByCodigo.get(compra.getCodigo()).getCodigo(),
                    produtosByCodigo.get(compra.getCodigo()).getTipoVinho(),
                    produtosByCodigo.get(compra.getCodigo()).getPreco(),
                    produtosByCodigo.get(compra.getCodigo()).getSafra(),
                    produtosByCodigo.get(compra.getCodigo()).getAnoCompra(),
                    compra.getQuantidade(),
                    compra.getQuantidade() * produtosByCodigo.get(compra.getCodigo()).getPreco()
                ))
                .collect(Collectors.toList());

    }
}
