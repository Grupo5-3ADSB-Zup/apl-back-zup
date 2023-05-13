package school.sptech.zup.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.UsuarioAdminPutRequest;
import school.sptech.zup.dto.UsuarioEmpresaPutRequestBody;
import school.sptech.zup.dto.obj.UsuarioObj;
import school.sptech.zup.repository.UsuarioRepository;
import school.sptech.zup.service.UsuarioService;
import school.sptech.zup.util.DateUtil;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/usuario")
@Log4j2
@RequiredArgsConstructor
public class UsuarioController {
    @Autowired
    private final DateUtil dateUtil;

    @Autowired
    private final UsuarioService usuarioService;

    @Autowired
    private final UsuarioRepository _usuarioRepository;

    private UsuarioObj[] vetor;
    private int nroElem;

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

    @PutMapping("user/empresa")
    public ResponseEntity<Usuario> atualizarUserEmpresa(@RequestBody UsuarioAdminPutRequest usuarioPutRequestBody) {
        var retorno = usuarioService.atualizarUsuarioAdmin(usuarioPutRequestBody);
        return retorno;
    }

    @PutMapping("user/admin")
    public ResponseEntity<Usuario> atualizarUserEmpresa(@RequestBody UsuarioEmpresaPutRequestBody usuarioPutRequestBody) {
        var retorno = usuarioService.atualizarUsuarioEmpresa(usuarioPutRequestBody);
        return retorno;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> deleteUser(@PathVariable long id) {
        var retorno = usuarioService.deleteUser(id);
        return retorno;
    }


}