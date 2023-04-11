package school.sptech.zup.service.AutenticacaoJWT;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioTokenDto {
    private Long id;
    private String nome;
    private String username;
    private String token;
}
