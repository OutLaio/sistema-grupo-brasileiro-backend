package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.CompanyForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.CompanyView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.CompanyAlreadyExistsException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form.CompanyFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.CompanyViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.CompanyRepository;


@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private CompanyFormMapper companyFormMapper;
	
	@Autowired
	private CompanyViewMapper companyViewMapper;
	
	@Transactional
	public Page<CompanyView> companyAll(PageRequest pageRequest) {
		Page<Company> projectPage = companyRepository.findAll(pageRequest);
		return projectPage.map(companyViewMapper::map);
	}
	
	@Transactional
	public CompanyView save(CompanyForm form) {
		Company entity = companyFormMapper.map(form);
		
		if(companyRepository.findByName(form.name().toUpperCase())!= null) {
			throw new CompanyAlreadyExistsException("Uma empresa com o nome " + form.name() + " já existe.");
		}
		companyRepository.save(entity);
		return companyViewMapper.map(entity);
	}
}
