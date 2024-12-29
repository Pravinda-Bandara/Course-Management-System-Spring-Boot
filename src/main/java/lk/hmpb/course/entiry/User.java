package lk.hmpb.course.entiry;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Name is mandatory")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Password is mandatory")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Number is mandatory")
    @Column(nullable = false)
    private String number;

    @NotNull(message = "Role is mandatory")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.student;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Enrollment> enrollments = new HashSet<>();

    public enum Role {
        student,
        admin
    }
}