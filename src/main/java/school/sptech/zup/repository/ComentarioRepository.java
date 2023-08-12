package school.sptech.zup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.zup.domain.Comentario;
import school.sptech.zup.domain.Noticia;

import java.util.List;
import java.util.Optional;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findAllById(Long idNoticia);

    @Query("SELECT c FROM Noticia n " +
            "JOIN Comentario c" +
            "   ON c.noticias.id = n.id" +
            "       JOIN Usuario u" +
            "           ON u.id = c.usuario.id" +
            "               WHERE u.id = :idUsuario" +
            "                   AND n.id = :idNoticia")
    Optional<Comentario> buscarLikePorUsuario(Long idUsuario, int idNoticia);
}
