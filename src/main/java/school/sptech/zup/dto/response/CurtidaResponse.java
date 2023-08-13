package school.sptech.zup.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import school.sptech.zup.domain.Curtida;

@Data
@NoArgsConstructor
public class CurtidaResponse {
    private Long id;
    private Integer likes;
    private UsuarioResponse usuario;

    public CurtidaResponse(Curtida curtida) {
        this.id = curtida.getId();
        this.likes = curtida.getLikes();
        this.usuario = new UsuarioResponse(curtida.getUsuario());
    }
}
