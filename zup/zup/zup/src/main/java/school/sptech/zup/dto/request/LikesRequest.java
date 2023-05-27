package school.sptech.zup.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LikesRequest {
    private int id;
    private Integer likes;
}
