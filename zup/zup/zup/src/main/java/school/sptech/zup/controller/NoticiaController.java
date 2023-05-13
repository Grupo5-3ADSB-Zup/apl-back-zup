package school.sptech.zup.controller;

import com.theokanning.openai.completion.CompletionChoice;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.domain.Gpt;
import school.sptech.zup.domain.Noticia;
import school.sptech.zup.repository.NoticiaRepository;
import school.sptech.zup.service.GptService;
import school.sptech.zup.service.NoticiaService;
import school.sptech.zup.util.DateUtil;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/noticia")
@RequiredArgsConstructor
@Log4j2
public class NoticiaController {
    @Autowired
    private NoticiaRepository _noticiaRepository;
    @Autowired
    private NoticiaService _noticiaService;
    @Autowired
    private GptService _gptService;

    @GetMapping("/rss/uol")
    public ResponseEntity<List<Noticia>> getRssUOL(){
        var retorno = _noticiaService.getXmlUOL();
        if (retorno.getStatusCodeValue() == 200){
            return retorno;
        }
        return retorno;
    }

    @GetMapping("/rss/gazeta")
    public ResponseEntity<List<Noticia>> getRssGazeta(){
        var retorno = _noticiaService.getXmlGAZETA();
        if (retorno.getStatusCodeValue() == 200){
            return retorno;
        }
        return retorno;
    }

    @GetMapping("/rss")
    public ResponseEntity<List<Noticia>> getNoticias(){
        List<Noticia> noticias = _noticiaRepository.findAll();
        if (noticias.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(noticias);
    }

    @PostMapping("/rss/info")
    public ResponseEntity<List<CompletionChoice>> InserirNoticiasGPT(@RequestBody Gpt gpt){
        var consulta = getNoticias();
        var consultaTituloNoticia = _noticiaService.procuraPorNome(gpt);

        if (consulta.getStatusCodeValue() == 200 && consultaTituloNoticia.getStatusCodeValue() == 200){
            var retorno = _gptService.gptNoticia(consulta.getBody(), gpt);
            return ResponseEntity.status(200).body(retorno);
        }
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping
    public ResponseEntity<List<Noticia>> delelte(){
        List<Noticia> noticias = _noticiaRepository.findAll();
        if (noticias.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        _noticiaRepository.deleteAll(noticias);
        return ResponseEntity.status(200).build();
    }
}
