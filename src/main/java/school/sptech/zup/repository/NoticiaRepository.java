package school.sptech.zup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.zup.domain.Noticia;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

public interface NoticiaRepository extends JpaRepository<Noticia, Integer> {
    Optional<Noticia> findByTitulo(String titulo);

    @Query("SELECT n FROM Noticia n WHERE n.dtNoticia >= :startDate")
    List<Noticia> listagemNoticias(LocalDateTime startDate);

    @Query("SELECT COUNT(n.likes) FROM Noticia n " +
            "JOIN Comentario c" +
            "   ON c.noticias.id = n.id" +
            "       JOIN Usuario u" +
            "           ON u.id = c.usuario.id" +
            "               WHERE n.id = :idUsuario" +
            "                   AND n.id = :idNoticia")

    Long buscarLikePorUsuario(Long idUsuario, int idNoticia);
}
