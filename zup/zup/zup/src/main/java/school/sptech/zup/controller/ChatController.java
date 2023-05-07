package school.sptech.zup.controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.zup.domain.Chat;
import school.sptech.zup.domain.Usuario;
import school.sptech.zup.dto.UsuarioPostRequestBody;
import school.sptech.zup.dto.UsuarioPutRequestBody;
import school.sptech.zup.service.ChatService;
import school.sptech.zup.util.DateUtil;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/chat")
@Log4j2
@RequiredArgsConstructor
public class ChatController {
    @Autowired
    private final DateUtil dateUtil;
    private final ChatService chatService;

    @GetMapping
    public ResponseEntity<List<Chat>> todosChats() {

        var retorno = chatService.todosOsChats();
        return retorno;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chat> findById(@PathVariable Long id) {
        var retorno = chatService.buscaPorId(id);

        return retorno;
    }

    @PostMapping
    public ResponseEntity<Chat> novoChat(@RequestBody Chat chat) {
        var retorno = chatService.save(chat);

        return retorno;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Chat> deleteChat(@PathVariable long id) {
        var retorno = chatService.deleteChat(id);

        return retorno;
    }
}
