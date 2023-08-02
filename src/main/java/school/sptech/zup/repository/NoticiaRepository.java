package school.sptech.zup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.zup.domain.Noticia;

import java.util.List;
import java.util.Optional;

public interface NoticiaRepository extends JpaRepository<Noticia, Integer> {
    Optional<Noticia> findByTitulo(String titulo);
}
