package school.sptech.zup.dto;

import lombok.Data;

@Data
public class UsuarioPutRequestBody {

    private Long id;
    private String nome;

    private String email;

    private String username;

    private String cpf;

    private String senha;

}
