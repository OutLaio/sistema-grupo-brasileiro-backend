package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardRegisterView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.BriefingViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;

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
