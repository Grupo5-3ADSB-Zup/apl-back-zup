package school.sptech.zup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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


    public ResponseEntity<Usuario> logar(UsuarioLoginDto loginDto){
        var consulta = usuarioService.getUsername(loginDto);
        if (consulta.getStatusCodeValue() == 200
                && consulta.getBody().getSenha().equals(loginDto.getSenha())){

            _usuarioRepository.save(consulta.getBody());
            return ResponseEntity.status(200).body(consulta.getBody());
        }
        return consulta;
    }

    public ResponseEntity<Usuario> deslogar(String username){
        var consulta = usuarioService.buscaPorUsername(username);
        if (consulta.getStatusCodeValue() == 200 ){

            _usuarioRepository.save(consulta.getBody());
            return ResponseEntity.status(200).body(consulta.getBody());
        }
        return consulta;
    }


}
