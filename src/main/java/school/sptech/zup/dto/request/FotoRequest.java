package school.sptech.zup.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
public class FotoRequest {
    @Column(length = 50 * 1024 * 1024)
    private byte[] foto;
}
