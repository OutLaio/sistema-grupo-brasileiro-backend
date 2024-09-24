package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.AgencyBoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;


import com.github.javafaker.Faker;

import static org.assertj.core.api.Assertions.assertThat;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.AgencyBoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.AgencyBoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.AgencyBoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.AgencyBoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.AgencyBoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testa a classe AgencyBoardTypeViewMapper.
 * Verifica o mapeamento de AgencyBoardType para AgencyBoardTypeView.
 */
@ExtendWith(MockitoExtension.class)
public class AgencyBoardTypeViewMapperTest {

    private final Faker faker = new Faker();

    @InjectMocks
    private AgencyBoardTypeViewMapper mapper;

    /**
     * Testa o método map da classe AgencyBoardTypeViewMapper.
     * Verifica se o método mapeia corretamente um AgencyBoardType para um AgencyBoardTypeView.
     */
    @Test
    void deveMapearAgencyBoardTypeParaAgencyBoardTypeView() {
        // Gerando dados de teste utilizando o Java Faker
        Long idFalso = faker.number().randomNumber();
        String descricaoFalsa = faker.lorem().sentence();

        // Criando um AgencyBoardType com valores falsos
        AgencyBoardType agencyBoardType = new AgencyBoardType();
        agencyBoardType.setId(idFalso);
        agencyBoardType.setDescription(descricaoFalsa);

        // Mapeia o AgencyBoardType para AgencyBoardTypeView
        AgencyBoardTypeView result = mapper.map(agencyBoardType);

        // Verifica se o resultado não é nulo
        assertThat(result).isNotNull();

        // Verifica se o campo id foi mapeado corretamente
        assertThat(result.id()).isEqualTo(idFalso);

        // Verifica se o campo description foi mapeado corretamente
        assertThat(result.description()).isEqualTo(descricaoFalsa);
    }

    /**
     * Testa o método map da classe AgencyBoardTypeViewMapper com dados nulos.
     * Verifica se o método mapeia corretamente um AgencyBoardType com valores nulos para um AgencyBoardTypeView.
     */
    @Test
    void deveMapearAgencyBoardTypeComDadosNulosParaAgencyBoardTypeView() {
        // Criando um AgencyBoardType com valores nulos
        AgencyBoardType agencyBoardType = new AgencyBoardType();
        agencyBoardType.setId(null);
        agencyBoardType.setDescription(null);

        // Mapeia o AgencyBoardType para AgencyBoardTypeView
        AgencyBoardTypeView result = mapper.map(agencyBoardType);

        // Verifica se o resultado não é nulo
        assertThat(result).isNotNull();

        // Verifica se os campos foram mapeados corretamente com valores nulos
        assertThat(result.id()).isNull();
        assertThat(result.description()).isNull();
    }
    
    /**
     * Testa o método map da classe AgencyBoardTypeViewMapper com valores padrão.
     * Verifica se o método mapeia corretamente um AgencyBoardType com valores padrão para um AgencyBoardTypeView.
     */
    @Test
    void deveMapearAgencyBoardTypeComValoresPadraoParaAgencyBoardTypeView() {
        // Criando um AgencyBoardType com valores padrão
        AgencyBoardType agencyBoardType = new AgencyBoardType();
        agencyBoardType.setId(1L); // Supondo um valor padrão
        agencyBoardType.setDescription("Descrição padrão"); // Supondo um valor padrão

        // Mapeia o AgencyBoardType para AgencyBoardTypeView
        AgencyBoardTypeView result = mapper.map(agencyBoardType);

        // Verifica se o resultado não é nulo
        assertThat(result).isNotNull();

        // Verifica se o campo id foi mapeado corretamente
        assertThat(result.id()).isEqualTo(1L);

        // Verifica se o campo description foi mapeado corretamente
        assertThat(result.description()).isEqualTo("Descrição padrão");
    }

}