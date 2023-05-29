package school.sptech.zup.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GptResponse {

    private int id;
    private String resposta;
}
