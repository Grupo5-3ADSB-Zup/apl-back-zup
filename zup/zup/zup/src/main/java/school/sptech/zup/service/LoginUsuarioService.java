package school.sptech.zup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.repository.UsuarioRerpository;

@Service
@RequiredArgsConstructor
public class LoginUsuarioService {
    //@Autowired
    private final CadastroUsuarioService _cadastroService;
    private final UsuarioRerpository _usuarioRepository;

    public ResponseEntity<Usuario> getId(Long id) {
        var consulta = _cadastroService.buscaPorId(id);
        if (consulta.getStatusCodeValue() == 200){
            return ResponseEntity.status(200).body(consulta.getBody());
        }
        return consulta;
    }

    public ResponseEntity<Usuario> logar(Long id){
        var consulta = getId(id);
        if (consulta.getStatusCodeValue() == 200 && consulta.getBody().isLogado() == false){
            consulta.getBody().setLogado(true);
            consulta.getBody().setId(id);
            _usuarioRepository.save(consulta.getBody());
            return ResponseEntity.status(200).body(consulta.getBody());
        }
        return consulta;
    }

    public ResponseEntity<Usuario> deslogar(Long id){
        var consulta = getId(id);
        if (consulta.getStatusCodeValue() == 200 && consulta.getBody().isLogado() == true){
            consulta.getBody().setLogado(false);
            consulta.getBody().setId(id);
            _usuarioRepository.save(consulta.getBody());
            return ResponseEntity.status(200).body(consulta.getBody());
        }
        return consulta;
    }
}
