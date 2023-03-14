import dto.HitDto;
import model.Hit;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class HitConverter {
    ModelMapper mapper;
    Converter<String, LocalDateTime> stringToDate = (timestamp) -> {
        var format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(timestamp.getSource(), format);
    };

    public HitConverter() {
        mapper = new ModelMapper();
        mapper.createTypeMap(String.class, LocalDateTime.class)
                .setConverter(stringToDate);
    }

    public Hit fromDto(HitDto dto) {
        return mapper.map(dto, Hit.class);
    }
}
