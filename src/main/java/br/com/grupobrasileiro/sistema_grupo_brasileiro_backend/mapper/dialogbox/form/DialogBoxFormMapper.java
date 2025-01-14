package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.dialogbox.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.form.DialogBoxForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.DialogBox;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DialogBoxFormMapper implements Mapper<DialogBoxForm, DialogBox> {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BriefingRepository briefingRepository;

    @Override
    public DialogBox map(DialogBoxForm i) {
        Employee employee = employeeRepository.findById(i.idEmployee()).orElseThrow(
                () -> new EntityNotFoundException("Employee not found")
        );
        Briefing briefing = briefingRepository.findById(i.idBriefing()).orElseThrow(
                () -> new EntityNotFoundException("Briefing not found")
        );
        return new DialogBox(
                null,
                employee,
                briefing,
                LocalDateTime.now(),
                i.message()
        );
    }
}
