package school.sptech.zup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.zup.domain.Noticia;

@RestController
@RequestMapping("/rss")
@RequiredArgsConstructor
public class NoticiaController {

    @PostMapping
    public void post(@RequestBody Noticia not){
        System.out.println(not);
    }
}
