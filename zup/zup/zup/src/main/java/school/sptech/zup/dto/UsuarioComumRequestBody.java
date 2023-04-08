package school.sptech.zup.dto;

import lombok.Data;

@Data
public class UsuarioComumRequestBody extends UsuarioPostRequestBody{
    private String cpf;
}
