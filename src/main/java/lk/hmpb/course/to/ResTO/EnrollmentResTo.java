package lk.hmpb.course.to.ResTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResTo {
    private Long enrollmentId;
    private Long studentId;
    private Long courseId;
    private String courseName;
    private String studentEmail;
}