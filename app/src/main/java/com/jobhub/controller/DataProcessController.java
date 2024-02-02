package com.jobhub.controller;

import com.jobhub.controller.dto.RecruitmentRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DataProcessController {

    @PostMapping("/processData")
    public void processData(@RequestBody List<RecruitmentRequest> data) {
        System.out.println("Processing python data...");

        for(RecruitmentRequest request : data) {
            System.out.println(request.toString());
        }
    }
}
