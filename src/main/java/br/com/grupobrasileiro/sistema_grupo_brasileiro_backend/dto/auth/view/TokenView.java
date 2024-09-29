package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.UserDetailsView;

public record TokenView(
        String token,
        UserDetailsView userView
) {
}
