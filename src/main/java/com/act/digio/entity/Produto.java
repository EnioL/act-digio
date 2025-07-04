package com.act.digio.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Produto {
    private String codigo;
    @JsonAlias("tipo_vinho")
    private String tipoVinho;
    private Double preco;
    private Integer safra;
    @JsonAlias("ano_compra")
    private Integer anoCompra;
}
