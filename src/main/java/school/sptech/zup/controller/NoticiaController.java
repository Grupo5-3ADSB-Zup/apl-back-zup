package school.sptech.zup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.domain.Comentario;
import school.sptech.zup.domain.Curtida;
import school.sptech.zup.domain.Gpt;
import school.sptech.zup.domain.Noticia;
import school.sptech.zup.dto.request.ComentarioRequest;
import school.sptech.zup.dto.request.LikesRequest;
import school.sptech.zup.dto.response.ComentarioResponse;
import school.sptech.zup.dto.response.GptResponse;
import school.sptech.zup.repository.NoticiaRepository;
import school.sptech.zup.service.GptService;
import school.sptech.zup.service.NoticiaService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/noticia")
@RequiredArgsConstructor
@Log4j2
public class NoticiaController {
    private final NoticiaRepository _noticiaRepository;
    private final NoticiaService _noticiaService;
    private final GptService _gptService;


    //@PostMapping("/rss/agendamento/diario")
    @Scheduled(cron = "0 11 15 * * ?")
    public void Agendamento(){
        //_noticiaService.getXmlInvestopedia(); Não rodar
        //_noticiaService.getXmlMarkets(); Não rodar
       //_noticiaService.getXmlUOL();
       _noticiaService.getXmlGAZETA();
       _noticiaService.getXmlForbes();
       _noticiaService.getXmlGlobo();
       _noticiaService.getXmlCnbc();
       _noticiaService.getXmlFeedsMarketwatch();

        System.out.println("Tarefas Diárias executadas com sucesso");
    }
    @GetMapping("/rss/uol")
    //@Scheduled(cron = "0 1 12 * * ?")
    public ResponseEntity<List<Noticia>> getRssUOL(){
        var retorno = _noticiaService.getXmlUOL();
            System.out.println("Tarefa diária UOL executada com sucesso");
            return ResponseEntity.ok(retorno);
    }

    @GetMapping("/rss/gazeta")
    //@Scheduled(cron = "0 1 12 * * ?")
    public ResponseEntity<List<Noticia>> getRssGazeta(){
        var retorno = _noticiaService.getXmlGAZETA();
            System.out.println("Tarefa diária Gazeta executada com sucesso");
            return ResponseEntity.ok(retorno);
    }

    @GetMapping("/rss/forbes")
    //@Scheduled(cron = "0 2 12 * * ?")
    public ResponseEntity<List<Noticia>> getRssForbes(){
        var retorno = _noticiaService.getXmlForbes();
        System.out.println("Tarefa diária Forbes executada com sucesso");
        return ResponseEntity.ok(retorno);
    }

    @GetMapping("/rss/globo")
    //@Scheduled(cron = "0 2 12 * * ?")
    public ResponseEntity<List<Noticia>> getRssGlobo(){
        var retorno = _noticiaService.getXmlGlobo();
        System.out.println("Tarefa diária Globo executada com sucesso");
        return ResponseEntity.ok(retorno);
    }

    @GetMapping("/rss/investopedia")
    //@Scheduled(cron = "0 2 12 * * ?")
    public ResponseEntity<List<Noticia>> getRssInvestopedia(){
        var retorno = _noticiaService.getXmlInvestopedia();
        System.out.println("Tarefa diária Investopedia executada com sucesso");
        return ResponseEntity.ok(retorno);
    }

    @GetMapping("/rss/cnbc")
    //@Scheduled(cron = "0 2 12 * * ?")
    public ResponseEntity<List<Noticia>> getRssCnbc(){
        var retorno = _noticiaService.getXmlCnbc();
        System.out.println("Tarefa diária Cnbc executada com sucesso");
        return ResponseEntity.ok(retorno);
    }

    @GetMapping("/rss/markets")
    //@Scheduled(cron = "0 2 12 * * ?")
    public ResponseEntity<List<Noticia>> getRssMarkets(){
        var retorno = _noticiaService.getXmlMarkets();
        System.out.println("Tarefa diária Markets executada com sucesso");
        return ResponseEntity.ok(retorno);
    }

    @GetMapping("/rss/feedsMarketwatch")
    //@Scheduled(cron = "0 2 12 * * ?")
    public ResponseEntity<List<Noticia>> getRssFeedsMarketwatch(){
        var retorno = _noticiaService.getXmlFeedsMarketwatch();
        System.out.println("Tarefa diária FeedsMarketwatch executada com sucesso");
        return ResponseEntity.ok(retorno);
    }

    @GetMapping("/rss")
    public ResponseEntity<List<Noticia>> getNoticia(){
        LocalDateTime startDateLocal = LocalDateTime.now().minusDays(1);
        var startDate = Date.from(startDateLocal.toInstant(ZoneOffset.UTC));
        var consulta = _noticiaRepository.listagemNoticias(startDate);
        return ResponseEntity.ok(consulta);
    }

    @GetMapping("/rss/{id}")
    public ResponseEntity<Noticia> getNoticiaId(Integer id){
        Optional<Noticia> consulta = _noticiaRepository.findById(id);

        if (consulta.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(consulta.get());
    }

    @PostMapping("/rss/info")
    public ResponseEntity<GptResponse> InserirNoticiasGPT(@RequestBody Gpt gpt){
        var consultaTituloNoticia = _noticiaService.procuraPorNome(gpt);
        var retorno = _gptService.gptNoticia(gpt);
        return ResponseEntity.status(200).body(retorno);
    }

    @PostMapping("/comentarios/{idUsuario}/{idNoticia}")
    public ResponseEntity<Comentario> salvarComentario(@RequestBody ComentarioRequest comentario,
                                                    @PathVariable Long idUsuario, @PathVariable int idNoticia){
        var consultaNoticia = _noticiaService.buscarNoticiaPorIdComentario(comentario, idNoticia, idUsuario);
        return ResponseEntity.status(200).body(consultaNoticia);
    }

    @PostMapping("/likes/{idUsuario}/{idNoticia}")
    public ResponseEntity<Curtida> salvarLikes(@RequestBody LikesRequest likes,
                                               @PathVariable Long idUsuario, @PathVariable int idNoticia){
        var consulta = _noticiaService.buscarNoticiaPorIdLikes(likes, idUsuario, idNoticia);
        return ResponseEntity.ok(consulta);
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

    @GetMapping("/comentarios")
    public ResponseEntity<List<Comentario>> comentarios(){
        var consulta = _noticiaService.comentarios();
        return ResponseEntity.ok(consulta);
    }

    @GetMapping("/comentarios-id-noticia/{idNoticia}")
    public ResponseEntity<List<ComentarioResponse>> comentarioPorId(@PathVariable long idNoticia){
        var consulta = getNoticiaId((int) idNoticia);
        var consultaComentario = _noticiaService.getComentarioNoticiaPorId(consulta.getBody());
        return ResponseEntity.ok(consultaComentario);
    }

    @GetMapping("/rss/isolado/{id}")
    public ResponseEntity<Optional<Noticia>> getNoticiaPorId(int id){
        Optional<Noticia> noticiaPorId = _noticiaRepository.findById(id);
        if (noticiaPorId.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(noticiaPorId);
    }

    @PostMapping("/chave/gpt/{idChave}")
    public ResponseEntity<Optional<Noticia>> InserirChave(@PathVariable String idChave){
        var inserirChave = _gptService.InserirChave(idChave);
        return ResponseEntity.ok().build();
    }
}
