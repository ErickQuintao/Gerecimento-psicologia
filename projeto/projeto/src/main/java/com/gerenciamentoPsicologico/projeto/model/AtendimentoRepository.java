/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gerenciamentoPsicologico.projeto.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  // Essa anotação é opcional quando você estende JpaRepository, mas pode ser usada para garantir que o Spring o reconheça como um bean
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
    // Métodos personalizados podem ser adicionados aqui
}
