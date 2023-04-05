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


    @GetMapping
    public List<Noticia> getRss(){
        try {

            List<Noticia> noticias = new ArrayList<>();


            String url = "http://rss.uol.com.br/feed/economia.xml";

            try (XmlReader reader = new XmlReader(new URL(url))) {
                SyndFeed feed = new SyndFeedInput().build(reader);
                System.out.println(feed.getTitle());
                System.out.println("***********************************");

                for (SyndEntry entry : feed.getEntries()) {
                    Noticia noticia = new Noticia();

                    System.out.println("Titulo: \n" + entry.getTitleEx().getValue() + "\n");
                    noticia.setTitulo(entry.getTitleEx().getValue());

                    System.out.println("Imagem e Descrição: \n" + entry.getDescription().getValue() + "\n");
                    noticia.setDescricao(entry.getDescription().getValue());

                    System.out.println("Link: \n" + entry.getLink() + "\n");
                    noticia.setLink(entry.getLink());

                    System.out.println("***********************************");

                    noticias.add(noticia);
                }
                List<Noticia> noticiaList = _noticiaRepository.saveAll(noticias);
                return noticiaList;
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Noticia>> getNoticias(){
        List<Noticia> noticias = _noticiaRepository.findAll();
        if (noticias.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(noticias);
    }
}
