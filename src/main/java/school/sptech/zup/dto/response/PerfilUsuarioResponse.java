package school.sptech.zup.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;

@Data
public class PerfilUsuarioResponse {
    @Schema(name = "nome", description = "Seu nome", example = "Carlos")
    private String nome;
    @Schema(name = "idPerfil", description = "Perfis do usuário: 1 - Conversador; 2 - Moderado; 3- Agressivo", example = "Moderado")
    private Long idPerfil;
    @Schema(name = "LinkYoutube", description = "Youtube do Influencer", example = "Primo Rico")
    private String linkYoutube;
    @Schema(name = "LinkInsta", description = "Instagram do Influencer", example = "Primo Rico")
    private String linkInstagram;
    @Schema(name = "LinkTikTok", description = "TikTok do Influencer", example = "Primo Rico")
    private String linkTikTok;
    @Schema(name = "DescPerfil", description = "Perfis do usuário: 1 - Conversador; 2 - Moderado; 3- Agressivo", example = "Moderado")
    private String descPerfil;
    @Column(length = 50 * 1024 * 1024)
    private byte[] foto;


}
