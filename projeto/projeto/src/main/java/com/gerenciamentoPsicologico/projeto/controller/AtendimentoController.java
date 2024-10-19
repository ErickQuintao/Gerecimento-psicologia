/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gerenciamentoPsicologico.projeto.controller;

import com.gerenciamentoPsicologico.projeto.model.Atendimento;
import com.gerenciamentoPsicologico.projeto.model.AtendimentoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author erick
 */
public class AtendimentoController {
    @Autowired
    private AtendimentoRepository atendimentoRepository;
    
    @GetMapping("/cadastro")
public String mostrarFormularioCadastro(Model model) {
    model.addAttribute("atendimento", new Atendimento()); // Adiciona um novo objeto Filme ao modelo
    return "cadastro-filme"; // Nome da página HTML para cadastrar filme
}

   @PostMapping("/salvar")
    public ResponseEntity<String> salvarFilme(@RequestBody Atendimento atendimento) {
        try {
            atendimentoRepository.save(atendimento);
            return ResponseEntity.ok("Filme cadastrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar o atendimento: " + e.getMessage());
        }
    }

     // Listar filmes
    @GetMapping("/listar")
    public String listarFilmes(Model model) {
        List<Atendimento> atendimentos = atendimentoRepository.findAll();
        model.addAttribute("atendimentos", atendimentos);
        return "listarAtendimento"; // Retorna a tela de listar
    }

    @GetMapping("/detalhes/{id}")
    public String detalhesFilme(@PathVariable Long id, Model model) {
        Optional<Filme> optionalFilme = filmeRepository.findById(id);
        if (!optionalFilme.isPresent()) {
            return "redirect:/filmes/listar"; // Redireciona se o filme não for encontrado
        }
        Filme filme = optionalFilme.get();
        List<Analise> analisesFilme = analiseRepository.findByFilmeId(filme.getId());
        model.addAttribute("filme", filme);
        model.addAttribute("analises", analisesFilme);
        return "detalhes-filme"; // Nome da página HTML para detalhes do filme
    }

    // Atualizar filme
    @PutMapping("/atualizar/{id}")
    @ResponseBody
    public ResponseEntity<String> atualizarFilme(@PathVariable Long id, @RequestBody Filme updatedFilme) {
        Optional<Filme> optionalFilme = filmeRepository.findById(id);
        if (optionalFilme.isPresent()) {
            Filme filme = optionalFilme.get();
            filme.setTitulo(updatedFilme.getTitulo());
            filme.setSinopse(updatedFilme.getSinopse());
            filme.setGenero(updatedFilme.getGenero());
            filme.setAnoLancamento(updatedFilme.getAnoLancamento());
            filmeRepository.save(filme);
            return ResponseEntity.ok("Filme atualizado com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Adicionar análise ao filme
    @PostMapping("/{id}/analise/adicionar")
    @ResponseBody
    public ResponseEntity<String> adicionarAnalise(@PathVariable Long id, @RequestBody Analise analise) {
        Optional<Filme> optionalFilme = filmeRepository.findById(id);
        if (!optionalFilme.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Filme filme = optionalFilme.get();
        analise.setFilme(filme);
        analiseRepository.save(analise);
        return ResponseEntity.ok("Análise adicionada com sucesso!");
    }

    // Excluir filme
    @DeleteMapping("/excluir/{id}")
    @ResponseBody
    public ResponseEntity<String> excluirFilme(@PathVariable Long id) {
        filmeRepository.deleteById(id);
        return ResponseEntity.ok("Filme excluído com sucesso!");
    }
}
