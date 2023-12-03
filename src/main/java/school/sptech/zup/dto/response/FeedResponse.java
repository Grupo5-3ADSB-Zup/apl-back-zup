package school.sptech.zup.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedResponse {
    private Integer id;
    private String titulo;
    private String descricao;
    private String link;
    private String emissora;
    private String dtNoticia;
    private String fotoNoticia;
}
