package school.sptech.zup.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComentarioResponse {
    private String nome;
    private String descricao;
    private byte[] foto;
}
