package school.sptech.zup.service.AutenticacaoJWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.repository.UsuarioRerpository;

import java.util.Optional;

public class AutenticacaoService implements UserDetailsService {
    @Autowired
    private UsuarioRerpository _usuarioRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> UsuarioOpt = _usuarioRepository.findByUsername(username);
        if (UsuarioOpt.isEmpty()){
            throw new UsernameNotFoundException(String.format("Usuario não encontrado " , username));
        }
        return new UsuarioDetalheDto(UsuarioOpt.get());
    }
}
