package school.sptech.zup.dto.obj;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import school.sptech.zup.domain.Comentario;
import school.sptech.zup.dto.response.ComentarioResponse;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NoticiaObj {
    private int id;
    private String titulo;
    private String descricao;
    private String link;
    private String emissora;
    private LocalDateTime dtNoticia;
    @Column(length = 50 * 1024 * 1024)
    private byte[] fotoNoticia;

    private List<ComentarioResponse> comentario = new ArrayList();

    public NoticiaObj(int id, String titulo, String descricao, String link, String emissora, LocalDateTime dtNoticia, Integer likes, byte[] fotoNoticia) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.link = link;
        this.emissora = emissora;
        this.dtNoticia = dtNoticia;
        this.fotoNoticia = fotoNoticia;
    }

    public NoticiaObj() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getEmissora() {
        return emissora;
    }

    public void setEmissora(String emissora) {
        this.emissora = emissora;
    }

    public LocalDateTime getDtNoticia() {
        return dtNoticia;
    }

    public void setDtNoticia(LocalDateTime dtNoticia) {
        this.dtNoticia = dtNoticia;
    }
    public byte[] getFotoNoticia() {
        return fotoNoticia;
    }

    public void setFotoNoticia(byte[] fotoNoticia) {
        this.fotoNoticia = fotoNoticia;
    }

    public List<ComentarioResponse> getComentario() {
        return comentario;
    }

    public void setComentario(ComentarioResponse comentario) {
        this.comentario.add(comentario);
    }
}
