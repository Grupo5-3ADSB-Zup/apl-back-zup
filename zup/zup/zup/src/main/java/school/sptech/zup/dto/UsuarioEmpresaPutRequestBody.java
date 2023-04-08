package school.sptech.zup.dto;

import lombok.Data;

@Data
public class UsuarioEmpresaPutRequestBody extends UsuarioPutRequestBody{
    private String cnpj;
}
