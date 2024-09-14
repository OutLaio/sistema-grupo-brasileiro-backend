package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.Mikaelle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.Mikaelle.form.DisableUserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.user.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EmailUniqueViolationException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.ProfileRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;

/**
 * Serviço responsável por gerenciar operações relacionadas a usuários.
 */
@Service
public class UserService {

    @Autowired
	private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; 


    @Autowired
	private ProfileRepository profileRepository;

    /**
     * Desativa um usuário no sistema.
     * 
     * Este método busca o usuário pelo ID fornecido no formulário e o desativa,
     * marcando o campo `disabled` como `true`. Se o usuário não for encontrado,
     * uma exceção é lançada.
     *
     * @param disableUserForm Formulário contendo o ID do usuário a ser desativado.
     * @throws RuntimeException Se o usuário não for encontrado.
     */
    @Transactional
    public void deactivateUser(DisableUserForm disableUserForm) {
        User user = userRepository.findById(disableUserForm.idUser())
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado, id: " + disableUserForm.idUser()));

        // Marca o usuário como desativado.
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
    public User createUser(UserForm userForm) {
        // Verifica se o e-mail já está em uso.
        if (userRepository.findByEmail(userForm.email()).isPresent()) {
            throw new EmailUniqueViolationException("Email já está em uso");
        }

        // Busca o perfil associado ao ID fornecido.
        Profile profile = profileRepository.findById(userForm.profile())
                .orElseThrow(() -> new EntityNotFoundException("Profile não encontrado"));

        // Cria uma nova instância de User e define seus atributos.
        User user = new User();
        user.setEmail(userForm.email());

        // Criptografa a senha fornecida e define no usuário.
        String encryptedPassword = passwordEncoder.encode(userForm.password());
        user.setPassword(encryptedPassword);

        // Define o perfil e marca o usuário como ativo (não desativado).
        user.setProfile(profile);
        user.setDisabled(false);

        // Salva o novo usuário no repositório.
        userRepository.save(user);

        return user;
    }

}
