package school.sptech.zup.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Login {
    @Schema(name = "username", description = "Crie o seu usuário no site", example = "Carlow")
    private String username;
    @Schema(name = "senha", description = "Crie uma senha de acesso", example = "Carlos1994")
    private String senha;
}
