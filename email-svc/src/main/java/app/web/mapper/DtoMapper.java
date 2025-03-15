package app.web.mapper;

import app.model.Email;
import app.web.dto.EmailResponse;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class DtoMapper {

    public static EmailResponse fromEmail(Email entity) {

        return EmailResponse.builder()
                .title(entity.getTitle())
                .email(entity.getEmail())
                .body(entity.getBody())
                .sentAt(entity.getSentAt())
                .build();
    }
}
