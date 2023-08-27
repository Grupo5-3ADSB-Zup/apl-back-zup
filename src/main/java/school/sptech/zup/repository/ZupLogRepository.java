package school.sptech.zup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.zup.domain.ZupLog;

public interface ZupLogRepository extends JpaRepository<ZupLog, Long> {

}
