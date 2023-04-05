package school.sptech.zup.controller;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.domain.Noticia;
import school.sptech.zup.repository.NoticiaRepository;
import school.sptech.zup.service.NoticiaService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rss")
@RequiredArgsConstructor
public class NoticiaController {
    @Autowired
    private NoticiaRepository _noticiaRepository;
    @Autowired
    private NoticiaService _noticiaService;


    @GetMapping
    public ResponseEntity<List<Noticia>> getRss(){
        var retorno = _noticiaService.getXml();
        if (retorno.getStatusCodeValue() == 200){
            return retorno;
        }
        return retorno;
    }
    @GetMapping("/todos")
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
