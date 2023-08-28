package school.sptech.zup.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Curtida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(name = "likes", description = "Curtidas da notícia", example = "30")
    private Integer likes;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Noticia noticias;
}
