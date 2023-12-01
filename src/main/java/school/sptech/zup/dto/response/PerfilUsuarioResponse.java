package school.sptech.zup.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import school.sptech.zup.domain.Usuario;

@Data
public class PerfilUsuarioResponse {
    @Schema(name = "nome", description = "Seu nome", example = "Carlos")
    private String nome;
    //private byte[] foto;
    @Schema(name = "idPerfil", description = "Perfis do usuário: 1 - Conversador; 2 - Moderado; 3- Agressivo", example = "Moderado")
    private Long IdPerfil;
    @Schema(name = "LinkYoutube", description = "Youtube do Influencer", example = "Primo Rico")
    private String LinkYoutube;
    @Schema(name = "LinkInsta", description = "Instagram do Influencer", example = "Primo Rico")
    private String LinkInstagram;
    @Schema(name = "LinkTikTok", description = "TikTok do Influencer", example = "Primo Rico")
    private String LinkTikTok;
    @Schema(name = "DescPerfil", description = "Perfis do usuário: 1 - Conversador; 2 - Moderado; 3- Agressivo", example = "Moderado")
    private String DescPerfil;
}
