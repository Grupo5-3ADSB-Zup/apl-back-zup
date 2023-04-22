package school.sptech.zup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Gpt {
    @Schema(name = "id", description = "Id da publicação", example = "200")
    private int id;
    @Schema(name = "titulo", description = "Titulo da Notícia", example = "Petrobrás Caiu")
    private String titulo;
    @Schema(name = "pergunta", description = "Pergunta que você pode fazer", example = "Baseado na Notícia. Compro Ação da Petrobrás ?")
    private String pergunta;
}
