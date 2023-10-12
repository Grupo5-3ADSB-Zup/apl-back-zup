package school.sptech.zup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.zup.domain.Comentario;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.response.ComentarioMobileResponse;

import java.util.Date;
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

    @Query("SELECT AVG(c.id) " +
            "FROM Comentario c " +
            "   JOIN Noticia n" +
            "       ON c.noticias.id = n.id" +
            "           WHERE n.id = :idNoticia")
    Integer findCountCommentNoticy(int idNoticia);

    @Query("SELECT c " +
            "FROM Comentario c " +
            "   JOIN FETCH Noticia n" +
            "       ON c.noticias.id = n.id" +
            "           JOIN FETCH Usuario u" +
            "               ON u.id = c.usuario.id" +
            "                   WHERE n.id = :idNoticia" +
            "                        ORDER BY u.influencer DESC, c.id DESC")
    List<Comentario> findCommentNoticy(int idNoticia);

    @Query("SELECT u " +
            "FROM Usuario u " +
            "   JOIN FETCH Comentario c" +
            "       ON u.id = c.usuario.id" +
            "           JOIN FETCH Noticia n" +
            "               ON c.noticias.id = n.id" +
            "                   WHERE n.id = :idNoticia" +
            "                        ORDER BY u.influencer DESC, c.id DESC")
    List<Usuario> findUsuariosComment(int idNoticia);


    @Query("SELECT c " +
            "FROM Comentario c " +
            "   JOIN FETCH Noticia n" +
            "       ON c.noticias.id = n.id" +
            "           JOIN FETCH Usuario u" +
            "               ON u.id = c.usuario.id" +
            "                   WHERE n.id = :idNoticia" +
            "                       AND c.dtComentario >= :startDate" +
            "                           ORDER BY u.influencer DESC, c.id DESC")
    List<Comentario> findCommentIA(Date startDate);
}
