package com.fabiolima.financeiro.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutenticarDTO {
    private String email;
    private String senha;
}
