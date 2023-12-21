package com.example.workload.rest;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class TrainingRequest {
    @NotNull
    private String trainerUsername;

    private String trainerFirstname;
    private String trainerLastname;
    private boolean active;

    @NotNull
    private Date trainingDate;
    @NotNull
    private int trainingDuration;
    @NotNull
    private ActionType actionType;

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

    public Date getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(Date trainingDate) {
        this.trainingDate = trainingDate;
    }

    public int getTrainingDuration() {
        return trainingDuration;
    }

    public void setTrainingDuration(int trainingDuration) {
        this.trainingDuration = trainingDuration;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }
}
