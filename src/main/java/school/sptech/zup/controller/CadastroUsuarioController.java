package school.sptech.zup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.*;
import school.sptech.zup.service.CadastroUsuarioService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cadastro")
@Log4j2
@RequiredArgsConstructor
public class CadastroUsuarioController {
    private final CadastroUsuarioService _cadastroService;

    @PostMapping("/user/comum")
    public ResponseEntity<Usuario> saveUserComum(@RequestBody UsuarioComumRequestBody usuario) {
        var retorno = _cadastroService.saveUserComum(usuario);
        return ResponseEntity.ok(retorno);
    }

    @PostMapping("/user/empresa")
    public ResponseEntity<Usuario> saveUserEmpresa(@RequestBody UsuarioEmpresaRequestBody usuario) {
        var retorno = _cadastroService.saveUserEmpresa(usuario);
        return ResponseEntity.ok(retorno);
    }

    @PostMapping("/user/admin")
    public ResponseEntity<Usuario> saveUserAdmin(@RequestBody UsuarioAdminPostRequest usuario) {
        var retorno = _cadastroService.saveUserAdmin(usuario);
        return ResponseEntity.ok(retorno);
    }
}
