package school.sptech.zup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.UsuarioPostRequestBody;
import school.sptech.zup.dto.UsuarioPutRequestBody;
import school.sptech.zup.service.CadastroService;
import school.sptech.zup.util.DateUtil;

import java.util.List;

@RestController
@RequestMapping("/users")
@Log4j2
@RequiredArgsConstructor
public class CadastroController {

    @Autowired
    private final DateUtil dateUtil;
    private final CadastroService _cadastroService;

    @GetMapping
    public ResponseEntity<List<Usuario>> usuarios() {

        var retorno = _cadastroService.TodosOsUsers();
        return retorno;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        var retorno = _cadastroService.buscaPorId(id);

        return retorno;
    }

    @PostMapping
    public ResponseEntity<Usuario> saveComum(@RequestBody UsuarioPostRequestBody usuario) {
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
