package school.sptech.zup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.dto.obj.NoticiaObj;
import school.sptech.zup.dto.obj.PilhaObj;
import school.sptech.zup.dto.obj.UsuarioObj;
import school.sptech.zup.service.AdminService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.List;

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


    @PostMapping(value = "/exportacao/txt/{nomeArquivo}")

    public ResponseEntity<BufferedWriter> gravarArquivoTXT(@PathVariable String nomeArquivo) {
        var retorno = _adminService.getListUsuario();
        if (retorno.getStatusCodeValue() == 200){
             var gerar = _adminService.gravarArquivoTxt(retorno.getBody(), nomeArquivo);

            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.status(404).build();
    }

    @PatchMapping(value = "/importacao/txt", consumes =
            "text/plain")

    public ResponseEntity<BufferedReader>  importarArquivoTXT(@RequestParam String nomeArquivo) {
           var retorno = _adminService.lerArquivoTxt(nomeArquivo);
           if (retorno.getStatusCodeValue() == 201){
               return retorno;
           }
            return ResponseEntity.status(404).build();
    }

    @GetMapping("/filaPilha/noticias")
    public ResponseEntity<List<NoticiaObj>> retornarFilaPilhaObj(){
            var retorno = _adminService.getNoticiasFilaPilha();
            if (retorno.isEmpty()){
                return ResponseEntity.status(404).build();
            }
            return ResponseEntity.status(200).body(retorno);
    }
}
