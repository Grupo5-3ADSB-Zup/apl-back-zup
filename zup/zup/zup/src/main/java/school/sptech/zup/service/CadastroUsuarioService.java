package school.sptech.zup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.*;
import school.sptech.zup.repository.UsuarioRerpository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CadastroUsuarioService {

    private final UsuarioRerpository _usuarioRepository;

    public ResponseEntity<Usuario> buscaPorId(Long id) {
        Optional<Usuario> usuarioConsulta = _usuarioRepository.findById(id);
        if (usuarioConsulta.isPresent()){
            return ResponseEntity.status(200).body(usuarioConsulta.get());
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<Usuario> saveUserComum(UsuarioComumRequestBody usuarioPostRequestBody) {
        var retorno = autenticar(usuarioPostRequestBody);
        if (retorno == true){
            Usuario usuario = Usuario.builder()
                    .nome(usuarioPostRequestBody.getNome())
                    .email(usuarioPostRequestBody.getEmail())
                    .username(usuarioPostRequestBody.getUsername())
                    .senha(usuarioPostRequestBody.getSenha())
                    .autenticado(usuarioPostRequestBody.getAutenticado())
                    .influencer(usuarioPostRequestBody.isInfluencer())
                    .logado(usuarioPostRequestBody.isLogado())
                    .cpf(usuarioPostRequestBody.getCpf())
                    .cnpj(null)
                    .build();
            _usuarioRepository.save(usuario);
            return ResponseEntity.status(200).body(usuario);
        }
         return ResponseEntity.status(401).build();
    }

    public ResponseEntity<Usuario> saveUserEmpresa(UsuarioEmpresaRequestBody usuarioPostRequestBody) {
        var retorno = autenticar(usuarioPostRequestBody);
        if (retorno == true){
            Usuario usuario = Usuario.builder()
                    .nome(usuarioPostRequestBody.getNome())
                    .email(usuarioPostRequestBody.getEmail())
                    .username(usuarioPostRequestBody.getUsername())
                    .senha(usuarioPostRequestBody.getSenha())
                    .autenticado(usuarioPostRequestBody.getAutenticado())
                    .influencer(usuarioPostRequestBody.isInfluencer())
                    .logado(usuarioPostRequestBody.isLogado())
                    .cnpj(usuarioPostRequestBody.getCnpj())
                    .cpf(null)
                    .build();
            _usuarioRepository.save(usuario);
            return ResponseEntity.status(200).body(usuario);
        }
        return ResponseEntity.status(401).build();
    }

    public ResponseEntity<Usuario> deleteUser(long id) {
        var retorno = buscaPorId(id);
        if (retorno.getStatusCodeValue() == 200){
            _usuarioRepository.deleteById(id);
            return ResponseEntity.status(200).build();
        }
        return retorno;
    }


    public ResponseEntity<Usuario> atualizarUsuarioComum(UsuarioComumPutRequestBody usuarioPutRequestBody) {
        var consulta = buscaPorId(usuarioPutRequestBody.getId());

        if (consulta.getStatusCodeValue() == 200){
            Usuario usuario = Usuario.builder()
                    .id(consulta.getBody().getId())
                    .nome(usuarioPutRequestBody.getNome())
                    .email(usuarioPutRequestBody.getEmail())
                    .username(usuarioPutRequestBody.getUsername())
                    .senha(usuarioPutRequestBody.getSenha())
                    .autenticado(usuarioPutRequestBody.getAutenticado())
                    .influencer(usuarioPutRequestBody.isInfluencer())
                    .logado(usuarioPutRequestBody.isLogado())
                    .cpf(usuarioPutRequestBody.getCpf())
                    .cnpj(null)
                    .build();
            _usuarioRepository.save(usuario);

            return ResponseEntity.status(200).body(usuario);
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<Usuario> atualizarUsuarioEmpresa(UsuarioEmpresaPutRequestBody usuarioPutRequestBody) {
        var consulta = buscaPorId(usuarioPutRequestBody.getId());

        if (consulta.getStatusCodeValue() == 200){
            Usuario usuario = Usuario.builder()
                    .id(consulta.getBody().getId())
                    .nome(usuarioPutRequestBody.getNome())
                    .email(usuarioPutRequestBody.getEmail())
                    .username(usuarioPutRequestBody.getUsername())
                    .senha(usuarioPutRequestBody.getSenha())
                    .autenticado(usuarioPutRequestBody.getAutenticado())
                    .influencer(usuarioPutRequestBody.isInfluencer())
                    .logado(usuarioPutRequestBody.isLogado())
                    .cnpj(usuarioPutRequestBody.getCnpj())
                    .cpf(null)
                    .build();
            _usuarioRepository.save(usuario);

            return ResponseEntity.status(200).body(usuario);
        }
        return ResponseEntity.status(404).build();
    }

    private boolean autenticar(UsuarioPostRequestBody user){
        if (user.getAutenticado() == false && user.isLogado() == false){
                user.setAutenticado(true);
                return true;
            }
        return false;
    }
}
