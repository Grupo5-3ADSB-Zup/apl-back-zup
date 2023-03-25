package school.sptech.zup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.zup.domain.Usuario;

public interface UsuarioRerpository extends JpaRepository<Usuario, Long> {

}
