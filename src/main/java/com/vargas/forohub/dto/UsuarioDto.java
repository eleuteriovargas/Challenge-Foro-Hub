package com.vargas.forohub.dto;



import com.vargas.forohub.domain.usuario.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para datos de usuario")
public class UsuarioDto {

    private Long id;

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez", requiredMode = REQUIRED)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Email del usuario", example = "usuario@ejemplo.com", requiredMode = REQUIRED)
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    private String email;

    @Schema(description = "Contraseña del usuario (mínimo 6 caracteres)", example = "password123", requiredMode = REQUIRED)
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String clave;

    private Usuario.RolUsuario rol;

    public UsuarioDto(Usuario usuario) {
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        this.email = usuario.getEmail();
        this.rol = usuario.getRol();
    }
}
