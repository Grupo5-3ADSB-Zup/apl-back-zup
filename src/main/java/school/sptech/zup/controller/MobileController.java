package school.sptech.zup.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.zup.dto.obj.NoticiaObj;
import school.sptech.zup.service.AdminService;
import school.sptech.zup.service.NoticiaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/mobile")
@RequiredArgsConstructor
@Log4j2
public class MobileController {

    private final AdminService _adminService;

    @GetMapping("/xml")
    public String getTeste() throws JsonProcessingException {
            ObjectMapper jsonMapper = new ObjectMapper();
            XmlMapper xmlMapper = new XmlMapper();

            var retorno = _adminService.getNoticiasFilaPilha();

            String json = jsonMapper.writeValueAsString(retorno);

            String xml = xmlMapper.writeValueAsString(jsonMapper.readValue(json, Object.class));

            return xml;
    }
}
