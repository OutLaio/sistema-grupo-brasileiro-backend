package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.joao.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.agencyBoard.BAgencyBoardRegisterView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.agencyBoards.view.BAgencyBoardView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BAgencyBoardRegisterViewMapper implements Mapper<BAgencyBoard, BAgencyBoardRegisterView> {

    @Autowired
    private BAgencyBoardViewMapper bAgencyBoardViewMapper;

    @Autowired
    private ProjectViewMapper projectViewMapper;

    @Autowired
    private BriefingViewMapper briefingViewMapper;

    @Override
    public BAgencyBoardRegisterView map(BAgencyBoard bAgencyBoard) {


        Briefing briefing = bAgencyBoard.getBriefing();

        Project project = briefing != null ? briefing.getProject() : null;

        BAgencyBoardView bAgencyBoardView = bAgencyBoardViewMapper.map(bAgencyBoard);
        ProjectView projectView = project != null ? projectViewMapper.map(project) : null;
        BriefingView briefingView = briefing != null ? briefingViewMapper.map(briefing) : null;

        return new BAgencyBoardRegisterView(
                bAgencyBoardView,
                projectView,
                briefingView
        );
    }
}
