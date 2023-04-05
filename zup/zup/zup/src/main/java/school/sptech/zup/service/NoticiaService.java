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

    public ResponseEntity<List<Noticia>>  getXml(){
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
                return ResponseEntity.status(200).body(noticiaList);
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(404).build();
    }
}
