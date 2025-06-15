package com.act.digio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class VendaDTO {
    private String nome;
    private String cpf;
    private List<CompraDTO> compras;
}
