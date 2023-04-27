package school.sptech.zup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UsuarioEmpresaPutRequestBody extends UsuarioPutRequestBody{
    @Schema(name = "cnpj", description = "CNPJ da sua empresa", example = "12345678987654")
    private String cnpj;
}
