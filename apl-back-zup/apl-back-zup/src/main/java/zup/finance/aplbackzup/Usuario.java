package zup.finance.aplbackzup;

public abstract class Usuario {
    private String nome;
    private String email;
    private String username;
    private String senha;
    private boolean isInfluencer;

    public Usuario(String nome, String email, String username, String senha, boolean isInfluencer) {
        this.nome = nome;
        this.email = email;
        this.username = username;
        this.senha = senha;
        this.isInfluencer = isInfluencer;
    }

    public Usuario() {
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setInfluencer(boolean influencer) {
        isInfluencer = influencer;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public boolean isInfluencer() {
        return isInfluencer;
    }
}
