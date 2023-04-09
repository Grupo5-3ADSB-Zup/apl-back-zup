package school.sptech.zup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import school.sptech.zup.domain.Login;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.repository.UsuarioRerpository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginUsuarioService {
    //@Autowired
    private final CadastroUsuarioService _cadastroService;
    private final UsuarioRerpository _usuarioRepository;

    public ResponseEntity<Usuario> getUsername(Login login) {
        var consulta = buscaPorUsername(login.getUsername());
        if (consulta.getStatusCodeValue() == 200 &&
                consulta.getBody().getEmail().equals(login.getEmail())
                && consulta.getBody().getSenha().equals(login.getSenha())){

            return ResponseEntity.status(200).body(consulta.getBody());
        }
        return consulta;
    }

    public ResponseEntity<Usuario> logar(Login login){
        var consulta = getUsername(login);
        if (consulta.getStatusCodeValue() == 200 && consulta.getBody().isLogado() == false &&
                consulta.getBody().getEmail().equals(login.getEmail())
                && consulta.getBody().getSenha().equals(login.getSenha())){

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
