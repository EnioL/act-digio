package com.act.digio.service;

import com.act.digio.dto.TopClienteDTO;
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
        List<CompraDTO> comprasDTOS = joinVendasProdutos(produtoService.getMapByCodigo());
        return comprasDTOS.stream().sorted(Comparator.comparing(CompraDTO::getValorTotal)).collect(Collectors.toList());
    }

    public CompraDTO getMaiorCompra(Integer anoCompra){
        List<CompraDTO> comprasDTOS = joinVendasProdutos(produtoService.getMapByCodigoAndFilterByAnoCompra(anoCompra));
        return comprasDTOS.stream().max(Comparator.comparing(CompraDTO::getValorTotal)).orElse(null);
    }

    public List<TopClienteDTO> getClientesFies(){
        Map<String, List<CompraDTO>> comprasGroupingByNome = getCompras().stream()
                .collect(Collectors.groupingBy(CompraDTO::getNome));

        return comprasGroupingByNome.entrySet().stream().map(
                entry -> {
                    List<CompraDTO> compraDTOS = entry.getValue();
                    Double somatorio = compraDTOS.stream().mapToDouble(CompraDTO::getValorTotal).sum();

                    return new TopClienteDTO(entry.getKey(), somatorio);
                }
        ).sorted(Comparator.comparing(TopClienteDTO::getGastoTotal).reversed())
        .limit(3)
        .toList();
    }


    public List<CompraDTO> joinVendasProdutos(Map<String, Produto> produtosMapByCodigo){
        List<CompraDTO> comprasDTOS = new ArrayList<>();

        getVendas().forEach(venda -> comprasDTOS.addAll(
                getComprasDTO(
                        venda.getNome(),
                        venda.getCpf(),
                        venda.getCompras(),
                        produtosMapByCodigo)
                )
        );

        return comprasDTOS;
    }

    private List<CompraDTO> getComprasDTO(String nome, String cpf, List<Compra> compras, Map<String, Produto> produtosMapByCodigo){
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
