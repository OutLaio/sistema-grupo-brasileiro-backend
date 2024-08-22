package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums;

public enum RoleEnum {
	ROLE_SUPERVISOR(0, "Supervisor"),
	ROLE_COLLABORATOR(1, "Collaborator"),
    ROLE_CLIENT(2, "Client");

    private Integer code;
    private String description;

    RoleEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static RoleEnum fromCode(Integer code) {
        for (RoleEnum role : RoleEnum.values()) {
            if (role.getCode() == code) {
                return role;
            }
        }

        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
