package school.sptech.zup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UsuarioEmpresaRequestBody extends UsuarioPostRequestBody{
    @Schema(name = "cnpj", description = "CNPJ da sua empresa", example = "12345678987654")
    private String cnpj;

    public UsuarioEmpresaRequestBody(String nome, String email, String username, String senha, boolean influencer, Boolean autenticado, boolean logado, String cnpj) {
        super(nome, email, username, senha, influencer, autenticado, logado);
        this.cnpj = cnpj;
    }
}
