package school.sptech.zup.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Noticia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Schema(name = "titulo", description = "Titulo da notícia", example = "Petrobrás Caiu")
    private String titulo;
    @Lob
    @Column(name="descricao", length=2048)
    @Schema(name = "descricao", description = "Descrição da notícia", example = "Petrobrás Caiu ontem...")
    private String descricao;
    @Schema(name = "link", description = "Link da notícia original", example = "https://...")
    private String link;
    @Schema(name = "emissora", description = "Emissora da notícia", example = "UOL")
    private String emissora;
    @Schema(name = "dtNoticiaFormatado", description = "Data da notícia", example = "20/04/2022")
    private String dtNoticiaFormatado;
    @Schema(name = "dtNoticia", description = "Data da notícia", example = "20/04/2022")
    private Date dtNoticia;
    @Column(length = 50 * 1024 * 1024)
    private byte[] foto;
}
