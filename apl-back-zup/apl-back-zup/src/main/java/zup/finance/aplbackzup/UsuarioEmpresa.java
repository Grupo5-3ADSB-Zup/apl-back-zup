package zup.finance.aplbackzup;

public class UsuarioEmpresa extends Usuario {
    private String cnpj;

    public UsuarioEmpresa(String nome, String email, String username, String senha, boolean isInfluencer, boolean autenticado, String cnpj) {
        super(nome, email, username, senha, isInfluencer, autenticado);
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
