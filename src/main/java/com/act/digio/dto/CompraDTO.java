package com.act.digio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CompraDTO {
    private String nome;
    private String cpf;
    private String codigo;
    private String tipoVinho;
    private Double preco;
    private Integer safra;
    private Integer anoCompra;
    private Integer quantidade;
    private Double valorTotal;
}
