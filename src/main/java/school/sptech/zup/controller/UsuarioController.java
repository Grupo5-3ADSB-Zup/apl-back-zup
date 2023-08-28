package school.sptech.zup.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.UsuarioAdminPutRequest;
import school.sptech.zup.dto.UsuarioComumPutRequestBody;
import school.sptech.zup.dto.UsuarioEmpresaPutRequestBody;
import school.sptech.zup.repository.UsuarioRepository;
import school.sptech.zup.service.UsuarioService;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuario")
@Log4j2
@RequiredArgsConstructor
public class UsuarioController {
    @Autowired
    private final UsuarioService usuarioService;
    @Autowired
    private final UsuarioRepository _usuarioRepository;
    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Usuario> getUsuario(@RequestParam String username) {
        var retorno = usuarioService.buscaPorUsername(username);
        return ResponseEntity.ok(retorno);
    }

    @GetMapping(value = "/foto/{idFoto}")
    public ResponseEntity<byte[]> retornaImagem(@PathVariable Long idFoto){
        var retorno = usuarioService.BuscarImagemPorId(idFoto);
        return ResponseEntity.ok(retorno);
    }

    @PatchMapping(value = "/foto/{idUsuario}")
    public ResponseEntity<Void> adicionarImagem(@PathVariable Long idUsuario, @RequestBody byte[] foto){
        var retorno = usuarioService.buscaPorId(idUsuario);
            _usuarioRepository.setFoto(idUsuario, foto);
            return ResponseEntity.ok().build();
    }

    @PutMapping("user/comum")
    public ResponseEntity<Usuario> atualizarUserComum(@RequestBody UsuarioComumPutRequestBody usuarioPutRequestBody) {
        var retorno = usuarioService.atualizarUsuarioComum(usuarioPutRequestBody);
        return ResponseEntity.ok(retorno);
    }

    @PutMapping("user/empresa")
    public ResponseEntity<Usuario> atualizarUserEmpresa(@RequestBody UsuarioAdminPutRequest usuarioPutRequestBody) {
        var retorno = usuarioService.atualizarUsuarioAdmin(usuarioPutRequestBody);
        return ResponseEntity.ok(retorno);
    }

    @PutMapping("user/admin")
    public ResponseEntity<Usuario> atualizarUserAdmin(@RequestBody UsuarioEmpresaPutRequestBody usuarioPutRequestBody) {
        var retorno = usuarioService.atualizarUsuarioEmpresa(usuarioPutRequestBody);
        return ResponseEntity.ok(retorno);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        var retorno = usuarioService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Usuario> getUsuarioId(@PathVariable Long id) {
        var retorno = usuarioService.buscaPorId(id);
        return ResponseEntity.ok(retorno);
    }
}
