package school.sptech.zup.service;


import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Service;
import school.sptech.zup.domain.Gpt;
import school.sptech.zup.dto.response.GptResponse;

@Service
public class GptService {
    private static final String API_KEY ="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";
    public GptResponse gptNoticia(Gpt gpt) {
        OpenAiService service = new OpenAiService(API_KEY);
        GptResponse gptResponse = new GptResponse();

        CompletionRequest request = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(gpt.getTitulo() + gpt.getPergunta())
                .maxTokens(1000)
                .build();
        gptResponse.setResposta(service.createCompletion(request).getChoices().get(0).getText());
        gptResponse.setId(gpt.getId());
        return gptResponse;
    }
}
