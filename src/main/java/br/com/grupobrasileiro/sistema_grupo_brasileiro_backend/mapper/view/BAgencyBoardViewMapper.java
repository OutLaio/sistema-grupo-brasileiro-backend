//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view;
//
//import org.springframework.stereotype.Component;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.BAgencyBoardView;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.BAgencyBoard;
//
//@Component
//public class BAgencyBoardViewMapper implements Mapper<BAgencyBoard, BAgencyBoardView> {
//
//    /**
//     * Converte um objeto BAgencyBoard em uma BAgencyBoardView.
//     * @param bAgencyBoard - entidade BAgencyBoard.
//     * @return BAgencyBoardView
//     */
//    @Override
//    public BAgencyBoardView map(BAgencyBoard i) {
//        return new BAgencyBoardView(
//			i.getId(),
//			i.getBoardLocation(),
//			i.getCompanySharing(),
//			i.getBoardType(),
//			i.getMaterial(),
//			i.getObservations(),
//			i.getProject().getId()
//        );
//    }
//}
