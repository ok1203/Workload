package com.example.service;

import com.example.model.TrainerMonthlySummary;
import com.example.rest.ActionType;
import com.example.rest.request.TrainingSecondaryRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrainerWorkloadService {

    private static final Logger log = LoggerFactory.getLogger(TrainerWorkloadService.class);
    private final Map<String, TrainerMonthlySummary> trainerSummaries = new HashMap<>();
    @Autowired
    private JmsTemplate jmsTemplate;

    public void actionTraining(TrainingSecondaryRequest request) {
        if (request.getActionType().equals(ActionType.ADD)) {
            create(request);
            log.info("Message received, ADD request");
        } else if (request.getActionType().equals(ActionType.DELETE)) {
            delete(request);
            log.info("Message received, DELETE request");
        }
    }

    private void create(TrainingSecondaryRequest request) {
        TrainerMonthlySummary trainerMonthlySummary =  trainerSummaries.getOrDefault(request.getTrainerUsername(), new TrainerMonthlySummary());
        trainerMonthlySummary.setTrainerUsername(request.getTrainerUsername());
        trainerMonthlySummary.setTrainerFirstname(request.getTrainerFirstname());
        trainerMonthlySummary.setTrainerLastname(request.getTrainerLastname());
        trainerMonthlySummary.setActive(request.isActive());
        List<List<Integer>> list = trainerMonthlySummary.getTrainingSummaryDuration();
        int currentDuration = list.get(request.getTrainingDate().getYear()-120).get(request.getTrainingDate().getMonth());
        currentDuration += request.getTrainingDuration();
        list.get(request.getTrainingDate().getYear()-120).set(request.getTrainingDate().getMonth(), currentDuration);
        trainerMonthlySummary.setTrainingSummaryDuration(list);
        trainerSummaries.put(request.getTrainerUsername(), trainerMonthlySummary);
        log.info(trainerSummaries.toString());
    }

    private void delete(TrainingSecondaryRequest request) {
        TrainerMonthlySummary trainerMonthlySummary =  trainerSummaries.get(request.getTrainerUsername());
        if (trainerMonthlySummary == null) {
            log.info("Not found summary for username: " + request.getTrainerUsername());

            jmsTemplate.send("ActiveMQ.DLQ", session -> session.createMessage());
        } else {
            List<List<Integer>> list = trainerMonthlySummary.getTrainingSummaryDuration();
            int currentDuration = list.get(request.getTrainingDate().getYear() - 120).get(request.getTrainingDate().getMonth());
            currentDuration -= request.getTrainingDuration();
            list.get(request.getTrainingDate().getYear() - 120).set(request.getTrainingDate().getMonth(), currentDuration);
            trainerMonthlySummary.setTrainingSummaryDuration(list);
            trainerSummaries.put(request.getTrainerUsername(), trainerMonthlySummary);
            log.info(trainerSummaries.toString());
        }
    }
}
