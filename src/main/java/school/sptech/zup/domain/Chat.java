package school.sptech.zup.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(name = "nomeGrupo", description = "Nome do chat", example = "Rombo Americanas")
    private String nomeGrupo;
    @Schema(name = "descricao", description = "Conversas do chat", example = "Acredito... que ...")
    private String descricao;
    @Schema(name = "dtCriacao", description = "Data da primeira mensagem", example = "Olá....")
    private LocalTime dtCriacao;
}
