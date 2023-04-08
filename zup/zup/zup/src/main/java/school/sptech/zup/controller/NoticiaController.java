package school.sptech.zup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.domain.Noticia;
import school.sptech.zup.repository.NoticiaRepository;
import school.sptech.zup.service.NoticiaService;

import java.util.List;

@RestController
@RequestMapping("/noticia")
@RequiredArgsConstructor
public class NoticiaController {
    @Autowired
    private NoticiaRepository _noticiaRepository;
    @Autowired
    private NoticiaService _noticiaService;

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
