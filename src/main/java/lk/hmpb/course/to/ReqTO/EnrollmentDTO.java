package lk.hmpb.course.to.ReqTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDTO {

    private Long id;
    private Long userId;
    private String userName;
    private Long courseId;
    private String courseTitle;
}