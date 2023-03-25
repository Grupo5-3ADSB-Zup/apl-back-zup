package school.sptech.zup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.UsuarioPostRequestBody;
import school.sptech.zup.dto.UsuarioPutRequestBody;
import school.sptech.zup.service.UsuarioService;
import school.sptech.zup.util.DateUtil;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
@Log4j2
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private final DateUtil dateUtil;
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> usuarios() {
        log.info(dateUtil.formLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(usuarioService.listAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        return new ResponseEntity<>(usuarioService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody UsuarioPostRequestBody usuarioPostRequestBody) {
        return new ResponseEntity<>(usuarioService.save(usuarioPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        usuarioService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.resolve(204));
    }

    @PutMapping
    public ResponseEntity<Void> atualizarUser(@RequestBody UsuarioPutRequestBody usuarioPutRequestBody) {
        usuarioService.atualizarUsuario(usuarioPutRequestBody);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
