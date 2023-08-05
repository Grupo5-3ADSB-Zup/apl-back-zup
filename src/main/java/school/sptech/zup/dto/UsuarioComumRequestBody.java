package school.sptech.zup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UsuarioComumRequestBody extends UsuarioPostRequestBody{
    @CPF
    @NotNull
    @Schema(name = "cpf", description = "Seu CPF", example = "123456764532")
    private String cpf;
}
