package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "trainer_summary")
public class TrainerMonthlySummary {

    @Id
    private String id;

    @Indexed(unique = true)
    private String trainerUsername;
    @Indexed
    private String trainerFirstname;
    @Indexed
    private String trainerLastname;
    private boolean active;
    private List<List<Integer>> trainingSummaryDuration;

    public TrainerMonthlySummary() {
        trainingSummaryDuration = new ArrayList<>();

        int yearsToInitialize = 10; // for example, initializing for 10 years

        for (int i = 0; i <= yearsToInitialize; i++) {
            List<Integer> monthsList = new ArrayList<>(12); // Initialize an ArrayList for each year
            for (int j = 0; j < 12; j++) {
                monthsList.add(0); // Initializing with 0 for each month (you can use any default value)
            }
            trainingSummaryDuration.add(monthsList); // Add the 12-month list for each year
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainerUsername() {
        return trainerUsername;
    }

    public void setTrainerUsername(String trainerUsername) {
        this.trainerUsername = trainerUsername;
    }

    public String getTrainerFirstname() {
        return trainerFirstname;
    }

    public void setTrainerFirstname(String trainerFirstname) {
        this.trainerFirstname = trainerFirstname;
    }

    public String getTrainerLastname() {
        return trainerLastname;
    }

    public void setTrainerLastname(String trainerLastname) {
        this.trainerLastname = trainerLastname;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<List<Integer>> getTrainingSummaryDuration() {
        return trainingSummaryDuration;
    }

    public void setTrainingSummaryDuration(List<List<Integer>> trainingSummaryDuration) {
        this.trainingSummaryDuration = trainingSummaryDuration;
    }

    @Override
    public String toString() {
        return "TrainerMonthlySummary{" +
                "trainerUsername='" + trainerUsername + '\'' +
                ", trainerFirstname='" + trainerFirstname + '\'' +
                ", trainerLastname='" + trainerLastname + '\'' +
                ", active=" + active +
                ", trainingSummaryDuration=" + trainingSummaryDuration +
                '}';
    }
}
