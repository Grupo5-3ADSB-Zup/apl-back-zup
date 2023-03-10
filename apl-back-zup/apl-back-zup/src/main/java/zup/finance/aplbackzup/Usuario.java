package zup.finance.aplbackzup;

import java.util.Objects;

public abstract class Usuario {
    private String nome;
    private String email;
    private String username;
    private String senha;
    private boolean isInfluencer;
    private boolean autenticado;

    public Usuario(String nome, String email, String username, String senha, boolean isInfluencer, boolean autenticado) {
        this.nome = nome;
        this.email = email;
        this.username = username;
        this.senha = senha;
        this.isInfluencer = isInfluencer;
        this.autenticado = autenticado;
    }

    public Usuario() {
    }

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
        return isInfluencer;
    }

    public void setInfluencer(boolean influencer) {
        isInfluencer = influencer;
    }

    public boolean isAutenticado() {
        return autenticado;
    }

    public void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return isInfluencer == usuario.isInfluencer && autenticado == usuario.autenticado && nome.equals(usuario.nome) && email.equals(usuario.email) && username.equals(usuario.username) && senha.equals(usuario.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, email, username, senha, isInfluencer, autenticado);
    }
}
