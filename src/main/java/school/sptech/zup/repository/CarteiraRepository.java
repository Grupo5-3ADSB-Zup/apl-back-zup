package school.sptech.zup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.zup.domain.Carteira;

public interface CarteiraRepository  extends JpaRepository<Carteira, Long> {
}
