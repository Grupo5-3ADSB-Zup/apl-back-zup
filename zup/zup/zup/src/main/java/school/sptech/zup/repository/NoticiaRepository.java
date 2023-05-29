package school.sptech.zup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.zup.domain.Noticia;

import java.util.List;

public interface NoticiaRepository extends JpaRepository<Noticia, Integer> {

}
