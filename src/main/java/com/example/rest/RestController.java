package com.example.rest;

import com.example.rest.request.TrainingSecondaryRequest;
import com.example.service.TrainerWorkloadService;
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
    public ResponseEntity<Void> addTraining(@RequestBody TrainingSecondaryRequest request) {
        trainerWorkloadService.actionTraining(request);
        return ResponseEntity.ok().build();
    }
}
