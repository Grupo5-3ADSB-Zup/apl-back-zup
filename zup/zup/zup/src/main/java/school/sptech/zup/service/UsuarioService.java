package school.sptech.zup.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<byte[]> BuscarImagemPorId(@PathVariable Long idFoto){
        var consulta = _usuarioRepository.findAll();
        for (int i = 0; i < consulta.size(); i++){
            if (consulta.get(i).getId() == idFoto){
                return ResponseEntity.status(200).body(consulta.get(i).getFoto());
            }
        }
        return ResponseEntity.status(404).build();
    }
}
