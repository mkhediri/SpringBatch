package com.batchtraining.mk.springbatch.service;

import com.batchtraining.mk.springbatch.model.StudentCsv;
import com.batchtraining.mk.springbatch.model.StudentResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class StudentService {

    private final RestClient restClient;

    private Queue<StudentResponse> queue;

    public StudentService(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<StudentResponse> restCallToGetStudents() {
            return restClient
                    .get()
                    .uri("http://localhost:8081/api/v1/students")
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<StudentResponse>>() {});
    }

    public StudentResponse getStudent() {
        if (queue == null) {
            queue = new LinkedList<>(restCallToGetStudents());
        }
        return queue.poll();
    }

    public StudentResponse restCallToCreateStudent(StudentCsv studentCsv) {
        return restClient
                .post()
                .uri("http://localhost:8081/api/v1/createStudent")
                .body(studentCsv)
                .retrieve()
                .body(StudentResponse.class);
    }
}
