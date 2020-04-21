package com.learn.location.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.location.entities.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {

}
