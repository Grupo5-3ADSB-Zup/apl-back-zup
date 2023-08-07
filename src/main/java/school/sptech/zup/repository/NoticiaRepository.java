package school.sptech.zup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.zup.domain.Noticia;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NoticiaRepository extends JpaRepository<Noticia, Integer> {
    Optional<Noticia> findByTitulo(String titulo);

    @Query("SELECT n FROM Noticia n WHERE n.dtNoticia >= :startDate")
    List<Noticia> listagemNoticias(LocalDateTime startDate);
}
