package school.sptech.zup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.zup.dto.response.ComentarioIAResponse;
import school.sptech.zup.dto.response.ComentarioMobileResponse;
import school.sptech.zup.service.NoticiaService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/IA")
@RequiredArgsConstructor
@Log4j2
public class IAController {

    private final NoticiaService _noticiaService;

    @GetMapping("/noticias/comentarios")
    public ResponseEntity<List<ComentarioIAResponse>> BuscarComentariosDiarios(){
        LocalDateTime startDateLocal = LocalDateTime.now().minusDays(1);
        //var startDate = Date.from(startDateLocal.toInstant(ZoneOffset.UTC));
        var busca = _noticiaService.getComentarioIA(startDateLocal);
        return ResponseEntity.ok().body(busca);
    }
}
