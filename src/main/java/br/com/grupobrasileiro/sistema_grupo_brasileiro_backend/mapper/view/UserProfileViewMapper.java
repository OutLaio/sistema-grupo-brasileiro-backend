//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view;
//
//import org.springframework.stereotype.Component;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserProfileView;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
//
//@Component
//public class UserProfileViewMapper implements Mapper<User, UserProfileView> {
//    @Override
//    public UserProfileView map(User i) {
//        return new UserProfileView(
//            i.getId(),
//            i.getName(),
//            i.getLastname(),
//            i.getPhonenumber(),
//            i.getSector(),
//            i.getOccupation(),
//            i.getNop(),
//            i.getEmail()
//        );
//    }
//}
