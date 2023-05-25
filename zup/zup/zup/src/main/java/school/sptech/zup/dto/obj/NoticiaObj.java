package school.sptech.zup.dto.obj;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.time.LocalDateTime;

public class NoticiaObj {
    private int id;
    private String titulo;
    private String descricao;

    private String link;
    private String emissora;
    private LocalDateTime dtNoticia;
    private Integer likes;
    private String comentario;

    @Column(length = 50 * 1024 * 1024)
    private byte[] foto;

    public NoticiaObj(int id, String titulo, String descricao, String link, String emissora, LocalDateTime dtNoticia, Integer likes, String comentario, byte[] foto) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.link = link;
        this.emissora = emissora;
        this.dtNoticia = dtNoticia;
        this.likes = likes;
        this.comentario = comentario;
        this.foto = foto;
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

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
