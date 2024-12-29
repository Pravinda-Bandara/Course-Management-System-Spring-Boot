package lk.hmpb.course.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentStatusResponse {
    private boolean enrolled;
    private String enrollmentId;

}
