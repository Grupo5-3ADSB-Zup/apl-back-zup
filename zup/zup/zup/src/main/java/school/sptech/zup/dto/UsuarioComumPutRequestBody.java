package school.sptech.zup.dto;

import lombok.Data;

@Data
public class UsuarioComumPutRequestBody extends UsuarioPutRequestBody{
    private String cpf;
}
