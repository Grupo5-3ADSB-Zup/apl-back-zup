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

    public NoticiaObj(int id, String titulo, String descricao, String link, String emissora, LocalDateTime dtNoticia) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.link = link;
        this.emissora = emissora;
        this.dtNoticia = dtNoticia;
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
}
