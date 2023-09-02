package school.sptech.zup.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(name = "nome", description = "Seu nome", example = "Carlos")
    private String nome;
    @Schema(name = "email", description = "Seu Email", example = "Carlos@gmail.com")
    private String email;
    @Schema(name = "username", description = "Crie o seu usuário no site", example = "Carlow")
    private String username;
    @Schema(name = "senha", description = "Crie uma senha de acesso", example = "Carlos1994")
    private String senha;
    @Schema(name = "Influencer", description = "Você é um influenciador ?", example = "@PrimoRico")
    private boolean influencer;
    private Boolean autenticado;
    @Schema(name = "cpf", description = "Seu CPF", example = "123456764532")
    private String cpf;
    @Schema(name = "cnpj", description = "CNPJ da sua empresa", example = "12345678987654")
    private String cnpj;
    private int Admin;
    @Column(length = 50 * 1024 * 1024)
    private byte[] foto;
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
