package school.sptech.zup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.UsuarioPostRequestBody;
import school.sptech.zup.dto.UsuarioPutRequestBody;
import school.sptech.zup.service.CadastroUsuarioService;
import school.sptech.zup.util.DateUtil;

@RestController
@RequestMapping("/cadastro")
@Log4j2
@RequiredArgsConstructor
public class CadastroUsuarioController {

    @Autowired
    private final DateUtil dateUtil;
    private final CadastroUsuarioService _cadastroService;

    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody UsuarioPostRequestBody usuario) {
        var retorno = _cadastroService.save(usuario);
        return retorno;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> deleteUser(@PathVariable long id) {
        var retorno = _cadastroService.deleteUser(id);
        return retorno;
    }

    @PutMapping
    public ResponseEntity<Usuario> atualizarUser(@RequestBody UsuarioPutRequestBody usuarioPutRequestBody) {
        var retorno = _cadastroService.atualizarUsuario(usuarioPutRequestBody);
        return retorno;
    }
}
