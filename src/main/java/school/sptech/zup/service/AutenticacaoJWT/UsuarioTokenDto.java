package school.sptech.zup.service.AutenticacaoJWT;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UsuarioTokenDto {
    private Long id;
    private String nome;
    private String username;
    private String token;
}
