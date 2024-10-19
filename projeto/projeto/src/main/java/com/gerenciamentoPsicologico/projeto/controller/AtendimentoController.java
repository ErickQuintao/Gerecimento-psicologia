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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/atendimentos")
public class AtendimentoController {
    @Autowired
    private AtendimentoRepository atendimentoRepository;
    
    @GetMapping("/cadastro")
public String mostrarFormularioCadastro(Model model) {
    model.addAttribute("atendimento", new Atendimento()); // Adiciona um novo objeto Filme ao modelo
    return "cadastro-filme"; // Nome da página HTML para cadastrar filme
}

   @PostMapping("/salvar")
    public ResponseEntity<String> salvarAtendimento(@RequestBody Atendimento atendimento) {
        try {
            atendimentoRepository.save(atendimento);
            return ResponseEntity.ok("Filme cadastrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar o atendimento: " + e.getMessage());
        }
    }

     // Listar filmes
    @GetMapping("/listar")
    public String listarAtendimentos(Model model) {
        List<Atendimento> atendimentos = atendimentoRepository.findAll();
        model.addAttribute("atendimentos", atendimentos);
        return "listarAtendimento"; // Retorna a tela de listar
    }

    @GetMapping("/detalhes/{id}")
    public String detalhesAtendimento(@PathVariable Long id, Model model) {
        Optional<Atendimento> optionalAtendimento = atendimentoRepository.findById(id);
        if (!optionalAtendimento.isPresent()) {
            return "redirect:/atendimentos/listar"; // Redireciona se o filme não for encontrado
        }
        Atendimento atendimento = optionalAtendimento.get();
        model.addAttribute("atendimento", atendimento);

        return "detalhesAtendimento"; // Nome da página HTML para detalhes do filme
    }

    // Atualizar filme
    @PutMapping("/atualizar/{id}")
    @ResponseBody
    public ResponseEntity<String> atualizarAtendimento(@PathVariable Long id, @RequestBody Atendimento updatedAtendimento) {
        Optional<Atendimento> optionalAtendimento = atendimentoRepository.findById(id);
        if (optionalAtendimento.isPresent()) {
            Atendimento atendimento = optionalAtendimento.get();
            atendimento.setNome(updatedAtendimento.getNome());
            atendimento.setHorarioInicial(updatedAtendimento.getHorarioInicial());
            atendimento.setHorarioFinal(updatedAtendimento.getHorarioFinal());
            atendimento.setValor(updatedAtendimento.getValor());
            atendimento.setDesconto(updatedAtendimento.getDesconto());
            atendimento.setValorTotal(updatedAtendimento.getValorTotal());
            atendimento.setMetodoPagamento(updatedAtendimento.getMetodoPagamento());
            atendimento.setConvenio(updatedAtendimento.getConvenio());
            atendimento.setDescricao(updatedAtendimento.getDescricao());
            atendimentoRepository.save(atendimento);
            return ResponseEntity.ok("atendimento atualizado com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Excluir filme
    @DeleteMapping("/excluir/{id}")
    @ResponseBody
    public ResponseEntity<String> excluirAtendimento(@PathVariable Long id) {
        atendimentoRepository.deleteById(id);
        return ResponseEntity.ok("atendimento excluído com sucesso!");
    }
}
