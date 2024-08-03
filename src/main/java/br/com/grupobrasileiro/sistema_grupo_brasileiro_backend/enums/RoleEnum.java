package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums;

public enum RoleEnum {
    ROLE_CLIENT(1, "Client"),
    ROLE_COLLABORATOR(2, "Collaborator"),
    ROLE_SUPERVISOR(3, "Supervisor");

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
