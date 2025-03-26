package app;

import app.model.Email;
import app.repository.EmailRepository;
import app.service.EmailService;
import app.web.dto.EmailRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class SendEmailITest {

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailRepository emailRepository;

    @Test
    void sendEmail_shouldSendEmailSuccessfully() {

        EmailRequest emailRequest = EmailRequest.builder()
                .title("Test title")
                .email("test@abv.bg")
                .message("Test message")
                .build();

        emailService.sendEmail(emailRequest);

        List<Email> emails = emailRepository.findAll();
        assertThat(emails).hasSize(1);

        Email savedEmail = emails.get(0);

        assertEquals("Test title", savedEmail.getTitle());
        assertEquals("test@abv.bg", savedEmail.getEmail());
        assertEquals("Test message", savedEmail.getMessage());
    }
}
