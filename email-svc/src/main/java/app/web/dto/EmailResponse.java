package app.web.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EmailResponse {

    private String title;

    private String email;

    private String body;

    private LocalDateTime sentAt;
}
