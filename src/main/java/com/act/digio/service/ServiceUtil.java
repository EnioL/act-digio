package com.act.digio.service;

import com.act.digio.dto.CompraDTO;
import com.act.digio.entity.Compra;
import com.act.digio.entity.Produto;
import com.act.digio.entity.Venda;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ServiceUtil {

    public List<CompraDTO> joinVendasProdutos(List<Venda> vendas, Map<String, Produto> produtosMapByCodigo){
        List<CompraDTO> comprasDTOS = new ArrayList<>();

        vendas.forEach(venda -> comprasDTOS.addAll(
                        getComprasDTO(
                                venda.getNome(),
                                venda.getCpf(),
                                venda.getCompras(),
                                produtosMapByCodigo)
                )
        );

        return comprasDTOS;
    }

    public List<CompraDTO> getComprasDTO(String nome, String cpf, List<Compra> compras, Map<String, Produto> produtosMapByCodigo){
        return compras.stream()
                .filter(compra -> produtosMapByCodigo.containsKey(compra.getCodigo()))
                .map(compra -> new CompraDTO(
                        nome,
                        cpf,
                        produtosMapByCodigo.get(compra.getCodigo()).getCodigo(),
                        produtosMapByCodigo.get(compra.getCodigo()).getTipoVinho(),
                        produtosMapByCodigo.get(compra.getCodigo()).getPreco(),
                        produtosMapByCodigo.get(compra.getCodigo()).getSafra(),
                        produtosMapByCodigo.get(compra.getCodigo()).getAnoCompra(),
                        compra.getQuantidade(),
                        compra.getQuantidade() * produtosMapByCodigo.get(compra.getCodigo()).getPreco()
                ))
                .collect(Collectors.toList());

    }
}
