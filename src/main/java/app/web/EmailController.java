package app.web;


import app.model.Email;
import app.service.EmailService;
import app.web.mapper.DtoMapper;
import app.web.dto.EmailRequest;
import app.web.dto.EmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/{email}/all-emails")
    public ResponseEntity<List<EmailResponse>> getUserEmails(@PathVariable String email) {

        List<Email> userEmails = emailService.getUserEmails(email);

        List<EmailResponse> list = userEmails
                .stream()
                .map(DtoMapper::fromEmail)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(list);
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
