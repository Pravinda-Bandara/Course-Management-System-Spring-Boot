package lk.hmpb.course.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseEnrollmentStatusResTo {
    private String _id;
    private String title;
    private String description;
    private String duration;
    private String instructor;
    private String instructorNum;
    private boolean enrolled;

}
