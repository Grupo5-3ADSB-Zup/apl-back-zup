package school.sptech.zup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.*;

@Data
public class UsuarioComumPutRequestBody extends UsuarioPutRequestBody{
    @CPF
    @NotNull
    @Schema(name = "cpf", description = "Seu CPF", example = "12345676453")
    private String cpf;
}
