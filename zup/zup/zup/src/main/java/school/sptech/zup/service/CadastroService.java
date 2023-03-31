package school.sptech.zup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.UsuarioPostRequestBody;
import school.sptech.zup.dto.UsuarioPutRequestBody;
import school.sptech.zup.repository.UsuarioRerpository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CadastroService {

    private final UsuarioRerpository _usuarioRepository;


    public ResponseEntity<List<Usuario>> TodosOsUsers() {
        var consulta = _usuarioRepository.findAll();
        if (consulta.isEmpty()){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(consulta);
    }

    public ResponseEntity<Usuario> buscaPorId(Long id) {
        Optional<Usuario> usuarioConsulta = _usuarioRepository.findById(id);
        if (usuarioConsulta.isPresent()){
            return ResponseEntity.status(200).body(usuarioConsulta.get());
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<Usuario> save(UsuarioPostRequestBody usuarioPostRequestBody) {
        Usuario usuario = Usuario.builder()
                .nome(usuarioPostRequestBody.getNome())
                .email(usuarioPostRequestBody.getEmail())
                .username(usuarioPostRequestBody.getUsername())
                .senha(usuarioPostRequestBody.getSenha())
                .build();
         _usuarioRepository.save(usuario);

         return ResponseEntity.status(200).body(usuario);
    }

    public ResponseEntity<Usuario> deleteUser(long id) {
        var retorno = buscaPorId(id);
        if (retorno.getStatusCodeValue() == 200){
            _usuarioRepository.deleteById(id);
            return ResponseEntity.status(200).build();
        }
        return retorno;
    }


    public ResponseEntity<Usuario> atualizarUsuario(UsuarioPutRequestBody usuarioPutRequestBody) {
        var consulta = buscaPorId(usuarioPutRequestBody.getId());
        Usuario usuario = Usuario.builder()
                .id(consulta.getBody().getId())
                .nome(usuarioPutRequestBody.getNome())
                .email(usuarioPutRequestBody.getEmail())
                .username(usuarioPutRequestBody.getUsername())
                .senha(usuarioPutRequestBody.getSenha())
                .build();
        _usuarioRepository.save(usuario);

        return ResponseEntity.status(200).body(usuario);
    }
}
