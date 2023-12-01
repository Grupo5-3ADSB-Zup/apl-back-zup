package school.sptech.zup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import school.sptech.zup.domain.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    @Modifying
    @Transactional
    @Query("update Usuario u set u.foto = ?2 where u.id = ?1")
    void setFoto(Long id, byte[] foto);

    @Query("select u.foto from Usuario u where u.id = ?1")
    byte[] getFoto(Long id);

    @Query("SELECT u" +
            "   FROM Usuario u" +
            "       WHERE u.IdPerfil = :IdPerfil" +
            "           AND u.influencer = true" +
            "       ORDER BY u.influencer DESC")
    List<Usuario> BuscaUsuarioTpPerfil(Long IdPerfil);

    @Query("SELECT u" +
            "   FROM Usuario u" +
            "       WHERE u.influencer = true")
    List<Usuario> BuscaTodosUsuariosInfluencers();

    @Modifying
    @Transactional
    @Query("update Usuario u " +
            "set u.LinkYoutube = :youtube," +
            "    u.LinkInstagram = :instagram," +
            "        u.LinkTikTok = :tiktok " +
            "           where u.id = :id")
    Boolean setDadosInfluencer(Long id,
                            String tiktok,
                            String youtube,
                            String instagram);

    @Query("SELECT u" +
            "   FROM Usuario u" +
            "       WHERE u.id = :idUsuario")
    Optional<Usuario> BuscaUsuario(Long idUsuario);

}
