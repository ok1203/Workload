package com.example;

import com.example.model.TrainerMonthlySummary;
import com.example.repo.SummaryRepository;
import com.example.rest.ActionType;
import com.example.rest.request.TrainingSecondaryRequest;
import com.example.service.TrainerWorkloadService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TrainerWorkloadServiceTest {

    @Mock
    private SummaryRepository summaryRepository;

    @Mock
    private JmsTemplate jmsTemplate;

    @InjectMocks
    private TrainerWorkloadService trainerWorkloadService;

    @Test
    public void testActionTrainingForAdd() {
        TrainingSecondaryRequest request = createTrainingSecondaryRequest(ActionType.ADD);
        TrainerMonthlySummary summary = createTrainerMonthlySummary();
        trainerWorkloadService.actionTraining(request);

        verify(summaryRepository).findByTrainerUsername("John.Doe");
//        verify(summaryRepository).save(summary);
        verify(summaryRepository).findAll();
    }

    @Test
    public void testActionTrainingForDelete() {
        TrainingSecondaryRequest request = createTrainingSecondaryRequest(ActionType.DELETE);
        TrainerMonthlySummary summary = createTrainerMonthlySummary();
        trainerWorkloadService.actionTraining(request);

        verify(summaryRepository).findByTrainerUsername("John.Doe");
    }

    private TrainingSecondaryRequest createTrainingSecondaryRequest(ActionType actionType) {
        TrainingSecondaryRequest request = new TrainingSecondaryRequest();
        request.setTrainerUsername("John.Doe");
        request.setTrainerFirstname("John");
        request.setTrainerLastname("Doe");
        request.setActive(true);
        request.setTrainingDate(new Date());
        request.setTrainingDuration(60);
        request.setActionType(actionType);

        return request;
    }

    private TrainerMonthlySummary createTrainerMonthlySummary() {
        TrainerMonthlySummary summary = new TrainerMonthlySummary();
        summary.setTrainingSummaryDuration(List.of(List.of(0,0,0,0,0,0,0,0,0,0,0,0)));
        summary.setActive(true);
        summary.setTrainerFirstname("John");
        summary.setTrainerLastname("Doe");
        summary.setTrainerUsername("John.Doe");
        return summary;
    }
}
