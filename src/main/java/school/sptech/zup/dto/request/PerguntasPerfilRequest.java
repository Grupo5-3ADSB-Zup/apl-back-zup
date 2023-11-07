package school.sptech.zup.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PerguntasPerfilRequest {
    private String idUsuario;
    private String tela1;
    private String tela2;
    private String tela3;
    private String tela4;
    private String tela5;
}
