package school.sptech.zup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.zup.domain.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
