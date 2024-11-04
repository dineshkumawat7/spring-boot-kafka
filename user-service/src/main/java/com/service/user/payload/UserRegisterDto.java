package com.service.user.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterDto {
    @NotBlank(message = "first name is mandatory")
    private String firstName;
    private String lastName;
    @NotBlank(message = "email address is mandatory")
    @Email(message = "invalid email address")
    private String email;
    @NotBlank(message = "phone number is mandatory")
    @Size(min = 10, max = 10, message = "invalid phone number")
    private String phone;
}
