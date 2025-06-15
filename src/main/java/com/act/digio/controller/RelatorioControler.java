package com.act.digio.controller;

import com.act.digio.dto.CompraDTO;
import com.act.digio.dto.VendaDTO;
import com.act.digio.service.ProdutoService;
import com.act.digio.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import util.HeaderUtil;

import java.util.List;

@RequestMapping("/")
@Controller
public class RelatorioControler {
    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private VendaService vendaService;

    @RequestMapping(value="/compras", method = RequestMethod.GET)
    public ResponseEntity<List<CompraDTO>>  getCompras(){
        try{
            List<CompraDTO> compras = vendaService.getCompras();
            return new ResponseEntity<>(compras, HttpStatus.OK);
        }catch (Exception e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("Relatorio",
                    e.getMessage(),
                    "Algo deu errado, tente novamente mais tarde.")).body(null);
        }
    }

    @RequestMapping(value="/maior_compra/{anoCompra}", method = RequestMethod.GET)
    public ResponseEntity<CompraDTO> getMaiorCompra(@PathVariable Integer anoCompra){
        try{
            CompraDTO compras = vendaService.getMaiorCompra(anoCompra);
            return new ResponseEntity<>(compras, HttpStatus.OK);
        }catch (Exception e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("Relatorio",
                    e.getCause().getMessage(),
                    "Algo deu errado, tente novamente mais tarde.")).body(null);
        }
    }
}
