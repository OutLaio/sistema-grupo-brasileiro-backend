package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.BAgencyBoard.register;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

/**
 * Representa um formulário para registrar ou atualizar informações relacionadas ao BAgencyBoard.
 *
 * <p>Este registro contém informações sobre o BAgencyBoard, medidas, rotas e outras rotas.</p>
 */
public record RegistrationForm(

        /**
         * Formulário para informações do BAgencyBoard.
         * Este campo é obrigatório e não pode ser nulo.
         */
        @NotNull(message = "{registrationForm.bAgencyBoardForm.required}")
        BAgencyBoardForm bAgencyBoardForm,

        /**
         * Formulário para medidas.
         * Este campo é obrigatório e não pode ser nulo.
         */
        @NotNull(message = "{registrationForm.measurementsForm.required}")
        MeasurementsForm measurementsForm,

        /**
         * Lista de formulários de rotas.
         * Esta lista pode ser nula, mas se fornecida, não deve conter elementos nulos.
         */
        @Valid
        List<@NotNull(message = "{registrationForm.routesForms.itemNotNull}") RoutesForm> routesForms,

        /**
         * Lista de formulários de outras rotas.
         * Esta lista pode ser nula, mas se fornecida, não deve conter elementos nulos.
         */
        @Valid
        List<@NotNull(message = "{registrationForm.otherRoutesForms.itemNotNull}") OtherRoutesForm> otherRoutesForms

) {
}
