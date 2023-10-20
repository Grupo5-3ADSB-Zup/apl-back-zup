package school.sptech.zup.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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

    private LocalDateTime dtComentario;
    private String dtComentarioFormatada;

    private Integer pesoComentario;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Noticia noticias;
}
