package school.sptech.zup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.zup.domain.Comentario;

import java.util.List;
import java.util.Optional;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findAllById(Long idNoticia);
    @Query("SELECT c " +
            "FROM Noticia n " +
            "   JOIN FETCH Comentario c" +
            "       ON c.noticias.id = n.id" +
            "           JOIN FETCH   Usuario u" +
            "               ON u.id = c.usuario.id" +
            "                   WHERE u.id = :idUsuario" +
            "                       AND n.id = :idNoticia" +
            "                           ORDER BY u.influencer DESC, c.id DESC")
    List<Comentario> findFirstCommentWithLimit(Long idUsuario, int idNoticia);
    @Query("SELECT c " +
            "FROM Comentario c " +
            "       ORDER BY c.id DESC")
    List<Comentario> findComment();
}

