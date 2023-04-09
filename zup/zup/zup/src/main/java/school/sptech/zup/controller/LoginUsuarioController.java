package school.sptech.zup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.domain.Login;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.service.LoginUsuarioService;
import school.sptech.zup.util.DateUtil;

@RestController
@RequestMapping("/login")
@Log4j2
@RequiredArgsConstructor
public class LoginUsuarioController {
    @Autowired
    private final DateUtil dateUtil;
    @Autowired
    private final LoginUsuarioService _loginService;

    @GetMapping("/{username}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable String username) {
        var retorno = _loginService.buscaPorUsername(username);
        return retorno;
    }

    @PostMapping
    public ResponseEntity<Usuario> logarUser(@RequestBody Login login){
        var retorno = _loginService.logar(login);
        return retorno;
    }

    @PutMapping("/{username}")
    public ResponseEntity<Usuario> Deslogar(@PathVariable String username){
        var retorno = _loginService.deslogar(username);
        return retorno;
    }
}
