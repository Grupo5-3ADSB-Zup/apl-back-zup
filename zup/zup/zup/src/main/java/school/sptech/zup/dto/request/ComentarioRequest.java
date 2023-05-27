package school.sptech.zup.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComentarioRequest {
    private int id;
    private String comentario;
}
