package app.web;


import app.model.Email;
import app.service.EmailService;
import app.web.mapper.DtoMapper;
import app.web.dto.EmailRequest;
import app.web.dto.EmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<EmailResponse> sendEmail(@RequestBody EmailRequest emailRequest) {

        Email email = emailService.sendEmail(emailRequest);

        EmailResponse response = DtoMapper.fromEmail(email);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
