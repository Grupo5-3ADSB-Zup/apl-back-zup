package school.sptech.zup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.zup.domain.Noticia;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

public interface NoticiaRepository extends JpaRepository<Noticia, Integer> {
    Optional<Noticia> findByTitulo(String titulo);
    @Query("SELECT n FROM Noticia n " +
            "   WHERE n.dtNoticia >= :startDate" +
            "   ORDER BY n.dtNoticia DESC")
    List<Noticia> listagemNoticias(Date startDate);
}
