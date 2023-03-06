package zup.finance.aplbackzup;

public class UsuarioComum extends Usuario {
    private String cpf;

    public UsuarioComum(String nome, String email, String username, String senha, boolean isInfluencer, String cpf) {
        super(nome, email, username, senha, isInfluencer);
        this.cpf = cpf;
    }

    public UsuarioComum() {
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}

