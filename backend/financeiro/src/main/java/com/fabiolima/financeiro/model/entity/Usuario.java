package com.fabiolima.financeiro.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "usuario", schema = "financas")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;
}
