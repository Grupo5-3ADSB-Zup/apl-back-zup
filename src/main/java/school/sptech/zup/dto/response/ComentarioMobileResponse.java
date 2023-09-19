package school.sptech.zup.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Lob;

@Data
public class ComentarioMobileResponse {
    private Long id;
    @Lob
    @Column(name="descricao", length=2048)
    private String descricao;

    //Infos do usuário
    @Schema(name = "nome", description = "Seu nome", example = "Carlos")
    private String nome;
    private byte[] foto;
}
