package school.sptech.zup.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.service.UsuarioService;
import school.sptech.zup.util.DateUtil;

@CrossOrigin
@RestController
@RequestMapping("/usuario")
@Log4j2
@RequiredArgsConstructor
public class UsuarioController {
    @Autowired
    private final DateUtil dateUtil;

    @Autowired
    private final UsuarioService usuarioService;

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Usuario> getUsuario(@RequestParam String username) {
        var retorno = usuarioService.buscaPorUsername(username);
        return retorno;
    }

    @PutMapping("/{username}")
    public ResponseEntity<Usuario> Deslogar(@PathVariable String username){
        var retorno = usuarioService.deslogar(username);
        return retorno;
    }

}
