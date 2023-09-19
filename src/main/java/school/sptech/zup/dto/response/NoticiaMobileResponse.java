package school.sptech.zup.dto.response;

import lombok.Data;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Data
public class NoticiaMobileResponse {
    private int id;
    private String titulo;
    private String descricao;
    private String link;
    private String emissora;
    private String dtNoticia;
    @Column(length = 50 * 1024 * 1024)
    private byte[] fotoNoticia;
}
