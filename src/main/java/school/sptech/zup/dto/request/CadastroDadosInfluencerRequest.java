package school.sptech.zup.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CadastroDadosInfluencerRequest {
    private Integer idUsuario;
    private String tiktok;
    private String youtube;
    private String instagram;
}
