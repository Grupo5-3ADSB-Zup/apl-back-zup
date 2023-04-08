package school.sptech.zup.service;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import org.springframework.stereotype.Service;
import school.sptech.zup.domain.Noticia;

import java.util.List;
import java.util.concurrent.CompletionService;

@Service
public class GptService {
    private static final String API_KEY = "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";

    public List<CompletionChoice> gptNoticia(List<Noticia> noticias) {
        OpenAiService service = new OpenAiService(API_KEY);

        CompletionRequest request = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(noticias.get(13).getDescricao() + "Dado isso, existem outros noticiários que publicaram isso ?")
                .maxTokens(1000)
                .build();
        return service.createCompletion(request).getChoices();
    }
}
