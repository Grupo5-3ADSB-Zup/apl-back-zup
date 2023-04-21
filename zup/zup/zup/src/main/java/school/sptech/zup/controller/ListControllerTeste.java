package school.sptech.zup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.zup.dto.obj.UsuarioObj;
import school.sptech.zup.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/list-usuario")
@Log4j2
@RequiredArgsConstructor
public class ListControllerTeste {
    @Autowired
    private final UsuarioService _usuarioService;

    private UsuarioObj[] vetor;
    private int nroElem;

    @GetMapping("/{nomeArquivo}")
    public ResponseEntity<Object>  gravarArquivoCsv(@PathVariable String nomeArquivo) {
        var retorno = _usuarioService.getListUsuario();
        if (retorno.getStatusCodeValue() == 200){
             _usuarioService.gravarArquivoCsv(retorno.getBody(), nomeArquivo);
        }
        return ResponseEntity.status(404).build();
    }
}
