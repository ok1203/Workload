package com.example.cucumber;

import com.example.rest.ActionType;
import com.example.rest.request.TrainingSecondaryRequest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;

public class CucumberSteps {

    @LocalServerPort
    private String port;

    private ResponseEntity<String> response;

    private TrainingSecondaryRequest request = new TrainingSecondaryRequest();

    private RestClient client = RestClient.create();

    private String token = client.get()
            .uri("http://localhost:8080/api/login?username=Jeremy.Dell5&password=WwnrlcSUAr")
            .retrieve()
            .toEntity(String.class)
            .getBody();

    @When("called endpoint with {string} action")
    public void whenGiven(String action) {
        ActionType type = (action.equals("ADD")) ? ActionType.ADD : ActionType.DELETE;
        request.setTrainerUsername("Test.Test");
        request.setActive(true);
        request.setTrainerFirstname("Test");
        request.setTrainerLastname("Test");
        request.setTrainingDate(new Date());
        request.setTrainingDuration(60);
        request.setActionType(type);
        response = client.post()
                .uri("http://localhost:" + port + "/trainer/workload")
                .header("Authorization", "Bearer " + token)
                .body(request)
                .retrieve()
                .toEntity(String.class);
    }

    @When("endpoint called with invalid request")
    public void whenInvalid() {
        request = new TrainingSecondaryRequest();
        try {
            response = client.post()
                    .uri("http://localhost:" + port + "/trainer/workload")
                    .header("Authorization", "Bearer " + token)
                    .body(request)
                    .retrieve()
                    .toEntity(String.class);
        } catch (Exception e) {
            response = ResponseEntity.status(500).build();
        }
    }

    @When("endpoint without token")
    public void endpointNoToken() {
        try {
        ActionType type = ActionType.ADD;
        request.setTrainerUsername("Test.Test");
        request.setActive(true);
        request.setTrainerFirstname("Test");
        request.setTrainerLastname("Test");
        request.setTrainingDate(new Date());
        request.setTrainingDuration(60);
        request.setActionType(type);
        response = client.post()
                .uri("http://localhost:" + port + "/trainer/workload")
                .body(request)
                .retrieve()
                .toEntity(String.class);

        } catch (HttpClientErrorException.Forbidden e) {
            response = ResponseEntity.status(403).build();
        }
    }

    @Then("returned status code is {int}")
    public void thenStatusCode(int code) {
        assertThat("status code is " + code, response.getStatusCode().value() == code);
    }

}
