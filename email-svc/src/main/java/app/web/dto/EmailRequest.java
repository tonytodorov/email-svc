package app.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String email;

    @NotBlank
    private String body;
}
