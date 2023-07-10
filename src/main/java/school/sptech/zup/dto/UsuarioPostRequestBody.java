package school.sptech.zup.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public abstract class UsuarioPostRequestBody {
    @Schema(name = "nome", description = "Seu nome", example = "Carlos")
    private String nome;
    @Schema(name = "email", description = "Seu Email", example = "Carlos@gmail.com")
    private String email;
    @Schema(name = "username", description = "Crie o seu usuário no site", example = "Carlow")
    private String username;
    @Schema(name = "senha", description = "Crie uma senha de acesso", example = "Carlos1994")
    private String senha;
    @Schema(name = "Influencer", description = "Você é um influenciador ?", example = "@PrimoRico")
    private boolean influencer;
    private Boolean autenticado;
    private byte[] foto;
}
