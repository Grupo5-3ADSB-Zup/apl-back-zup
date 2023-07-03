package school.sptech.zup.service.AutenticacaoJWT;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UsuarioLoginDto{
    @Schema(name = "username", description = "Seu username", example = "Carlow")
    private String username;
    @Schema(name = "senha", description = "Seu senha", example = "3395")
    private String senha;
}
