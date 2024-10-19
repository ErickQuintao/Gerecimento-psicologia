/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gerenciamentoPsicologico.projeto.controller;

import com.gerenciamentoPsicologico.projeto.model.Atendimento;
import com.gerenciamentoPsicologico.projeto.model.AtendimentoRepository;
import java.time.LocalDateTime;
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
        model.addAttribute("atendimento", new Atendimento()); // Adiciona um novo objeto Atendimento ao modelo
        return "cadastroAtendimento"; // Nome da página HTML para cadastrar atendimento
    }

@PostMapping("/salvar")
public ResponseEntity<String> salvarAtendimento(@RequestBody Atendimento atendimento) {
    try {
        // Certifique-se de que todos os campos necessários estão preenchidos
        if (atendimento.getHorarioInicial() == null) {
            atendimento.setHorarioInicial(LocalDateTime.now()); // Define um valor padrão se necessário
        }
        atendimentoRepository.save(atendimento);
        return ResponseEntity.ok("Atendimento cadastrado com sucesso!");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar o atendimento: " + e.getMessage());
    }
}


    @GetMapping("/listar")
    public String listarAtendimentos(Model model) {
        List<Atendimento> atendimentos = atendimentoRepository.findAll();
        model.addAttribute("atendimentos", atendimentos);
        return "listarAtendimentos"; // Nome do template HTML (listarAtendimentos.html)
    }

    @GetMapping("/detalhes/{id}")
    public String detalhesAtendimento(@PathVariable Long id, Model model) {
        Optional<Atendimento> optionalAtendimento = atendimentoRepository.findById(id);
        if (!optionalAtendimento.isPresent()) {
            return "redirect:/atendimentos/listar"; // Redireciona se o atendimento não for encontrado
        }
        Atendimento atendimento = optionalAtendimento.get();
        model.addAttribute("atendimento", atendimento);

        return "detalhesAtendimento"; // Nome da página HTML para detalhes do atendimento
    }

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
            return ResponseEntity.ok("Atendimento atualizado com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/excluir/{id}")
    @ResponseBody
    public ResponseEntity<String> excluirAtendimento(@PathVariable Long id) {
        try {
            atendimentoRepository.deleteById(id);
            return ResponseEntity.ok("Atendimento excluído com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir o atendimento: " + e.getMessage());
        }
    }
}
