package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.PasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EmailUniqueViolationException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.IncorrectPasswordException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.form.UserFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.ProfileRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço responsável por gerenciar operações relacionadas a usuários.
 */
@Service
public class UserService {

    @Autowired
	private UserRepository userRepository;

    @Autowired
    private UserFormMapper userFormMapper;

    @Autowired
	private ProfileRepository profileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Desativa um usuário no sistema.
     * 
     * Este método busca o usuário pelo ID fornecido e o desativa,
     * marcando o campo `disabled` como `true`. Se o usuário não for encontrado,
     * uma exceção é lançada.
     *
     * @param id ID do usuário a ser desativado.
     * @throws RuntimeException Se o usuário não for encontrado.
     */
    @Transactional
    public void deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado, id: " + id));

        user.setDisabled(true);
        userRepository.save(user);
    }


 /**
     * Cria um novo usuário no sistema.
     * <p>
     * O método verifica se o e-mail já está em uso e, se não, cria um novo usuário
     * com os dados fornecidos no formulário, criptografando a senha antes de
     * salvá-lo no banco de dados.
     * 
     * @param userForm Formulário contendo os dados do novo usuário.
     * @return O usuário recém-criado.
     * @throws EmailUniqueViolationException Se o e-mail já estiver em uso.
     * @throws EntityNotFoundException Se o perfil associado ao usuário não for encontrado.
     */
    public User create(UserForm userForm) {
        if (userRepository.findByEmail(userForm.email()).isPresent()) {
            throw new EmailUniqueViolationException("Email já está em uso");
        }
        User user = userFormMapper.map(userForm);
        userRepository.save(user);
        return user;
    }

    public void changePassword(PasswordForm passwordForm) {
        User user = userRepository.findById(passwordForm.id())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        String currentPassword = passwordEncoder.encode(passwordForm.currentPassword());
        if(!user.getPassword().equals(currentPassword)) throw new IncorrectPasswordException("A senha atual está incorreta!");
        user.setPassword(passwordEncoder.encode(passwordForm.newPassword()));
        userRepository.save(user);
    }
}
