package school.sptech.zup.service;


import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.zup.domain.ChaveKey;
import school.sptech.zup.domain.Gpt;
import school.sptech.zup.domain.ZupLog;
import school.sptech.zup.dto.response.GptResponse;
import school.sptech.zup.repository.ChaveKeyRepository;
import school.sptech.zup.repository.ZupLogRepository;
import school.sptech.zup.util.DateUtil;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GptService {
    private final ZupLogRepository _zupLog;
    private final ChaveKeyRepository _chaveKey;
    private final DateUtil _dateutil;
    //private static final String API_KEY ="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"; A CHAVE NOVA AQUI

    private static final String API_KEY ="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";
    //private String API_KEY = null;
    public ChaveKey InserirChave(String idChave){
        ChaveKey chave = new ChaveKey();
        chave.setIdChave(idChave);
        _chaveKey.save(chave);
        return chave;
    }
    GptResponse gptResponse = new GptResponse();
    public GptResponse gptNoticia(Gpt gpt) {

        var buscaChave = _chaveKey.findAll();

        try{
            OpenAiService service = new OpenAiService(buscaChave.get(0).getIdChave());

            CompletionRequest request = CompletionRequest.builder()
                    .model("text-davinci-003")
                    .prompt(gpt.getTitulo() + gpt.getPergunta())
                    .maxTokens(1000)
                    .build();
            gptResponse.setResposta(service.createCompletion(request).getChoices().get(0).getText());
            gptResponse.setId(gpt.getId());
            return gptResponse;
        }
        catch (OpenAiHttpException ex){
            if (ex.statusCode == 401){
                ZupLog log = new ZupLog();
                log.setDescricao("GPT: ERRO 401, Token expirado => " + ex.getMessage());
                log.setDt_entrada(
                        _dateutil.formLocalDate(LocalDateTime.now())
                );
                _zupLog.save(log);
            } else if (ex.statusCode == 404) {
                ZupLog log = new ZupLog();
                log.setDescricao("GPT: ERRO 404 => " + ex.getMessage());
                log.setDt_entrada(
                        _dateutil.formLocalDate(LocalDateTime.now())
                );
                _zupLog.save(log);
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro, acesse o Log!");
    }
}
