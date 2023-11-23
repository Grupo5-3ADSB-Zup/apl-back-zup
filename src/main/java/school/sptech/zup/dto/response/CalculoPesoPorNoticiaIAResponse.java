package school.sptech.zup.dto.response;

import lombok.Data;

@Data
public class CalculoPesoPorNoticiaIAResponse {
    private int Id;
    private String PorcentagemPesoCompra;
    private String PorcentagemPesoPensaEmCompra;
    private String PorcentagemPesoNeutro;
    private String PorcentagemPesoPenseEmVender;
    private String PorcentagemPesoVenda;
}
