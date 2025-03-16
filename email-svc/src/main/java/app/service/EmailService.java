package app.service;

import app.model.Email;
import app.repository.EmailRepository;
import app.web.dto.EmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class EmailService {

    private final MailSender mailSender;
    private final EmailRepository emailRepository;

    @Autowired
    public EmailService(MailSender mailSender, EmailRepository emailRepository) {
        this.mailSender = mailSender;
        this.emailRepository = emailRepository;
    }

    public Email sendEmail(EmailRequest emailRequest) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("tonytodorov11@gmail.com");
        message.setReplyTo(emailRequest.getEmail());
        message.setSubject(emailRequest.getTitle());
        message.setText(emailRequest.getMessage());

        Email email = Email.builder()
                .title(emailRequest.getTitle())
                .email(emailRequest.getEmail())
                .message(emailRequest.getMessage())
                .sentAt(LocalDateTime.now())
                .build();

        try {
            mailSender.send(message);
        } catch (Exception e) {
            log.warn("There was an issue sending an email to %s due to %s.".formatted(email.getEmail(), e.getMessage()));
        }

        return emailRepository.save(email);
    }

    public List<Email> getUserEmails(String email) {
        return emailRepository.findAllByEmail(email);
    }
}
