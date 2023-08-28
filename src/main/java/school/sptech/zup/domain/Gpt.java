package school.sptech.zup.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Gpt {
    @Schema(name = "id", description = "Id da publicação", example = "200")
    private int id;
    @Schema(name = "titulo", description = "Título da notícia", example = "Petrobrás Caiu")
    private String titulo;
    @Schema(name = "pergunta", description = "Pergunta que você pode fazer", example = "Baseado na Notícia x Compro Ação da Petrobrás ?")
    private String pergunta;
}
