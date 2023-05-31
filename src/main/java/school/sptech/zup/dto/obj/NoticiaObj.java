package school.sptech.zup.dto.obj;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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
    @Column(length = 50 * 1024 * 1024)
    private byte[] fotoNoticia;

    private Long idComentario;
    private String nomeUsuario;
    @Lob
    @Column(name="descricao", length=2048)
    private String descricaoComentario;
    private byte[] fotoUsuario;

    public NoticiaObj(int id, String titulo, String descricao, String link, String emissora, LocalDateTime dtNoticia, Integer likes, byte[] fotoNoticia, Long idComentario, String nomeUsuario, String descricaoComentario, byte[] fotoUsuario) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.link = link;
        this.emissora = emissora;
        this.dtNoticia = dtNoticia;
        this.likes = likes;
        this.fotoNoticia = fotoNoticia;
        this.idComentario = idComentario;
        this.nomeUsuario = nomeUsuario;
        this.descricaoComentario = descricaoComentario;
        this.fotoUsuario = fotoUsuario;
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

    public byte[] getFotoNoticia() {
        return fotoNoticia;
    }

    public void setFotoNoticia(byte[] fotoNoticia) {
        this.fotoNoticia = fotoNoticia;
    }

    public Long getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Long idComentario) {
        this.idComentario = idComentario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getDescricaoComentario() {
        return descricaoComentario;
    }

    public void setDescricaoComentario(String descricaoComentario) {
        this.descricaoComentario = descricaoComentario;
    }

    public byte[] getFotoUsuario() {
        return fotoUsuario;
    }

    public void setFotoUsuario(byte[] fotoUsuario) {
        this.fotoUsuario = fotoUsuario;
    }
}
