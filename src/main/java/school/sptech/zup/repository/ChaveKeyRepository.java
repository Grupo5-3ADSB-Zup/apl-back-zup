package school.sptech.zup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.zup.domain.ChaveKey;
import school.sptech.zup.domain.Noticia;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ChaveKeyRepository extends JpaRepository<ChaveKey, Long> {
    @Query("SELECT k FROM ChaveKey k " +
            "   ORDER BY k.id DESC")
    List<ChaveKey> UltimaChaveInserida();
}
