package school.sptech.zup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.UsuarioPostRequestBody;
import school.sptech.zup.dto.UsuarioPutRequestBody;
import school.sptech.zup.repository.UsuarioRerpository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRerpository usuarioRerpository;


    public List<Usuario> listAll() {
        return usuarioRerpository.findAll();
    }

    public Usuario findById(long id) {
        return usuarioRerpository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi"));
    }

    public Usuario save(UsuarioPostRequestBody usuarioPostRequestBody) {
        Usuario usuario = Usuario.builder()
                .nome(usuarioPostRequestBody.getNome())
                .email(usuarioPostRequestBody.getEmail())
                .username(usuarioPostRequestBody.getUsername())
                .cpf(usuarioPostRequestBody.getCpf())
                .senha(usuarioPostRequestBody.getSenha())
                .build();

        return usuarioRerpository.save(usuario);
    }

    public void deleteUser(long id) {
        usuarioRerpository.delete(findById(id));
    }


    public void atualizarUsuario(UsuarioPutRequestBody usuarioPutRequestBody) {
        Usuario saveUsers = findById(usuarioPutRequestBody.getId());
        Usuario usuario = Usuario.builder()
                .id(saveUsers.getId())
                .nome(usuarioPutRequestBody.getNome())
                .email(usuarioPutRequestBody.getEmail())
                .username(usuarioPutRequestBody.getUsername())
                .cpf(usuarioPutRequestBody.getCpf())
                .senha(usuarioPutRequestBody.getSenha())
                .build();
        usuarioRerpository.save(usuario);
    }


}
