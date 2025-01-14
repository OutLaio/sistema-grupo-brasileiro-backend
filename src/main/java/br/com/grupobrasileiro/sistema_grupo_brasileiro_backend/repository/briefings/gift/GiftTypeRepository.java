package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.GiftType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GiftTypeRepository extends JpaRepository<GiftType,Long> {
}
