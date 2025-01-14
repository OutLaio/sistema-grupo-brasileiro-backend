package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums;

import lombok.Getter;

@Getter
public enum ProjectStatusEnum {
    TO_DO("A fazer"),
    IN_PROGRESS("Em Andamento"),
    WAITING_APPROVAL("Aguardando Aprovação"),
    APPROVED("Aprovado"),
    IN_PRODUCTION("Em Confecção"),
    COMPLETED("Concluído"),
    STAND_BY("Em espera");

    private final String description;

    ProjectStatusEnum(String description) {
        this.description = description;
    }
}
