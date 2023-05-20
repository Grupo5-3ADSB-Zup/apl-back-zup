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
import school.sptech.zup.util.enumerador.EnumUsuario;

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


    public ResponseEntity<Usuario> saveUserComum(UsuarioComumRequestBody usuarioPostRequestBody) {
            Usuario usuario = Usuario.builder()
                    .nome(usuarioPostRequestBody.getNome())
                    .email(usuarioPostRequestBody.getEmail())
                    .username(usuarioPostRequestBody.getUsername())
                    .senha(usuarioPostRequestBody.getSenha())
//                    .autenticado(usuarioPostRequestBody.getAutenticado())
                    .tipoUsuario(EnumUsuario.COMUM.getDescricao())
//                    .logado(usuarioPostRequestBody.isLogado())
                    .cpf(usuarioPostRequestBody.getCpf())
                    .cnpj(null)
                    .build();

            String senhaCriptografada = _passwordEncoder.encode(usuario.getSenha());
            usuario.setSenha(senhaCriptografada);

            _usuarioRepository.save(usuario);
            return ResponseEntity.status(200).body(usuario);

    }

    public ResponseEntity<Usuario> saveUserEmpresa(UsuarioEmpresaRequestBody usuarioPostRequestBody) {
            Usuario usuario = Usuario.builder()
                    .nome(usuarioPostRequestBody.getNome())
                    .email(usuarioPostRequestBody.getEmail())
                    .username(usuarioPostRequestBody.getUsername())
                    .senha(usuarioPostRequestBody.getSenha())
//                    .autenticado(usuarioPostRequestBody.getAutenticado())
                    .tipoUsuario(EnumUsuario.EMPRESA.getDescricao())
//                    .logado(usuarioPostRequestBody.isLogado())
                    .cnpj(usuarioPostRequestBody.getCnpj())
                    .cpf(null)
                    .build();
            _usuarioRepository.save(usuario);
            return ResponseEntity.status(201).body(usuario);

    }

//    public ResponseEntity<Usuario> saveUserAdmin(UsuarioAdminPostRequest usuarioPostRequestBody) {
//        var retorno = autenticar(usuarioPostRequestBody);
//        if (retorno == true){
//            Usuario usuario = Usuario.builder()
//                    .nome(usuarioPostRequestBody.getNome())
//                    .email(usuarioPostRequestBody.getEmail())
//                    .username(usuarioPostRequestBody.getUsername())
//                    .senha(usuarioPostRequestBody.getSenha())
//                    .autenticado(null)
//                    .influencer(usuarioPostRequestBody.isInfluencer())
//                    .logado(usuarioPostRequestBody.isLogado())
//                    .cnpj(null)
//                    .cpf(null)
//                    .Admin(usuarioPostRequestBody.getAdmin())
//                    .build();
//            _usuarioRepository.save(usuario);
//            return ResponseEntity.status(200).body(usuario);
//        }
//        return ResponseEntity.status(401).build();
//    }

//       private boolean autenticar(UsuarioPostRequestBody user){
//        if (user.getAutenticado() == false && user.isLogado() == false){
//                user.setAutenticado(true);
//                return true;
//            }
//        return false;
//    }

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
