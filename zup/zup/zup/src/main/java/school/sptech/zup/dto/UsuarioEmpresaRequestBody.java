package school.sptech.zup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UsuarioEmpresaRequestBody extends UsuarioPostRequestBody{
    private String cnpj;
}
