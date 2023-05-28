package school.sptech.zup.service;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import org.springframework.stereotype.Service;
import school.sptech.zup.domain.Gpt;
import school.sptech.zup.domain.Noticia;
import school.sptech.zup.dto.response.GptResponse;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionService;

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
