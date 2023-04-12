package school.sptech.zup.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Login {
    private String email;
    private String username;
    private String senha;
}
