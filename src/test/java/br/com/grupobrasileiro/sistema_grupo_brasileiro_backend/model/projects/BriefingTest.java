package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

    public class BriefingTest {

        /**
         * Testa o construtor padrão da classe Briefing.
         * Verifica se o construtor padrão cria uma instância não nula da classe.
         */
        @Test
        void testDefaultConstructor() {
            Briefing briefing = new Briefing();
            assertThat(briefing).isNotNull();
        }

        /**
         * Testa o construtor parametrizado da classe Briefing.
         * Verifica se o construtor com parâmetros define corretamente os atributos startTime, expectedDate e detailedDescription.
         */
        @Test
        void testParameterizedConstructor() {
            LocalDateTime startTime = LocalDateTime.now();
            LocalDateTime expectedTime = LocalDateTime.now().plusDays(10);
            String detailedDescription = "Briefing description";

            Briefing briefing = new Briefing();
            briefing.setStartTime(startTime);
            briefing.setExpectedTime(expectedTime);
            briefing.setDetailedDescription(detailedDescription);

            assertThat(briefing.getStartTime()).isEqualTo(startTime);
            assertThat(briefing.getExpectedTime()).isEqualTo(expectedTime);
            assertThat(briefing.getDetailedDescription()).isEqualTo(detailedDescription);
        }

        /**
         * Testa os métodos setters e getters da classe Briefing.
         * Verifica se os métodos setStartTime, setExpectedTime e setDetailedDescription definem corretamente os atributos
         * e se os métodos getStartTime, getExpectedTime e getDetailedDescription retornam os valores esperados.
         */
        @Test
        void testSettersAndGetters() {
            Briefing briefing = new Briefing();
            LocalDateTime startTime = LocalDateTime.now();
            LocalDateTime expectedTime = LocalDateTime.now().plusDays(10);
            String detailedDescription = "Briefing description";

            briefing.setStartTime(startTime);
            briefing.setExpectedTime(expectedTime);
            briefing.setDetailedDescription(detailedDescription);

            assertThat(briefing.getStartTime()).isEqualTo(startTime);
            assertThat(briefing.getExpectedTime()).isEqualTo(expectedTime);
            assertThat(briefing.getDetailedDescription()).isEqualTo(detailedDescription);
        }

        /**
         * Testa os métodos equals e hashCode da classe Briefing.
         * Verifica se duas instâncias com os mesmos valores de startTime, expectedDate e detailedDescription são iguais
         * e se têm o mesmo hashCode.
         */
        @Test
        void testEqualsAndHashCode() {
            LocalDateTime startTime = LocalDateTime.now();
            LocalDateTime expectedTime = LocalDateTime.now().plusDays(10);
            String detailedDescription = "Briefing description";

            Briefing briefing1 = new Briefing();
            briefing1.setStartTime(startTime);
            briefing1.setExpectedTime(expectedTime);
            briefing1.setDetailedDescription(detailedDescription);

            Briefing briefing2 = new Briefing();
            briefing2.setStartTime(startTime);
            briefing2.setExpectedTime(expectedTime);
            briefing2.setDetailedDescription(detailedDescription);

            Briefing briefing3 = new Briefing();
            briefing3.setStartTime(startTime.plusDays(1)); // Instância com startTime diferente
            briefing3.setExpectedTime(expectedTime);
            briefing3.setDetailedDescription(detailedDescription);

            assertThat(briefing1).isEqualTo(briefing2);
            assertThat(briefing1.hashCode()).isEqualTo(briefing2.hashCode());

            // Verifica que objetos diferentes não são iguais
            assertThat(briefing1).isNotEqualTo(briefing3);
            assertThat(briefing1.hashCode()).isNotEqualTo(briefing3.hashCode());
        }

        /**
         * Testa o método toString da classe Briefing.
         * Verifica se o método toString retorna uma representação correta da instância
         * com os valores de startTime, expectedDate e detailedDescription.
         */
        @Test
        void testToString() {
            LocalDateTime startTime = LocalDateTime.now();
            LocalDateTime expectedTime = LocalDateTime.now().plusDays(10);
            String detailedDescription = "Briefing description";

            Briefing briefing = new Briefing();
            briefing.setStartTime(startTime);
            briefing.setExpectedTime(expectedTime);
            briefing.setDetailedDescription(detailedDescription);

            String expectedToString = "Briefing(id=null, project=null, briefingType=null, startTime=" + startTime + 
                                      ", expectedDate=" + expectedTime +
                                      ", finishTime=null, detailedDescription=" + detailedDescription +
                                      ", otherCompany=null, versions=[], dialogs=[], measurement=null, " +
                                      "companies=[], printed=null, gift=null, agencyBoard=null, sticker=null, " +
                                      "signpost=null, handout=null, internalCampaign=null)";

            assertThat(briefing.toString()).contains(expectedToString);
        }
    }

