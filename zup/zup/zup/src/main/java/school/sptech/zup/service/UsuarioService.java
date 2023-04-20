package school.sptech.zup.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import school.sptech.zup.domain.Usuario;

import java.util.List;
@Service
public class UsuarioService {
    private final UsuarioRepository _usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        _usuarioRepository = usuarioRepository;
    }

    public ResponseEntity<List<Usuario>> getListUsuario(){
        List<Usuario> usuarioConsulta = _usuarioRepository.findAll();

        if (usuarioConsulta.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(usuarioConsulta);
    }
}
