package school.sptech.zup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String username;

    private String senha;

    private boolean influencer;

    private Boolean autenticado;

    private boolean logado;

    private String cpf;

    private String cnpj;

    @JsonIgnore
    @Column(length = 50 * 1024 * 1024)
    private byte[] foto;
}
