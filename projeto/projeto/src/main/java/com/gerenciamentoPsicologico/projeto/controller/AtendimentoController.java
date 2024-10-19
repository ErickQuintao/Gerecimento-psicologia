/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gerenciamentoPsicologico.projeto.controller;

import com.gerenciamentoPsicologico.projeto.model.Atendimento;
import java.util.ArrayList;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author erick
 */
public class AtendimentoController {
    // eu so preciso preencher direito aqui quando eu for mexer com db
     private List<Atendimento> atendimentos = new ArrayList<>();
    
    @GetMapping("/cadastroAtendimento")
public String mostrarFormularioCadastro(Model model) {
    model.addAttribute("atendimento", new Atendimento()); // Adiciona um novo objeto Filme ao modelo
    return "cadastro-filme"; // Nome da página HTML para cadastrar filme
}
     @GetMapping("/listarAtendimento")
    public String listarFilmes(Model model) {
        model.addAttribute("atendimentos", atendimentos); // Adiciona a lista de filmes ao modelo
        return "listarAtendimento"; // Nome da página HTML para listar filmes
    }
}
