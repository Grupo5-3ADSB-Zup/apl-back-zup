package school.sptech.zup.dto.obj;

import school.sptech.zup.util.enumerador.EnumUsuario;

public class UsuarioObj {
    private Long id;

    private String nome;

    private String email;

    private String username;

    private String senha;

//    private boolean influencer;

//    private Boolean autenticado;
//
//    private boolean logado;

    private String enumUsuario;

    private String cpf;

    private String cnpj;



    public UsuarioObj() {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.username = username;
        this.senha = senha;
//        this.influencer = influencer;
//        this.autenticado = autenticado;
//        this.logado = logado;
        this.enumUsuario = enumUsuario;
        this.cpf = cpf;
        this.cnpj = cnpj;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

//    public boolean isInfluencer() {
//        return influencer;
//    }

//    public void setInfluencer(boolean influencer) {
//        this.influencer = influencer;
//    }

//    public Boolean getAutenticado() {
//        return autenticado;
//    }

//    public void setAutenticado(Boolean autenticado) {
//        this.autenticado = autenticado;
//    }

//    public boolean isLogado() {
//        return logado;
//    }

//    public void setLogado(boolean logado) {
//        this.logado = logado;
//    }


    public String getEnumUsuario() {
        return enumUsuario;
    }

    public void setEnumUsuario(String enumUsuario) {
        this.enumUsuario = enumUsuario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
