//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;
//
//import org.springframework.stereotype.Component;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.MeasurementForm;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.BAgencyBoard;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Measurement;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.BAgencyBoardRepository;
//
//@Component
//public class MeasurementFormMapper implements Mapper<MeasurementForm, Measurement>{
//
//	private final BAgencyBoardRepository bAgencyBoardRepository;
//
//	public MeasurementFormMapper(BAgencyBoardRepository  bAgencyBoardRepository) {
//		this.bAgencyBoardRepository = bAgencyBoardRepository;
//
//	}
//
//	@Override
//	public Measurement map(MeasurementForm measurementForm) {
//		BAgencyBoard bAgencyBoard = bAgencyBoardRepository.findById(measurementForm.bAgencyBoardId())
//				.orElseThrow(() -> new EntityNotFoundException("Measurement não encontrado com o ID: " + measurementForm.bAgencyBoardId()));
//
//		Measurement measurement = new Measurement(
//			null,
//			measurementForm.height(),
//			measurementForm.length(),
//			bAgencyBoard
//		);
//
//		return measurement;
//	}
//}
