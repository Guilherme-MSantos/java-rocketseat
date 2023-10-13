package br.com.guilhermemoreirasantos.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @Controller //* */ Dá uma flexibilidade maior podendo retornar templates e etc
@RestController // Para quando se cria uma api
@RequestMapping("/primeiraRota")

public class PrimeiraController {

    /**
     * GET - Buscar uma informação
     * POST- Adicionar um dado/informação
     * PUT - Alterar dado/info
     * DELETE - Remover um dado
     * PATCH - Alterar somente uma parte da info/dado
     */

    @GetMapping("/primeiroMetodo")
    public String primeiraMensagem() {
        return "Olá mundo";
    }
}
