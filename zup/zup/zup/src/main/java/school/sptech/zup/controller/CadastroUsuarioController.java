package school.sptech.zup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.*;
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

    @PostMapping("/user/comum")
    public ResponseEntity<Usuario> saveUserComum(@RequestBody UsuarioComumRequestBody usuario) {
        var retorno = _cadastroService.saveUserComum(usuario);
        return retorno;
    }

    @PostMapping("/user/empresa")
    public ResponseEntity<Usuario> saveUserEmpresa(@RequestBody UsuarioEmpresaRequestBody usuario) {
        var retorno = _cadastroService.saveUserEmpresa(usuario);
        return retorno;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> deleteUser(@PathVariable long id) {
        var retorno = _cadastroService.deleteUser(id);
        return retorno;
    }

    @PutMapping("/user/comum")
    public ResponseEntity<Usuario> atualizarUserComum(@RequestBody UsuarioComumPutRequestBody usuarioPutRequestBody) {
        var retorno = _cadastroService.atualizarUsuarioComum(usuarioPutRequestBody);
        return retorno;
    }

    @PutMapping("user/empresa")
    public ResponseEntity<Usuario> atualizarUserEmpresa(@RequestBody UsuarioEmpresaPutRequestBody usuarioPutRequestBody) {
        var retorno = _cadastroService.atualizarUsuarioEmpresa(usuarioPutRequestBody);
        return retorno;
    }
}
