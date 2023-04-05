package school.sptech.zup.dto;

import lombok.Data;

@Data
public class UsuarioPostRequestBody {
    private String nome;

    private String email;

    private String username;

    private String senha;

    private boolean influencer;

    private Boolean autenticado;

    private boolean logado;

    private String cpf;

    private String cnpj;

}
