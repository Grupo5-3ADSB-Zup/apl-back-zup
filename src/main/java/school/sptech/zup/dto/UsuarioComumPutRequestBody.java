package school.sptech.zup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UsuarioComumPutRequestBody extends UsuarioPutRequestBody{
    @Schema(name = "cpf", description = "Seu CPF", example = "123456764532")
    private String cpf;
}
