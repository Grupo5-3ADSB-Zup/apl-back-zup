package school.sptech.zup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.service.AutenticacaoJWT.UsuarioLoginDto;
import school.sptech.zup.service.AutenticacaoJWT.UsuarioTokenDto;
import school.sptech.zup.service.CadastroUsuarioService;
import school.sptech.zup.service.LoginUsuarioService;
import school.sptech.zup.util.DateUtil;

@CrossOrigin
@RestController
@RequestMapping("/login")
@Log4j2
@RequiredArgsConstructor
public class LoginUsuarioController {
    @Autowired
    private final DateUtil dateUtil;
    @Autowired
    private final LoginUsuarioService _loginService;
    @Autowired
    private final CadastroUsuarioService _cadastroUsuarioService;

   @PostMapping("/logar")
    public ResponseEntity<UsuarioTokenDto> logarUser(@RequestBody UsuarioLoginDto loginDto){
        //var retorno = _loginService.logar(loginDto);
        UsuarioTokenDto usuarioTokenDto = this._cadastroUsuarioService.autenticar(loginDto);
        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    @PutMapping("/{username}")
    public ResponseEntity<Usuario> Deslogar(@PathVariable String username){
        var retorno = _loginService.deslogar(username);
        return retorno;
    }

}
