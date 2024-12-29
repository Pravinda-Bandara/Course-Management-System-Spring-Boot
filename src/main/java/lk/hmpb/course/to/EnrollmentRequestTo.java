package lk.hmpb.course.to;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentRequestTo {

    @NotNull(message = "Student ID is mandatory")
    private Long studentId;

    @NotNull(message = "Course ID is mandatory")
    private Long courseId;

}