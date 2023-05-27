package school.sptech.zup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.zup.domain.Comentario;
import school.sptech.zup.domain.Noticia;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findAllById(Long idNoticia);
}
