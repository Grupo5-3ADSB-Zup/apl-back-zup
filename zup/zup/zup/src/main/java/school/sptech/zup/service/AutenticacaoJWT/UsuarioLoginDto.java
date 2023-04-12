package school.sptech.zup.service.AutenticacaoJWT;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioLoginDto{
    private String username;
    private String senha;
}
