package com.example.workload.rest;

import com.example.workload.service.TrainerWorkloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/trainer/workload")
public class RestController {

    @Autowired
    private TrainerWorkloadService trainerWorkloadService;

    @PostMapping
    public ResponseEntity<Void> addTraining(@RequestBody TrainingRequest request) {
        trainerWorkloadService.actionTraining(request);
        return ResponseEntity.ok().build();
    }
}
