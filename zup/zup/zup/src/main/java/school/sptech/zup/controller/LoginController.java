package school.sptech.zup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.service.LoginService;
import school.sptech.zup.util.DateUtil;

@RestController
@RequestMapping("/logins")
@Log4j2
@RequiredArgsConstructor
public class LoginController {
    @Autowired
    private final DateUtil dateUtil;
    @Autowired
    private final LoginService _loginService;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> get(@PathVariable Long id) {
        var retorno = _loginService.getId(id);

        return retorno;
    }
}
