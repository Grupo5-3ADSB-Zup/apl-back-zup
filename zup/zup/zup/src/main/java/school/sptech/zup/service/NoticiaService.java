package school.sptech.zup.service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import school.sptech.zup.domain.Noticia;
import school.sptech.zup.repository.NoticiaRepository;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticiaService {
    private final NoticiaRepository _noticiaRepository;

    public ResponseEntity<List<Noticia>>  getXmlUOL(){
        try {

            List<Noticia> noticias = new ArrayList<>();


            String url = "http://rss.uol.com.br/feed/economia.xml";

            try (XmlReader reader = new XmlReader(new URL(url))) {
                SyndFeed feed = new SyndFeedInput().build(reader);

                for (SyndEntry entry : feed.getEntries()) {
                    Noticia noticia = new Noticia();
                    noticia.setTitulo(entry.getTitleEx().getValue());
                    noticia.setDescricao(entry.getDescription().getValue());
                    noticia.setLink(entry.getLink());
                    noticia.setEmissora("UOL");

                    noticias.add(noticia);
                }
                List<Noticia> noticiaList = _noticiaRepository.saveAll(noticias);
                return ResponseEntity.status(200).body(noticiaList);
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<List<Noticia>>  getXmlGAZETA(){
        try {
            List<Noticia> noticias = new ArrayList<>();


            String url = "https://www.gazetadopovo.com.br/feed/rss/economia.xml";

            try (XmlReader reader = new XmlReader(new URL(url))) {
                SyndFeed feed = new SyndFeedInput().build(reader);

                for (SyndEntry entry : feed.getEntries()) {
                    Noticia noticia = new Noticia();
                    noticia.setTitulo(entry.getTitleEx().getValue());
                    noticia.setDescricao(entry.getDescription().getValue());
                    noticia.setLink(entry.getLink());
                    noticia.setEmissora("Gazeta");

                    noticias.add(noticia);
                }
                List<Noticia> noticiaList = _noticiaRepository.saveAll(noticias);
                return ResponseEntity.status(200).body(noticiaList);
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(404).build();
    }
}
