package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.dialogbox;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.form.DialogBoxForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.view.DialogBoxView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.dialogbox.form.DialogBoxFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.dialogbox.view.DialogBoxViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.DialogBox;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.DialogBoxRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DialogBoxService {

    private static final Logger logger = LoggerFactory.getLogger(DialogBoxService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BriefingRepository briefingRepository;

    @Autowired
    private DialogBoxRepository dialogBoxRepository;

    @Autowired
    private DialogBoxFormMapper dialogBoxFormMapper;

    @Autowired
    private DialogBoxViewMapper dialogBoxViewMapper;

    public DialogBoxView createMessage(DialogBoxForm form) {
        logger.info("Iniciando o processo de criação de mensagem com dados: {}", form);
        DialogBox dialogBox = dialogBoxFormMapper.map(form);
        dialogBox = dialogBoxRepository.save(dialogBox);
        logger.info("Mensagem criada com sucesso: {}", dialogBox);
        return dialogBoxViewMapper.map(dialogBox);
    }

    /**
     * Recupera todas as mensagens associadas ao briefing com o ID fornecido.
     *
     * @param idBriefing o ID do briefing
     * @return uma lista de DTOs com os detalhes das mensagens
     */
    public Set<DialogBoxView> getMessagesByBriefingId(Long idBriefing) {
        logger.info("Buscando mensagens para o Briefing ID: {}", idBriefing);
        return dialogBoxRepository.findByBriefingId(idBriefing).stream().map(
            dialogBox -> dialogBoxViewMapper.map(dialogBox)
        ).collect(Collectors.toSet());
    }
}
