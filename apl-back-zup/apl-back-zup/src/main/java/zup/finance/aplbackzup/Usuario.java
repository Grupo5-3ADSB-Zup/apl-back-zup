package zup.finance.aplbackzup;

import java.util.Objects;

public abstract class Usuario {
    private String nome;
    private String email;
    private String username;
    private String senha;
    private boolean influencer;
    private Boolean autenticado;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isInfluencer() {
        return influencer;
    }

    public void setInfluencer(boolean influencer) {
        this.influencer = influencer;
    }

    public Boolean getAutenticado() {
        return autenticado;
    }

    public void setAutenticado(Boolean autenticado) {
        this.autenticado = autenticado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(nome, usuario.nome) && Objects.equals(email, usuario.email) && Objects.equals(username, usuario.username) && Objects.equals(senha, usuario.senha) && Objects.equals(influencer, usuario.influencer) && Objects.equals(autenticado, usuario.autenticado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, email, username, senha, influencer, autenticado);
    }
}
