package mate.academy.bookstore.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import mate.academy.bookstore.validation.FieldMatch;

@FieldMatch(field = "password",
        fieldMatch = "repeatPassword",
        message = "Password fields must match")
@Data
public class UserRegistrationRequestDto {
    @NotBlank
    @Size(min = 8, max = 50)
    private String email;

    @NotBlank
    @Size(min = 8, max = 100)
    private String password;

    @NotBlank
    @Size(min = 8, max = 100)
    private String repeatPassword;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String shippingAddress;
}
