package school.sptech.zup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.dto.obj.UsuarioObj;
import school.sptech.zup.service.UsuarioService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/list-usuario")
@Log4j2
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private final UsuarioService _usuarioService;

    private UsuarioObj[] vetor;
    private int nroElem;

    @PostMapping("/csv/{nomeArquivo}")
    public ResponseEntity<Object>  gravarArquivoCsv(@PathVariable String nomeArquivo) {
        var retorno = _usuarioService.getListUsuario();
        if (retorno.getStatusCodeValue() == 200){
             _usuarioService.gravarArquivoCsv(retorno.getBody(), nomeArquivo);
             return ResponseEntity.status(201).build();
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/{indice}")
    public ResponseEntity<UsuarioObj>  pesquisaBinaria(@PathVariable String indice) {
        return _usuarioService.pesquisaBinaria(indice);
    }


    @PostMapping("/txt/{nomeArquivo}")
    public ResponseEntity<Object>  gravarArquivoTXT(@PathVariable String nomeArquivo) {
        var retorno = _usuarioService.getListUsuario();
        if (retorno.getStatusCodeValue() == 200){
            _usuarioService.gravarArquivoTxt(retorno.getBody(), nomeArquivo);
            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.status(404).build();
    }
}
