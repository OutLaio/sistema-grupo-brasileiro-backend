package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.sticker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker.BSticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BStickerRepository extends JpaRepository<BSticker,Long> {
}
