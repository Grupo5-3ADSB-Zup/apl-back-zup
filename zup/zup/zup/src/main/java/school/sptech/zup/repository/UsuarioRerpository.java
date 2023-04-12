package school.sptech.zup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.zup.domain.Usuario;

import java.util.Optional;

public interface UsuarioRerpository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);
}
