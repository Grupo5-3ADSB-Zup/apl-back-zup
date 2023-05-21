package school.sptech.zup.service;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import org.springframework.stereotype.Service;
import school.sptech.zup.domain.Gpt;
import school.sptech.zup.domain.Noticia;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionService;

@Service
public class GptService {
    private static final String API_KEY = "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";

    public List<CompletionChoice> gptNoticia(List<Noticia> noticias, Gpt gpt) {
        OpenAiService service = new OpenAiService(API_KEY);

        CompletionRequest request = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(noticias.get(gpt.getId()) + gpt.getPergunta())
                .maxTokens(1000)
                .build();


        return service.createCompletion(request).getChoices();
    }
}
