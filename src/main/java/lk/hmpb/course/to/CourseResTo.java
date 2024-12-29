package lk.hmpb.course.to;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class CourseResTo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is mandatory")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Description is mandatory")
    @Column(nullable = false)
    private String description;

    @NotBlank(message = "Duration is mandatory")
    @Column(nullable = false)
    private String duration;

    @NotBlank(message = "Instructor is mandatory")
    @Column(nullable = false)
    private String instructor;

    @NotBlank(message = "Instructor number is mandatory")
    @Column(nullable = false)
    private String instructorNum;
}