package com.example.messaging;

import com.example.rest.request.TrainingSecondaryRequest;
import com.example.service.TrainerWorkloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @Autowired
    private TrainerWorkloadService service;

    @JmsListener(destination = "workload.queue")
    public void receiveWorkload(TrainingSecondaryRequest request) {
        service.actionTraining(request);
    }
}
