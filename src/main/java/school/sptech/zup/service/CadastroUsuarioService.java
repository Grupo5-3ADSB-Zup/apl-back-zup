package school.sptech.zup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import java.util.Base64;

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
    public Usuario saveUserComum(UsuarioComumRequestBody usuarioPostRequestBody) {
        var retorno = autenticar(usuarioPostRequestBody);

       // byte[] foto = Base64.getDecoder().decode(usuarioPostRequestBody.getFoto());

        if (retorno == true){
            Usuario usuario = Usuario.builder()
                    .nome(usuarioPostRequestBody.getNome())
                    .email(usuarioPostRequestBody.getEmail())
                    .username(usuarioPostRequestBody.getUsername())
                    .senha(usuarioPostRequestBody.getSenha())
                    .autenticado(usuarioPostRequestBody.getAutenticado())
                    .influencer(usuarioPostRequestBody.isInfluencer())
                    .cpf(usuarioPostRequestBody.getCpf())
                    .foto(usuarioPostRequestBody.getFoto())
                    .cnpj(null)
                    .build();

            String senhaCriptografada = _passwordEncoder.encode(usuario.getSenha());
            usuario.setSenha(senhaCriptografada);

            _usuarioRepository.save(usuario);
            return usuario;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sem credenciais");
    }
    public Usuario saveUserEmpresa(UsuarioEmpresaRequestBody usuarioPostRequestBody) {
        var retorno = autenticar(usuarioPostRequestBody);

        byte[] foto = Base64.getDecoder().decode(usuarioPostRequestBody.getFoto());

        if (retorno == true){
            Usuario usuario = Usuario.builder()
                    .nome(usuarioPostRequestBody.getNome())
                    .email(usuarioPostRequestBody.getEmail())
                    .username(usuarioPostRequestBody.getUsername())
                    .senha(usuarioPostRequestBody.getSenha())
                    .autenticado(usuarioPostRequestBody.getAutenticado())
                    .influencer(usuarioPostRequestBody.isInfluencer())
                    .cnpj(usuarioPostRequestBody.getCnpj())
                    .foto(foto)
                    .cpf(null)
                    .build();
            _usuarioRepository.save(usuario);
            return usuario;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sem credenciais");
    }
    public Usuario saveUserAdmin(UsuarioAdminPostRequest usuarioPostRequestBody) {
        var retorno = autenticar(usuarioPostRequestBody);

        byte[] foto = Base64.getDecoder().decode(usuarioPostRequestBody.getFoto());

        if (retorno == true){
            Usuario usuario = Usuario.builder()
                    .nome(usuarioPostRequestBody.getNome())
                    .email(usuarioPostRequestBody.getEmail())
                    .username(usuarioPostRequestBody.getUsername())
                    .senha(usuarioPostRequestBody.getSenha())
                    .autenticado(null)
                    .influencer(usuarioPostRequestBody.isInfluencer())
                    .cnpj(null)
                    .cpf(null)
                    .foto(foto)
                    .Admin(usuarioPostRequestBody.getAdmin())
                    .build();
            _usuarioRepository.save(usuario);
            return usuario;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sem credenciais");
    }
    private boolean autenticar(UsuarioPostRequestBody user){
        if (user.getAutenticado() == false){
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
