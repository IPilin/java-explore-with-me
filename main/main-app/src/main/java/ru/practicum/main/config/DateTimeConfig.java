package ru.practicum.main.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.main.model.constant.AppConstants;

@Configuration
public class DateTimeConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.deserializers(new LocalDateTimeDeserializer(AppConstants.FORMATTER));
            builder.serializers(new LocalDateTimeSerializer(AppConstants.FORMATTER));
        };
    }
}
