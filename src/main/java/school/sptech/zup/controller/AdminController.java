package school.sptech.zup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.obj.NoticiaObj;
import school.sptech.zup.dto.obj.PilhaObj;
import school.sptech.zup.dto.obj.UsuarioObj;
import school.sptech.zup.service.AdminService;

import java.io.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin")
@Log4j2
@RequiredArgsConstructor
public class AdminController {

    private final AdminService _adminService;

    private final NoticiaController _noticiaController;

    private UsuarioObj[] vetor;
    private int nroElem;

    @GetMapping("/csv/{nomeArquivo}")
    public ResponseEntity<byte[]> baixarArquivo(@PathVariable String nomeArquivo) {

        var retorno = _adminService.getListUsuario();
        if (retorno.getStatusCodeValue() == 200) {
          var consulta =  _adminService.gravarArquivoCsv(retorno.getBody(), nomeArquivo);

          if (consulta.getStatusCodeValue() == 200){
              return ResponseEntity.status(200).body(consulta.getBody());
          }
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/{indice}")
    public ResponseEntity<UsuarioObj> pesquisaBinaria(@PathVariable String indice) {
        return _adminService.pesquisaBinaria(indice);
    }


    @PostMapping(value = "/exportacao/txt/{nomeArquivo}")

    public ResponseEntity<BufferedWriter> gravarArquivoTXT(@PathVariable String nomeArquivo) {
        var retorno = _adminService.getListUsuario();
        if (retorno.getStatusCodeValue() == 200) {
            var gerar = _adminService.gravarArquivoTxt(retorno.getBody(), nomeArquivo);

            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.status(404).build();
    }



    @PostMapping(value = "/importacao/txt", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BufferedReader> importarArquivoTXT(@RequestParam("arquivo") MultipartFile arquivo) {
        var retorno = _adminService.lerArquivoTxt(arquivo);
        if (retorno.getStatusCodeValue() == 201) {
            return retorno;
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/filaPilha/noticias")
    public ResponseEntity<List<NoticiaObj>> retornarFilaPilhaObj() {
        var retorno = _adminService.getNoticiasFilaPilha();
        if (retorno.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(retorno);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getTodosOsUsuarios() {
        var retorno = _adminService.getListTodosUsuario();

        if (retorno.getStatusCodeValue() == 200) {
            return ResponseEntity.status(200).body(retorno.getBody());
        }
        return ResponseEntity.status(404).build();
    }
}
