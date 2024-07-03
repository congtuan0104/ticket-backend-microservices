package com.ticket.user.services;

import com.ticket.user.entities.CreateOrganizationRequest;
import com.ticket.user.entities.User;
import com.ticket.user.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository repository;
    private final RestTemplate restTemplate;

    @Autowired
    public UserService(UserRepository repository,
                       RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }


    public User save(User user) {
        var newUser = this.repository.save(user);
        if(newUser.getRole().equals("organize")){
            HttpEntity<CreateOrganizationRequest> createOrganizationRequest = new HttpEntity<CreateOrganizationRequest>(
                    new CreateOrganizationRequest(newUser.getId().toHexString(), ""));

            restTemplate.postForObject("http://organization-service/api/organization", createOrganizationRequest, Object.class);
        }
        return newUser;
    }

    public User update(User user) {
        var newUser = this.repository.save(user);
        if(newUser.getRole().equals("organize")){
            HttpEntity<CreateOrganizationRequest> createOrganizationRequest = new HttpEntity<CreateOrganizationRequest>(
                    new CreateOrganizationRequest(newUser.getId().toHexString(), ""));

            restTemplate.postForObject("http://organization-service/api/organization", createOrganizationRequest, Object.class);
        }
        return newUser;
    }

    public User getById(ObjectId id) {
        return this.repository.findById(id).orElse(null);
    }

    public Optional<User> findUserByEmail(String email) {
        return this.repository.findByEmail(email);
    }

//    public ResponseTemplateVO getUserWithDepartment(String id) {
//        User user = this.getById(new ObjectId(id));
//
//        Department department = restTemplate.getForObject("http://service01/service01/" + user.getDepartmentId(), Department.class);
//
//        return new ResponseTemplateVO(user, department);
//    }
}
