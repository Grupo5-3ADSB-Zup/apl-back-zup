package school.sptech.zup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.obj.NoticiaObj;
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

    @GetMapping("/csv/{nomeArquivo}")
    public ResponseEntity<byte[]> baixarArquivo(@PathVariable String nomeArquivo) {
        var retorno = _adminService.getListUsuario();
        var consulta =  _adminService.gravarArquivoCsv(retorno, nomeArquivo);

          if (consulta.getStatusCodeValue() == 200){
              return ResponseEntity.status(200).body(consulta.getBody());
          }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/{indice}")
    public ResponseEntity<UsuarioObj> pesquisaBinaria(@PathVariable String indice) {
        UsuarioObj consulta = _adminService.pesquisaBinaria(indice);

        return ResponseEntity.ok(consulta);
    }

    @PostMapping(value = "/exportacao/txt/{nomeArquivo}")
    public ResponseEntity<BufferedWriter> gravarArquivoTXT(@PathVariable String nomeArquivo) {
        var retorno = _adminService.getListUsuario();
            var gerar = _adminService.gravarArquivoTxt(retorno, nomeArquivo);

            return ResponseEntity.status(201).build();
    }

    @PostMapping(value = "/importacao/txt", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BufferedReader> importarArquivoTXT(@RequestParam("arquivo") MultipartFile arquivo) {
        _adminService.lerArquivoTxt(arquivo);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filaPilha/noticias")
    public ResponseEntity<List<NoticiaObj>> retornarFilaPilhaObj() {
        var retorno = _adminService.getNoticiasFilaPilha();

        return ResponseEntity.ok(retorno);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getTodosOsUsuarios() {
        var retorno = _adminService.getListTodosUsuario();
        return ResponseEntity.ok(retorno);
    }

    @PutMapping("influencer/comum/{idUsuario}/{influencer}")
    public ResponseEntity<Usuario> atualizarUserComum(@PathVariable Long idUsuario, @PathVariable boolean influencer) {
        var retorno = _adminService.atualizarUsuarioParaInfluencer(idUsuario, influencer);
        return ResponseEntity.ok(retorno);
    }
}
