package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums;

public enum RoleEnum {
	ADMIN("admin"),
    CLIENT("user");

    private String role;

    RoleEnum(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}