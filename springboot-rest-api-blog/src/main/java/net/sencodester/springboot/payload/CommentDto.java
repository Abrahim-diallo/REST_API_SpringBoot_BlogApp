package net.sencodester.springboot.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private long id;

    // Validations pour la ressource
    // Le nom ne doit pas être nul ou vide
    // Le nom doit avoir au moins 2 caractères
    @NotEmpty(message = "Le nom ne doit pas être nul ou vide")
    @Size(min = 2, message = "Le nom doit contenir au moins 2 caractères")
    private String name;

    // L'email ne doit pas être nul ou vide
    // Validation du champ email
    @NotEmpty(message = "L'email ne doit pas être nul ou vide")
    @Email(message = "L'email doit être une adresse email valide")
    private String email;

    // Le corps du commentaire ne doit pas être nul ou vide
    // Le corps du commentaire doit avoir au moins 10 caractères
    @NotEmpty(message = "Le corps du commentaire ne doit pas être nul ou vide")
    @Size(min = 10, message = "Le corps du commentaire doit contenir au moins 10 caractères")
    private String body;

}
