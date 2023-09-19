package school.sptech.zup.controller;

import com.azure.core.annotation.Get;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.zup.domain.Noticia;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.response.ComentarioMobileResponse;
import school.sptech.zup.dto.response.NoticiaMobileResponse;
import school.sptech.zup.dto.response.PerfilUsuarioResponse;
import school.sptech.zup.service.AdminService;
import school.sptech.zup.service.NoticiaService;
import school.sptech.zup.service.UsuarioService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/mobile")
@RequiredArgsConstructor
@Log4j2
public class MobileController {
    private final AdminService _adminService;
    private final UsuarioService _usuarioService;
    private final NoticiaController _noticiaController;
    private final NoticiaService _noticiaService;
    @GetMapping(value = "xml", produces = "text/xml")
    public String GetNoticiaFeed() throws JsonProcessingException {
            ObjectMapper jsonMapper = new ObjectMapper();
            XmlMapper xmlMapper = new XmlMapper();
            var retorno = _adminService.getNoticiasFilaPilha();
            String json = jsonMapper.writeValueAsString(retorno);
            String xml = xmlMapper.writeValueAsString(jsonMapper.readValue(json, Object.class));
            return xml;
    }

    @GetMapping("/usuarios/perfil/{IdPerfil}")
    public ResponseEntity<List<PerfilUsuarioResponse>> BuscaUsuariosInfluencerTpPerfil(@PathVariable Long IdPerfil){
        var buscar = _usuarioService.getPerfis(IdPerfil);
        return ResponseEntity.ok().body(buscar);
    }

    @GetMapping("/noticias/feed")
    public ResponseEntity<List<NoticiaMobileResponse>> getNoticiasMobile(){
        var buscarNoticias = _noticiaController.getNoticia();
        if (buscarNoticias.getStatusCodeValue() == 200){
            var MappingNoticia = _noticiaService.getNoticiaMobile(buscarNoticias.getBody());
            return ResponseEntity.ok().body(MappingNoticia);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notícia não encontrada");
    }

    @GetMapping("/noticias/feed/comentarios/{idNoticia}")
    public ResponseEntity<List<ComentarioMobileResponse>> getComentarioMobile(@PathVariable int idNoticia){
        var buscarComentarios = _noticiaService.getComentarioNoticiaPorIdMobile(idNoticia);
        return ResponseEntity.ok().body(buscarComentarios);
    }
}
