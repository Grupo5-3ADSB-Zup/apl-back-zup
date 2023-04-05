package school.sptech.zup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.zup.domain.Noticia;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {
}
