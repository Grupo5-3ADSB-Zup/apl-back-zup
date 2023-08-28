package school.sptech.zup.domain;

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
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(name="descricao", length=2048)
    private String descricao;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Noticia noticias;
}
