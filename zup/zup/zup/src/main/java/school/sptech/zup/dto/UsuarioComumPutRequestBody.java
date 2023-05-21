package school.sptech.zup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UsuarioComumPutRequestBody extends UsuarioPutRequestBody{
    @Schema(name = "cpf", description = "Seu CPF", example = "123456764532")
    private String cpf;

    public UsuarioComumPutRequestBody(Long id, String nome, String email, String username, String senha, boolean influencer, Boolean autenticado, boolean logado, String cpf) {
        super(id, nome, email, username, senha, influencer, autenticado, logado);
        this.cpf = cpf;
    }
}
