package school.sptech.zup.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
public class FotoResponse {
    @Column(length = 50 * 1024 * 1024)
    private byte[] foto;
}
