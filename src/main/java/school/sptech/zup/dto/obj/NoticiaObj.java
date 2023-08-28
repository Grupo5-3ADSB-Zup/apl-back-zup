package school.sptech.zup.dto.obj;

import school.sptech.zup.dto.response.ComentarioResponse;
import school.sptech.zup.dto.response.CurtidaResponse;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

public class NoticiaObj {
    private int id;
    private String titulo;
    private String descricao;
    private String link;
    private String emissora;
    private String dtNoticia;
    @Column(length = 50 * 1024 * 1024)
    private byte[] fotoNoticia;
    private List<ComentarioResponse> comentarios = new ArrayList();
    private List<CurtidaResponse> curtidas = new ArrayList<>();
    private Integer QtdComentarios;
    private Integer QtdCurtidas;
    public NoticiaObj(int id, String titulo, String descricao,
                      String link, String emissora,
                      String dtNoticia, byte[] fotoNoticia, Integer qtdComentarios, Integer qtdCurtidas) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.link = link;
        this.emissora = emissora;
        this.dtNoticia = dtNoticia;
        this.fotoNoticia = fotoNoticia;
        this.QtdComentarios = qtdComentarios;
        this.QtdCurtidas = qtdCurtidas;
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
    public String getDtNoticia() {
        return dtNoticia;
    }
    public void setDtNoticia(String dtNoticia) {
        this.dtNoticia = dtNoticia;
    }
    public byte[] getFotoNoticia() {
        return fotoNoticia;
    }
    public void setFotoNoticia(byte[] fotoNoticia) {
        this.fotoNoticia = fotoNoticia;
    }
    public List<ComentarioResponse> getComentarios() {
        return comentarios;
    }
    public void setComentarios(ComentarioResponse comentarios) {
        this.comentarios.add(comentarios);
    }
    public List<CurtidaResponse> getCurtidas() {
        return curtidas;
    }
    public void setCurtidas(CurtidaResponse curtidas) {
        this.curtidas.add(curtidas);
    }
    public Integer getQtdComentarios() {
        return QtdComentarios;
    }
    public void setQtdComentarios(Integer qtdComentarios) {
        QtdComentarios = qtdComentarios;
    }
    public Integer getQtdCurtidas() {
        return QtdCurtidas;
    }
    public void setQtdCurtidas(Integer qtdCurtidas) {
        QtdCurtidas = qtdCurtidas;
    }
}
