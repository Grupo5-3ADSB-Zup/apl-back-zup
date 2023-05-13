package school.sptech.zup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.*;
import school.sptech.zup.repository.UsuarioRepository;
import school.sptech.zup.service.CadastroUsuarioService;
import school.sptech.zup.service.UsuarioService;
import school.sptech.zup.util.DateUtil;

@CrossOrigin
@RestController
@RequestMapping("/cadastro")
@Log4j2
@RequiredArgsConstructor
public class CadastroUsuarioController {

    @Autowired
    private final DateUtil dateUtil;
    private final CadastroUsuarioService _cadastroService;
    private final UsuarioRepository _usuarioRepository;

    private final UsuarioService _usuarioService;

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

    @PostMapping("/user/admin")
    public ResponseEntity<Usuario> saveUserAdmin(@RequestBody UsuarioAdminPostRequest usuario) {
        var retorno = _cadastroService.saveUserAdmin(usuario);
        return retorno;
    }
}
