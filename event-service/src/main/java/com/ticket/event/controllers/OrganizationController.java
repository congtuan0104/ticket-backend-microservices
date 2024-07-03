package com.ticket.event.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.event.entities.Organization;
import com.ticket.event.repositories.OrganizationRepository;

@RestController
@RequestMapping("/api")

public class OrganizationController {
    @Autowired
    private OrganizationRepository organizationRepository;

    @GetMapping(value = "/organization")
    public ResponseEntity<List<Organization>> getAllOrganization()
    {
        try {
            List<Organization> organizations = new ArrayList<Organization>();
            organizations = organizationRepository.findAll();

            return new ResponseEntity<>(organizations, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/organization/{id}")
    public ResponseEntity<Organization> getOrganizationByOrganizationId(
        @PathVariable("id") String organizationId
    )
    {
        Optional<Organization>  organizationData = organizationRepository.findById(organizationId);
        if (organizationData.isPresent())
        {
            return new ResponseEntity<>(organizationData.get(), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/organization/userId")
    public ResponseEntity<List<Organization>> getOrganizationByUserId(
        @RequestParam(name ="id") String userId
    )
    {
        try {
            List<Organization>  organizationData = organizationRepository.findByUserId(userId);
            return new ResponseEntity<>(organizationData, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/organization")
    public ResponseEntity<Organization> createOrganization(
        @RequestBody Organization organization
    )
    {
        try {
            Organization _organization = organizationRepository.save(organization);
            return new ResponseEntity<>(_organization, HttpStatus.CREATED);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping(value = "/organization")
    public ResponseEntity<Organization> updateOrganizationById(
        @RequestBody Organization inputOrganization
    )
    {
        Optional<Organization> organzationData = organizationRepository.findById(inputOrganization.getOrganizationId().toString());
        if (organzationData.isPresent())
        {
            Organization _organization = organzationData.get();
            _organization.setUserId(inputOrganization.getUserId());
            _organization.setDescription(inputOrganization.getDescription());
            _organization.setImgLogo_src(inputOrganization.getImgLogo_src());

            return new ResponseEntity<>(organizationRepository.save(_organization), HttpStatus.OK);
        }
        else 
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);


        }
    }


    @DeleteMapping(value = "/organization")
    public ResponseEntity<HttpStatus> deleteOrganizationByOrganizatonId(
        @RequestParam(name = "organizationId") String organizationId
    )
    {
        try {
            organizationRepository.deleteById(organizationId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
