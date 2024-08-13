package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;

@Component
public class UserConverter {

    @Autowired
    private UserRepository userRepository; 

    public Set<User> converter(Set<Integer> userIds) {
        return userIds.stream()
                      .map(id -> userRepository.findById(id.longValue())
                                               .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado.")))
                      .collect(Collectors.toSet());
    }
}
