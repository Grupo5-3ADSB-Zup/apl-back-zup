package school.sptech.zup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Gpt {
    private int id;
    private String titulo;
    private String pergunta;
}
