package school.sptech.zup.service.Mappings;

import lombok.Data;
import org.springframework.stereotype.Service;
import school.sptech.zup.domain.Noticia;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.response.ComentarioMobileResponse;
import school.sptech.zup.dto.response.NoticiaMobileResponse;
import school.sptech.zup.dto.response.PerfilUsuarioResponse;
import school.sptech.zup.repository.ComentarioRepository;
import school.sptech.zup.repository.CurtidaRepository;
import school.sptech.zup.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class Mappings {
    private final DateUtil _dateUtil;
    private final ComentarioRepository _comentarioRepository;
    private final CurtidaRepository _curtidaRepository;
    public List<PerfilUsuarioResponse> MappingPerfilUsuarioInfluencer(List<Usuario> usuarios){

        List<PerfilUsuarioResponse> listaUsuariosPerfis = new ArrayList<>();
        for (int i = 0; i < usuarios.size(); i++){
            PerfilUsuarioResponse perfilUsuario = new PerfilUsuarioResponse();
            perfilUsuario.setNome(usuarios.get(i).getNome());
            perfilUsuario.setIdPerfil(usuarios.get(i).getIdPerfil());
            perfilUsuario.setDescPerfil(usuarios.get(i).getDescPerfil());
            perfilUsuario.setLinkInstagram(usuarios.get(i).getLinkInstagram());
            perfilUsuario.setLinkYoutube(usuarios.get(i).getLinkYoutube());
            perfilUsuario.setLinkTikTok(usuarios.get(i).getLinkTikTok());
            perfilUsuario.setFoto(usuarios.get(i).getFoto());

            listaUsuariosPerfis.add(perfilUsuario);
        }
        return listaUsuariosPerfis;
    }

    public List<NoticiaMobileResponse> MappingNoticiasMobile(List<Noticia> noticias){
        List<NoticiaMobileResponse> listaNoticiasMobile = new ArrayList<>();
        for (int i = 0; i < noticias.size(); i++){
            NoticiaMobileResponse noticia = new NoticiaMobileResponse();
            noticia.setId(noticias.get(i).getId());
            noticia.setTitulo(noticias.get(i).getTitulo());
            noticia.setDescricao(noticias.get(i).getDescricao());
            noticia.setLink(noticias.get(i).getLink());
            noticia.setEmissora(noticias.get(i).getEmissora());
            noticia.setDtNoticia(
                    _dateUtil.formDate(noticias.get(i).getDtNoticia())
            );
            noticia.setFotoNoticia(noticias.get(i).getFoto());
            listaNoticiasMobile.add(noticia);
        }
        return listaNoticiasMobile;
    }

    public List<ComentarioMobileResponse> MappingComentariosNoticiasMobile(int idNoticia) {

        //criar for que busca comentarios daquela notícia, nome e foto do usuário que fez aquele comentario (colocar os
        // campos direto no Response da Repository)

        List<ComentarioMobileResponse> comentariosMobile = new ArrayList<>();
        return null;
    }
}