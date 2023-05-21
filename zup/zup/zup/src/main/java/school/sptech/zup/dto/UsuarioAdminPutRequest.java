package school.sptech.zup.dto;

import lombok.Data;

@Data
public class UsuarioAdminPutRequest extends UsuarioPutRequestBody{
    private int admin;

    public UsuarioAdminPutRequest(Long id, String nome, String email, String username, String senha, boolean influencer, Boolean autenticado, boolean logado, int admin) {
        super(id, nome, email, username, senha, influencer, autenticado, logado);
        this.admin = admin;
    }
}
