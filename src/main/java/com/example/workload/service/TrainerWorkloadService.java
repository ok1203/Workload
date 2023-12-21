package com.example.workload.service;

import com.example.workload.model.TrainerMonthlySummary;
import com.example.workload.rest.ActionType;
import com.example.workload.rest.TrainingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TrainerWorkloadService {

    private static final Logger log = LoggerFactory.getLogger(TrainerWorkloadService.class);
    private final Map<String, TrainerMonthlySummary> trainerSummaries = new HashMap<>();

    public void actionTraining(TrainingRequest request) {
        if (request.getActionType().equals(ActionType.ADD)) {
            create(request);
        } else if (request.getActionType().equals(ActionType.DELETE)) {
            delete(request);
        }
    }

    public void create(TrainingRequest request) {
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

    public void delete(TrainingRequest request) {
        TrainerMonthlySummary trainerMonthlySummary =  trainerSummaries.get(request.getTrainerUsername());
        List<List<Integer>> list = trainerMonthlySummary.getTrainingSummaryDuration();
        int currentDuration = list.get(request.getTrainingDate().getYear() - 120).get(request.getTrainingDate().getMonth());
        currentDuration -= request.getTrainingDuration();
        list.get(request.getTrainingDate().getYear() - 120).set(request.getTrainingDate().getMonth(), currentDuration);
        trainerMonthlySummary.setTrainingSummaryDuration(list);
        trainerSummaries.put(request.getTrainerUsername(), trainerMonthlySummary);
        log.info(trainerSummaries.toString());
    }
}
