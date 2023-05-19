package school.sptech.zup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.dto.obj.UsuarioObj;
import school.sptech.zup.service.AdminService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/admin")
@Log4j2
@RequiredArgsConstructor
public class AdminController {

    private final AdminService _adminService;

    private final NoticiaController _noticiaController;

    private UsuarioObj[] vetor;
    private int nroElem;

    @PostMapping("/csv/{nomeArquivo}")
    public ResponseEntity<Object>  gravarArquivoCsv(@PathVariable String nomeArquivo) {
        var retorno = _adminService.getListUsuario();
        if (retorno.getStatusCodeValue() == 200){
             _adminService.gravarArquivoCsv(retorno.getBody(), nomeArquivo);
             return ResponseEntity.status(201).build();
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/{indice}")
    public ResponseEntity<UsuarioObj>  pesquisaBinaria(@PathVariable String indice) {
        return _adminService.pesquisaBinaria(indice);
    }


    @PostMapping("/txt/{nomeArquivo}")
    public ResponseEntity<Object>  gravarArquivoTXT(@PathVariable String nomeArquivo) {
        var retorno = _adminService.getListUsuario();
        if (retorno.getStatusCodeValue() == 200){
            _adminService.gravarArquivoTxt(retorno.getBody(), nomeArquivo);
            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/filaPilha/noticias")
    public ResponseEntity<Object> retornarFilaPilhaObj(){
            var retorno = _adminService.getNoticiasFilaPilha();
            if (retorno.getStatusCodeValue() == 200){
                return ResponseEntity.status(200).body(retorno);
            }
          return ResponseEntity.status(404).build();
    }
}
