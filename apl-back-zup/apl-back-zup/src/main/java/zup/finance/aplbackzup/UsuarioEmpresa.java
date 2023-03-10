package zup.finance.aplbackzup;

public class UsuarioEmpresa extends Usuario {
    private String cnpj;

    public UsuarioEmpresa(String nome, String cnpj) {
        super(nome);
        this.cnpj = cnpj;
    }

    public UsuarioEmpresa() {
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
