package school.sptech.zup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.domain.Noticia;

@RestController
@RequestMapping("/rss")
@RequiredArgsConstructor
public class NoticiaController {

    @CrossOrigin
    @PostMapping
    public void post(@RequestBody Noticia not){
        System.out.println(not);
    }
}
