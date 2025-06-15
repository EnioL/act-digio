package com.act.digio.service;

import com.act.digio.dto.RecomendacaoClienteDTO;
import com.act.digio.dto.TopClienteDTO;
import com.act.digio.dto.CompraDTO;
import com.act.digio.entity.Venda;
import com.act.digio.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private ServiceUtil serviceUtil;

    public List<Venda> getVendas(){
        return vendaRepository.getVendas();
    }

    public List<CompraDTO> getCompras(){
        List<CompraDTO> comprasDTOS = serviceUtil.joinVendasProdutos(getVendas(), produtoService.getMapByCodigo());
        return comprasDTOS.stream().sorted(Comparator.comparing(CompraDTO::getValorTotal)).collect(Collectors.toList());
    }

    public CompraDTO getMaiorCompra(Integer anoCompra){
        List<CompraDTO> comprasDTOS = serviceUtil.joinVendasProdutos(getVendas(), produtoService.getMapByCodigoAndFilterByAnoCompra(anoCompra));
        return comprasDTOS.stream().max(Comparator.comparing(CompraDTO::getValorTotal)).orElse(null);
    }

    public List<TopClienteDTO> getClientesFies(){
        // Agrupar as compras por nome do cliente
        Map<String, List<CompraDTO>> comprasGroupingByNome = getCompras().stream()
                .collect(Collectors.groupingBy(CompraDTO::getNome));

        return comprasGroupingByNome.entrySet().stream().map(
                entry -> {
                    // Soma todos os gastos do cliente
                    List<CompraDTO> compraDTOS = entry.getValue();
                    Double somatorio = compraDTOS.stream().mapToDouble(CompraDTO::getValorTotal).sum();

                    return new TopClienteDTO(entry.getKey(), somatorio);
                }
        ).sorted(Comparator.comparing(TopClienteDTO::getGastoTotal).reversed())
        .limit(3)
        .toList();
    }

    public List<RecomendacaoClienteDTO> getRecomedacao(){
        // Agrupar as compras por nome do cliente
        return getCompras().stream()
                .collect(Collectors.groupingBy(CompraDTO::getNome))
                .entrySet().stream()
                .map(entry -> {
                    String nomeCliente = entry.getKey();
                    List<CompraDTO> comprasDoCliente = entry.getValue();

                    // Agrupar as compras do cliente por tipo de vinho
                    Map<String, Integer> quantidadePorVinho = comprasDoCliente.stream()
                            .collect(Collectors.groupingBy(
                                    CompraDTO::getTipoVinho,
                                    Collectors.summingInt(CompraDTO::getQuantidade)
                            ));

                    // Encontrar o vinho com maior quantidade comprada
                    String vinhoFavorito = quantidadePorVinho.entrySet().stream()
                            .max(Map.Entry.comparingByValue())
                            .map(Map.Entry::getKey)
                            .orElse("");

                    return new RecomendacaoClienteDTO(nomeCliente, vinhoFavorito);
                })
                .collect(Collectors.toList());
    }



}
