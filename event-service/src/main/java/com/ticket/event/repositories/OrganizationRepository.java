package com.ticket.event.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ticket.event.entities.Organization;

@Repository
public interface OrganizationRepository extends MongoRepository<Organization, String> {
List<Organization> findByUserId(String userId);
}
