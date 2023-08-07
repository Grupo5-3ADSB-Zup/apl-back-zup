package school.sptech.zup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import school.sptech.zup.domain.Chat;
import school.sptech.zup.repository.ChatRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;


    public ResponseEntity<List<Chat>> todosOsChats() {
        var consulta = chatRepository.findAll();
        if (consulta.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(consulta);
    }

    public ResponseEntity<Chat> buscaPorId(Long id) {
        Optional<Chat> usuarioConsulta = chatRepository.findById(id);
        if (usuarioConsulta.isPresent()){
            return ResponseEntity.status(200).body(usuarioConsulta.get());
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<Chat> save(Chat novoChat) {
            chatRepository.save(novoChat);
            return ResponseEntity.status(200).body(novoChat);
    }

    public ResponseEntity<Chat> deleteChat(long id) {
        var retorno = buscaPorId(id);
        if (retorno.getStatusCodeValue() == 200){
            chatRepository.deleteById(id);
            return ResponseEntity.status(200).build();
        }
        return retorno;
    }


    
}
