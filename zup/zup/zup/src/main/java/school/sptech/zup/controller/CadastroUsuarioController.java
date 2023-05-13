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

@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<Usuario> atualizarUserEmpresa(@RequestBody UsuarioAdminPutRequest usuarioPutRequestBody) {
        var retorno = _cadastroService.atualizarUsuarioAdmin(usuarioPutRequestBody);
        return retorno;
    }

    @PutMapping("user/admin")
    public ResponseEntity<Usuario> atualizarUserEmpresa(@RequestBody UsuarioEmpresaPutRequestBody usuarioPutRequestBody) {
        var retorno = _cadastroService.atualizarUsuarioEmpresa(usuarioPutRequestBody);
        return retorno;
    }

    @PatchMapping(value = "/foto/{idFoto}")
    public ResponseEntity<Void> adicionarImagem(@PathVariable Long idFoto, @RequestBody byte[] foto){
        var retorno = _cadastroService.buscaPorId(idFoto);
        if (retorno.getStatusCodeValue() == 200){
            _usuarioRepository.setFoto(idFoto, foto);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping(value = "/foto/{idFoto}")
    public ResponseEntity<byte[]> retornaImagem(@PathVariable Long idFoto){
        var retorno = _usuarioService.BuscarImagemPorId(idFoto);
        if (retorno.getStatusCodeValue() == 200){
            return ResponseEntity.status(200).body(retorno.getBody());
        }
        return retorno;
    }
}
