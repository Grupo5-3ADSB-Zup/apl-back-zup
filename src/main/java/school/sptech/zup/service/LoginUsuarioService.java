package school.sptech.zup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.repository.UsuarioRepository;
import school.sptech.zup.service.AutenticacaoJWT.UsuarioLoginDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginUsuarioService {
    //@Autowired
    private final CadastroUsuarioService _cadastroService;
    private final UsuarioRepository _usuarioRepository;
    private final UsuarioService usuarioService;
    public Usuario logar(UsuarioLoginDto loginDto){
        var consulta = usuarioService.getUsername(loginDto);
        if (consulta.getSenha().equals(loginDto.getSenha())){
            _usuarioRepository.save(consulta);
            return consulta;
        }
        return consulta;
    }
    public Usuario deslogar(String username){
        var consulta = usuarioService.buscaPorUsername(username);
        _usuarioRepository.save(consulta);
        return consulta;
    }
}
