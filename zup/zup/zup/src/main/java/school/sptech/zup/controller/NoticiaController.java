package school.sptech.zup.controller;

import com.theokanning.openai.completion.CompletionChoice;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.domain.Gpt;
import school.sptech.zup.domain.Noticia;
import school.sptech.zup.dto.obj.NoticiaObj;
import school.sptech.zup.dto.request.ComentarioRequest;
import school.sptech.zup.dto.request.LikesRequest;
import school.sptech.zup.dto.response.GptResponse;
import school.sptech.zup.repository.NoticiaRepository;
import school.sptech.zup.service.GptService;
import school.sptech.zup.service.NoticiaService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/noticia")
@RequiredArgsConstructor
@Log4j2
public class NoticiaController {
    private final NoticiaRepository _noticiaRepository;
    private final NoticiaService _noticiaService;
    private final GptService _gptService;

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
    public ResponseEntity<List<Noticia>> getNoticia(){
        var consulta = _noticiaRepository.findAll();
        if (consulta.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(consulta);
    }

    @PostMapping("/rss/info")
    public ResponseEntity<GptResponse> InserirNoticiasGPT(@RequestBody Gpt gpt){
        var consulta = getNoticia();
        var consultaTituloNoticia = _noticiaService.procuraPorNome(gpt);

        if (consulta.getStatusCodeValue() == 200 && consultaTituloNoticia.getStatusCodeValue() == 200){
            var retorno = _gptService.gptNoticia(consulta.getBody(), gpt);
            return ResponseEntity.status(200).body(retorno);
        }
        return ResponseEntity.status(404).build();
    }

    @PutMapping("/comentarios/{id}")
    public ResponseEntity<Noticia> salvarComentario(@RequestBody ComentarioRequest comentario, @PathVariable int id){
        var consulta = _noticiaService.buscarNoticiaPorIdComentario(comentario, id);
        if (consulta.getStatusCodeValue() == 200){
            return ResponseEntity.status(200).body(consulta.getBody());
        }
        return ResponseEntity.status(404).build();
    }

    @PutMapping("/likes/{id}")
    public ResponseEntity<Noticia> salvarLikes(@RequestBody LikesRequest likes, @PathVariable int id){
        var consulta = _noticiaService.buscarNoticiaPorIdLikes(likes, id);
        if (consulta.getStatusCodeValue() == 200){
            return ResponseEntity.status(200).body(consulta.getBody());
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
