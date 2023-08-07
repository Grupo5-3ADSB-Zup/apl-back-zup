package school.sptech.zup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.repository.UsuarioRepository;
import school.sptech.zup.service.AutenticacaoJWT.UsuarioLoginDto;

@Service
@RequiredArgsConstructor
public class LoginUsuarioService {
    private final UsuarioRepository _usuarioRepository;
    private final UsuarioService usuarioService;


    public ResponseEntity<Usuario> logar(UsuarioLoginDto loginDto){
        var consulta = usuarioService.getUsername(loginDto);
        if (consulta.getStatusCodeValue() == 200 && consulta.getBody().isLogado() == false
                && consulta.getBody().getSenha().equals(loginDto.getSenha())){

            consulta.getBody().setLogado(true);
            _usuarioRepository.save(consulta.getBody());
            return ResponseEntity.status(200).body(consulta.getBody());
        }
        return consulta;
    }

    public ResponseEntity<Usuario> deslogar(String username){
        var consulta = usuarioService.buscaPorUsername(username);
        if (consulta.getStatusCodeValue() == 200 && consulta.getBody().isLogado() == true){
            consulta.getBody().setLogado(false);
            _usuarioRepository.save(consulta.getBody());
            return ResponseEntity.status(200).body(consulta.getBody());
        }
        return consulta;
    }
}
