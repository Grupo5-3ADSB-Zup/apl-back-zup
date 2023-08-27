package school.sptech.zup.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class DateUtil {
    public  String formDate(Date data){
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(data);
    }
    public  String formLocalDate(LocalDateTime data){
        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(data);
    }
}
