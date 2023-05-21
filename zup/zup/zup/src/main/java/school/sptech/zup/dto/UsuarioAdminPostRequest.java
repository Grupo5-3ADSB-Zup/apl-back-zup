package school.sptech.zup.dto;

import lombok.Data;

@Data
public class UsuarioAdminPostRequest extends UsuarioPostRequestBody{
    private int admin;

    public UsuarioAdminPostRequest(String nome, String email, String username, String senha, boolean influencer, Boolean autenticado, boolean logado, int admin) {
        super(nome, email, username, senha, influencer, autenticado, logado);
        this.admin = admin;
    }
}
