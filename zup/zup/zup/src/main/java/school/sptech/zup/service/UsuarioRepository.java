package school.sptech.zup.service;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.zup.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


}
