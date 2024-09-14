package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.Mikaelle;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.Mikaelle.form.PasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;

@Service
public class PasswordService {

    @Autowired
    private UserRepository userRepository;

    // @Autowired
    // private PasswordEncoder passwordEncoder;

    /**
     * Redefine a senha de um usuário.
     *
     * Este método encontra o usuário pelo seu ID e atualiza sua senha com uma nova senha
     * criptografada utilizando o `PasswordEncoder`. Se o usuário não for encontrado,
     * uma exceção `EntityNotFoundException` é lançada.
     *
     * @param id O ID do usuário que terá a senha redefinida.
     * @param newPassword A nova senha a ser definida para o usuário.
     * @throws EntityNotFoundException Se o usuário não for encontrado pelo ID fornecido.
     */
    public void resetPassword(PasswordForm passwordForm) {

        // Busca o usuário pelo ID, lançando exceção se não for encontrado.
        User user = userRepository.findById(passwordForm.id())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        // Criptografa a nova senha e a define no usuário.
        // user.setPassword(passwordEncoder.encode(passwordForm.passwordNew()));

        // Salva as alterações no banco de dados.
        userRepository.save(user);
    }
}

