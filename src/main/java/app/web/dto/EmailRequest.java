package app.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmailRequest {

    @NotNull
    private String title;

    @NotNull
    private String email;

    @NotNull
    private String message;
}
