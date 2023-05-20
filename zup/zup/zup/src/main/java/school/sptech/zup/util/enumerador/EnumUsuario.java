package school.sptech.zup.util.enumerador;

public enum EnumUsuario {
    COMUM("Comum"), EMPRESA("Empresa"), ADMIN("Admin"), INFLUENCER("Influencer");

    public String descricao;

    EnumUsuario(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
