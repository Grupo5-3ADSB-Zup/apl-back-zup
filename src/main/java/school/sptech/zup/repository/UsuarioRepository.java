package school.sptech.zup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.zup.domain.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    @Modifying
    @Transactional
    @Query("update Usuario u set u.foto = ?2 where u.id = ?1")
    void setFoto(Long id, byte[] foto);

    @Query("select u.foto from Usuario u where u.id = ?1")
    byte[] getFoto(Long id);
}
