package app.web;

import app.model.Email;
import app.service.EmailService;
import app.web.dto.EmailRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmailController.class)
public class EmailControllerApiTest {

    @MockitoBean
    private EmailService emailService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRequestToUserEmails_shouldReturnUserEmail() throws Exception {

        String userEmail = "test@abv.bg";

        Email email = Email.builder()
                .title("Test title")
                .email(userEmail)
                .message("Test message")
                .sentAt(LocalDateTime.now())
                .build();

        when(emailService.getUserEmails(userEmail)).thenReturn(List.of(email));

        MockHttpServletRequestBuilder request = get("/api/v1/email/{email}/all-emails", userEmail);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").isNotEmpty())
                .andExpect(jsonPath("$[0].email").isNotEmpty())
                .andExpect(jsonPath("$[0].message").isNotEmpty())
                .andExpect(jsonPath("$[0].sentAt").isNotEmpty());
    }

    @Test
    void postWithBodyToSendEmail_returns201AndCorrectDtoStructure() throws Exception {

        String title = "Test title";
        String email = "test@abv.bg";
        String message = "Test message";

        EmailRequest emailRequest = EmailRequest.builder()
                .title(title)
                .email(email)
                .message(message)
                .build();

        Email emailResponse = Email.builder()
                .title(title)
                .email(email)
                .message(message)
                .sentAt(LocalDateTime.now())
                .build();

        when(emailService.sendEmail(emailRequest)).thenReturn(emailResponse);

        MockHttpServletRequestBuilder request = post("/api/v1/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(emailRequest));

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("title").isNotEmpty())
                .andExpect(jsonPath("email").isNotEmpty())
                .andExpect(jsonPath("message").isNotEmpty())
                .andExpect(jsonPath("sentAt").isNotEmpty());
    }

}