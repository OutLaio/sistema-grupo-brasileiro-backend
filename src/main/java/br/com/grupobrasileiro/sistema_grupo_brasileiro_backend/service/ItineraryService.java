//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ItineraryForm;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ItineraryView;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form.ItineraryFormMapper;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.ItineraryViewMapper;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Itinerary;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.ItineraryRepository;
//
//@Service
//public class ItineraryService {
//
//    @Autowired
//    private ItineraryRepository itineraryRepository;
//
//    @Autowired
//    private ItineraryFormMapper itineraryFormMapper;
//
//    @Autowired
//    private ItineraryViewMapper itineraryViewMapper;
//
//    @Transactional
//    public void save(ItineraryForm itineraryForm) {
//        Itinerary itinerary = itineraryFormMapper.map(itineraryForm);
//        itineraryRepository.save(itinerary);
//    }
//
//    @Transactional(readOnly = true)
//    public ItineraryView getItineraryById(Long id) {
//        Itinerary itinerary = itineraryRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Itinerary not found with id: " + id));
//        return itineraryViewMapper.map(itinerary);
//    }
//
//    @Transactional
//    public ItineraryView updateItinerary(Long id, ItineraryForm form) {
//        Itinerary itinerary = itineraryRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Itinerary not found with id: " + id));
//
//        // Atualiza os campos da entidade
//        itinerary.setMain(form.main());
//        itinerary.setConnections(form.connections());
//
//        Itinerary updatedItinerary = itineraryRepository.save(itinerary);
//        return itineraryViewMapper.map(updatedItinerary);
//    }
//
//    @Transactional
//    public Page<ItineraryView> getAllItineraries(PageRequest pageRequest) {
//        Page<Itinerary> itinerariesPage = itineraryRepository.findAll(pageRequest);
//        return itinerariesPage.map(itineraryViewMapper::map);
//    }
//}
