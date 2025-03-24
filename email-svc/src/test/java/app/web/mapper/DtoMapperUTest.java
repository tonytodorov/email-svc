package app.web.mapper;

import app.model.Email;
import app.web.dto.EmailResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DtoMapperUTest {

    @Test
    void givenEmailEntity_whenMappingToEmailResponse_thenCorrectMapping() {

        Email email = Email.builder()
                .title("Test Title")
                .email("test@example.com")
                .message("Test message")
                .sentAt(LocalDateTime.now())
                .build();

        EmailResponse emailResponse = DtoMapper.fromEmail(email);

        assertEquals(email.getTitle(), emailResponse.getTitle());
        assertEquals(email.getEmail(), emailResponse.getEmail());
        assertEquals(email.getMessage(), emailResponse.getMessage());
        assertEquals(email.getSentAt(), emailResponse.getSentAt());
    }
}
