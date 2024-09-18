package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BSignpostTest {

    private BSignpost bSignpost;
    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();

        // Criando instância de BSignpost
        bSignpost = new BSignpost();
        bSignpost.setId(faker.number().randomNumber());
        bSignpost.setBoardLocation(faker.address().streetAddress());
        bSignpost.setSector(faker.company().industry());

        // Assumindo que você tem uma classe Material e Briefing configuradas
        Material material = new Material();
        material.setId(faker.number().randomNumber());
        material.setDescription(faker.commerce().material());

        Briefing briefing = new Briefing();
        briefing.setId(faker.number().randomNumber());

        bSignpost.setMaterial(material);
        bSignpost.setBriefing(briefing);
    }

    @Test
    void testBSignpostCreation() {
        // Verifica se o ID foi gerado corretamente
        assertThat(bSignpost.getId()).isNotNull();

        // Verifica se a localização do quadro e o setor foram atribuídos corretamente
        assertThat(bSignpost.getBoardLocation()).isNotNull().isNotEmpty();
        assertThat(bSignpost.getSector()).isNotNull().isNotEmpty();

        // Verifica se o material e o briefing foram atribuídos corretamente
        assertThat(bSignpost.getMaterial()).isNotNull();
        assertThat(bSignpost.getBriefing()).isNotNull();
    }

    @Test
    void testBSignpostEquality() {
        // Criando uma segunda instância com o mesmo ID
        BSignpost anotherBSignpost = new BSignpost();
        anotherBSignpost.setId(bSignpost.getId());
        anotherBSignpost.setBoardLocation(bSignpost.getBoardLocation());
        anotherBSignpost.setSector(bSignpost.getSector());
        anotherBSignpost.setMaterial(bSignpost.getMaterial());
        anotherBSignpost.setBriefing(bSignpost.getBriefing());

        // Verifica se as instâncias com o mesmo ID e atributos são consideradas iguais
        assertThat(bSignpost).isEqualTo(anotherBSignpost);
    }

    @Test
    void testBSignpostToString() {
        // Verifica o método toString
        String bSignpostString = bSignpost.toString();
        assertThat(bSignpostString).contains(bSignpost.getId().toString());
    }
}
