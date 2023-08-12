package school.sptech.zup.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import school.sptech.zup.domain.Comentario;

@Data
@NoArgsConstructor
public class ComentarioResponse {
    private Long id;

    private String descricao;

    private Integer likes;

    private UsuarioResponse usuario;

    public ComentarioResponse(Comentario comentario) {
        this.id = comentario.getId();
        this.descricao = comentario.getDescricao();
        this.likes = comentario.getLikes();
        this.usuario = new UsuarioResponse(comentario.getUsuario());
    }
}
