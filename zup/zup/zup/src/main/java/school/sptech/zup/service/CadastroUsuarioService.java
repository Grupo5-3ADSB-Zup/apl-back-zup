package school.sptech.zup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.zup.configuration.security.jwt.GerenciadorTokenJwt;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.*;
import school.sptech.zup.repository.UsuarioRepository;
import school.sptech.zup.service.AutenticacaoJWT.UsuarioLoginDto;
import school.sptech.zup.service.AutenticacaoJWT.UsuarioTokenDto;

import java.util.Optional;

@Service
public class CadastroUsuarioService {
    private final UsuarioRepository _usuarioRepository;
    private final PasswordEncoder _passwordEncoder;
    private final GerenciadorTokenJwt _gerenciadorTokenJwt;
    private final AuthenticationManager _authenticationManager;
    @Autowired
    public CadastroUsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                                  GerenciadorTokenJwt gerenciadorTokenJwt,
                                        AuthenticationManager authenticationManager) {

        _usuarioRepository = usuarioRepository;
        _passwordEncoder = passwordEncoder;
        _gerenciadorTokenJwt = gerenciadorTokenJwt;
        _authenticationManager = authenticationManager;
    }

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

            String senhaCriptografada = _passwordEncoder.encode(usuario.getSenha());
            usuario.setSenha(senhaCriptografada);

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

    public ResponseEntity<Usuario> saveUserAdmin(UsuarioAdminPostRequest usuarioPostRequestBody) {
        var retorno = autenticar(usuarioPostRequestBody);
        if (retorno == true){
            Usuario usuario = Usuario.builder()
                    .nome(usuarioPostRequestBody.getNome())
                    .email(usuarioPostRequestBody.getEmail())
                    .username(usuarioPostRequestBody.getUsername())
                    .senha(usuarioPostRequestBody.getSenha())
                    .autenticado(null)
                    .influencer(usuarioPostRequestBody.isInfluencer())
                    .logado(usuarioPostRequestBody.isLogado())
                    .cnpj(null)
                    .cpf(null)
                    .Admin(usuarioPostRequestBody.getAdmin())
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

    public ResponseEntity<Usuario> atualizarUsuarioAdmin(UsuarioAdminPutRequest usuarioPutRequestBody) {
        var consulta = buscaPorId(usuarioPutRequestBody.getId());

        if (consulta.getStatusCodeValue() == 200){
            Usuario usuario = Usuario.builder()
                    .id(consulta.getBody().getId())
                    .nome(usuarioPutRequestBody.getNome())
                    .email(usuarioPutRequestBody.getEmail())
                    .username(usuarioPutRequestBody.getUsername())
                    .senha(usuarioPutRequestBody.getSenha())
                    .autenticado(null)
                    .influencer(usuarioPutRequestBody.isInfluencer())
                    .logado(usuarioPutRequestBody.isLogado())
                    .cnpj(null)
                    .cpf(null)
                    .Admin(usuarioPutRequestBody.getAdmin())
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

    public UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto){
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuarioLoginDto.getUsername(), usuarioLoginDto.getSenha());

        final Authentication authentication = this._authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado =
                _usuarioRepository.findByUsername(usuarioLoginDto.getUsername())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Usarname do Usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = _gerenciadorTokenJwt.generateToken(authentication);

        return MapperJWT.of(usuarioAutenticado, token);
    }
}
