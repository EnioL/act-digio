package com.act.digio.dto;

import com.act.digio.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class RecomendacaoClienteDTO {
    private String nome;
    private String recomendacao;
}
