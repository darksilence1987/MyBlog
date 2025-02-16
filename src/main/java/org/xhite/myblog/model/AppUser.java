package org.xhite.myblog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be empty")
    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,20}$",
            message = "Username must be 3-20 characters long and can only contain letters, numbers, underscores, and hyphens")
    private String username;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters long and contain at least one digit, " +
                    "one lowercase letter, one uppercase letter, and one special character")
    private String password;
}
