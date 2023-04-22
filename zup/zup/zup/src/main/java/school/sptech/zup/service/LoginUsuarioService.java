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

    public ResponseEntity<Usuario> getUsername(UsuarioLoginDto loginDto) {
        var consulta = buscaPorUsername(loginDto.getUsername());
        if (consulta.getStatusCodeValue() == 200
                && consulta.getBody().getSenha().equals(loginDto.getSenha())){

            return ResponseEntity.status(200).body(consulta.getBody());
        }
        return consulta;
    }

    public ResponseEntity<Usuario> logar(UsuarioLoginDto loginDto){
        var consulta = getUsername(loginDto);
        if (consulta.getStatusCodeValue() == 200 && consulta.getBody().isLogado() == false
                && consulta.getBody().getSenha().equals(loginDto.getSenha())){

            consulta.getBody().setLogado(true);
            _usuarioRepository.save(consulta.getBody());
            return ResponseEntity.status(200).body(consulta.getBody());
        }
        return consulta;
    }

    public ResponseEntity<Usuario> deslogar(String username){
        var consulta = buscaPorUsername(username);
        if (consulta.getStatusCodeValue() == 200 && consulta.getBody().isLogado() == true){
            consulta.getBody().setLogado(false);
            _usuarioRepository.save(consulta.getBody());
            return ResponseEntity.status(200).body(consulta.getBody());
        }
        return consulta;
    }

    public ResponseEntity<Usuario> buscaPorUsername(String username){
        List<Usuario> usuarioConsulta = _usuarioRepository.findAll();

        for (int i = 0; i < usuarioConsulta.size(); i++) {
            if (usuarioConsulta.get(i).getUsername().equals(username)){
                return ResponseEntity.status(200).body(usuarioConsulta.get(i));
            }
        }
        return ResponseEntity.status(404).build();
    }
}
