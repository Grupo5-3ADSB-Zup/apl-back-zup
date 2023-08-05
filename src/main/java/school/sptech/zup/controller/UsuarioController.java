package school.sptech.zup.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.UsuarioAdminPutRequest;
import school.sptech.zup.dto.UsuarioComumPutRequestBody;;
import school.sptech.zup.dto.UsuarioEmpresaPutRequestBody;
import school.sptech.zup.repository.UsuarioRepository;
import school.sptech.zup.service.UsuarioService;

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
        return retorno;
    }

    @GetMapping(value = "/foto/{idFoto}")
    public ResponseEntity<byte[]> retornaImagem(@PathVariable Long idFoto){
        var retorno = usuarioService.BuscarImagemPorId(idFoto);
        if (retorno.getStatusCodeValue() == 200){
            return ResponseEntity.status(200).body(retorno.getBody());
        }
        return retorno;
    }

    @PatchMapping(value = "/foto/{idFoto}")
    public ResponseEntity<Void> adicionarImagem(@PathVariable Long idFoto, @RequestBody byte[] foto){
        var retorno = usuarioService.buscaPorId(idFoto);
        if (retorno.getStatusCodeValue() == 200){
            _usuarioRepository.setFoto(idFoto, foto);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }

    @PutMapping("user/comum")
    public ResponseEntity<Usuario> atualizarUserComum(@RequestBody UsuarioComumPutRequestBody usuarioPutRequestBody) {
        var retorno = usuarioService.atualizarUsuarioComum(usuarioPutRequestBody);
        return retorno;
    }

    @PutMapping("user/empresa")
    public ResponseEntity<Usuario> atualizarUserEmpresa(@RequestBody UsuarioAdminPutRequest usuarioPutRequestBody) {
        var retorno = usuarioService.atualizarUsuarioAdmin(usuarioPutRequestBody);
        return retorno;
    }

    @PutMapping("user/admin")
    public ResponseEntity<Usuario> atualizarUserAdmin(@RequestBody UsuarioEmpresaPutRequestBody usuarioPutRequestBody) {
        var retorno = usuarioService.atualizarUsuarioEmpresa(usuarioPutRequestBody);
        return retorno;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> deleteUser(@PathVariable long id) {
        var retorno = usuarioService.deleteUser(id);
        return retorno;
    }

    @GetMapping("{id}")
    public ResponseEntity<Usuario> getUsuarioId(@PathVariable Long id) {
        var retorno = usuarioService.buscaUsuarioPorId(id);
        return retorno;
    }

    @PutMapping("/linkTwitter/{idUsuario}/{link}")
    public ResponseEntity<Usuario> PostLinkTwitter(@PathVariable Long idUsuario, @PathVariable String link) {
        var retorno = usuarioService.InserirLinkTwitter(idUsuario, link);
        if (retorno != null){
            return ResponseEntity.status(200).body(retorno);
        }
        return ResponseEntity.status(404).build();
    }

    @PutMapping("/linkLinkedin/{idUsuario}/{link}")
    public ResponseEntity<Usuario> PostLinkLinkedin(@PathVariable Long idUsuario, @PathVariable String link) {
        var retorno = usuarioService.InserirLinkLinkedin(idUsuario, link);
        if (retorno != null){
            return ResponseEntity.status(200).body(retorno);
        }
        return ResponseEntity.status(404).build();
    }
}
