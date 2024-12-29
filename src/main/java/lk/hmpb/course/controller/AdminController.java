package lk.hmpb.course.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    @GetMapping("/data")
    public String getAdminData() {
        return "Admin-only data";
    }
}