package com.act.digio.controller;

import com.act.digio.dto.ComprasDTO;
import com.act.digio.entity.Produto;
import com.act.digio.entity.Venda;
import com.act.digio.repository.ProdutoRepository;
import com.act.digio.service.ProdutoService;
import com.act.digio.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<List<Venda>>  getCompras(){
        try{
            List<Venda> compras = vendaService.getCompras();
            return new ResponseEntity<List<Venda>>(compras, HttpStatus.OK);
        }catch (Exception e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("Relatorio", e.getCause().getMessage(), e.getMessage())).body(null);
        }
    }
}
