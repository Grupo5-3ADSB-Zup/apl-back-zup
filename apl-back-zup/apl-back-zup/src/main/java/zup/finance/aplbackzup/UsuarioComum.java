package zup.finance.aplbackzup;

public class UsuarioComum extends Usuario {
    private String cpf;

    public UsuarioComum(String nome, String cpf) {
        super(nome);
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

