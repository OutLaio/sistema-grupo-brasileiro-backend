package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums;

public enum ProjectStatusEnum {
    A_FAZER("AF", "A Fazer"),
    EM_ANDAMENTO("EA", "Em Andamento"),
	A_APROVAR("AA", "Aguardando Aprovação"),
	APROVADO("AP", "Aprovado"),
	EM_CONFEC("EC", "Em Confecção"),
	CONCLUIDO("CO", "Concluído");

    private String code;
    private String description;

    ProjectStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ProjectStatusEnum fromCode(String code) {
        for (ProjectStatusEnum role : ProjectStatusEnum.values()) {
            if (role.getCode().equals(code)) {
                return role;
            }
        }

        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
