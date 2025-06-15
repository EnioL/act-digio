package com.act.digio.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Venda {
    private String nome;
    private String cpf;
    private List<Compra> compras;
}
