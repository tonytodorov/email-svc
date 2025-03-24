package app.service;

import app.model.Email;
import app.repository.EmailRepository;
import app.web.dto.EmailRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceUTest {

    @Mock
    private MailSender mailSender;

    @Mock
    private EmailRepository emailRepository;

    @InjectMocks
    private EmailService emailService;

    @Test
    void givenEmailRequest_whenSendEmail_thenCorrectEmailIsSentAndSaved() {

        EmailRequest emailRequest = EmailRequest.builder()
                .title("Test title")
                .email("test@test.com")
                .message("Test message")
                .build();

        Email email = Email.builder()
                .title(emailRequest.getTitle())
                .email(emailRequest.getEmail())
                .message(emailRequest.getMessage())
                .sentAt(LocalDateTime.now())
                .build();

        when(emailRepository.save(any(Email.class))).thenReturn(email);
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        Email result = emailService.sendEmail(emailRequest);

        assertEquals(email.getTitle(), result.getTitle());
        assertEquals(email.getEmail(), result.getEmail());
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
        verify(emailRepository, times(1)).save(any(Email.class));
    }

    @Test
    void givenEmailRequest_whenSendEmailFails_thenThrownException() {

        EmailRequest emailRequest = EmailRequest.builder()
                .title("Test title")
                .email("test@test.com")
                .message("Test message")
                .build();

        Email email = Email.builder()
                .title(emailRequest.getTitle())
                .email(emailRequest.getEmail())
                .message(emailRequest.getMessage())
                .sentAt(LocalDateTime.now())
                .build();

        when(emailRepository.save(any(Email.class))).thenReturn(email);
        doThrow(new RuntimeException("server error")).when(mailSender).send(any(SimpleMailMessage.class));

        Email result = emailService.sendEmail(emailRequest);

        assertEquals(email.getTitle(), result.getTitle());
        assertEquals(email.getEmail(), result.getEmail());
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
        verify(emailRepository, times(1)).save(any(Email.class));
    }

    @Test
    void givenEmailAddress_whenGetUserEmails_thenReturnListOfEmails() {

        String emailAddress = "test@abv.com";

        Email email = Email.builder()
                .title("Test")
                .email(emailAddress)
                .message("Test message")
                .build();

        List<Email> expectedEmails = List.of(email);

        when(emailRepository.findAllByEmail(emailAddress)).thenReturn(expectedEmails);

        List<Email> result = emailService.getUserEmails(emailAddress);

        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getTitle());
        verify(emailRepository, times(1)).findAllByEmail(emailAddress);
    }

    @Test
    void givenEmailAddress_whenGetUserEmails_thenReturnEmptyListIfNoEmailsFound() {

        String emailAddress = "test@abv.com";

        when(emailRepository.findAllByEmail(emailAddress)).thenReturn(List.of());

        List<Email> result = emailService.getUserEmails(emailAddress);

        verify(emailRepository, times(1)).findAllByEmail(emailAddress);
        assertTrue(result.isEmpty());
    }
}
