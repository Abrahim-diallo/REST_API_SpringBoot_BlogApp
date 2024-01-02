package net.sencodester.springboot.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {
    @NotBlank(message = "Le nom d'utilisateur ou l'email ne peut pas être vide")
    private String usernameOrEmail;

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    private String password;
}
