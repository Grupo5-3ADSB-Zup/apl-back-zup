package school.sptech.zup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.zup.domain.Comentario;
import school.sptech.zup.domain.Curtida;

import java.util.List;
import java.util.Optional;

public interface CurtidaRepository extends JpaRepository<Curtida, Long> {
    @Query("SELECT c " +
            "FROM Curtida c " +
            "   JOIN FETCH Noticia n" +
            "       ON c.noticias.id = n.id" +
            "           JOIN FETCH Usuario u" +
            "               ON u.id = c.usuario.id" +
            "                   WHERE u.id = :idUsuario" +
            "                       AND n.id = :idNoticia" +
            "                           ORDER BY c.id DESC")
    Curtida findFirstLikeWithLimit(Long idUsuario, int idNoticia);
}
