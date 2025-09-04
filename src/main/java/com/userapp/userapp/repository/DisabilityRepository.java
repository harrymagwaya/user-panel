package com.userapp.userapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userapp.userapp.model.Disability;
import com.userapp.userapp.model.User;

@Repository
public interface DisabilityRepository extends JpaRepository<Disability, Long> {
    List<Disability> findByUser(User user);

    void saveAll(List<Disability> entity);
}
