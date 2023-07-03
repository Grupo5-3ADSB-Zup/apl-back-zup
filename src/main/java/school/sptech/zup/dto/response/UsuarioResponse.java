package school.sptech.zup.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import school.sptech.zup.domain.Usuario;

@Data
public class UsuarioResponse {

    private Long id;
    @Schema(name = "nome", description = "Seu nome", example = "Carlos")
    private String nome;
    @Schema(name = "email", description = "Seu Email", example = "Carlos@gmail.com")
    private String email;
    @Schema(name = "username", description = "Crie o seu usuário no site", example = "Carlow")
    private String username;
    @Schema(name = "senha", description = "Crie uma senha de acesso", example = "Carlos1994")
    private String senha;

    private byte[] foto;

    public UsuarioResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.username = usuario.getUsername();
        this.senha = usuario.getSenha();
        this.foto = usuario.getFoto();
    }
}
