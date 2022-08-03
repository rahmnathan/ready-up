package com.github.rahmnathan.ready_up.persistence.repository;

import com.github.rahmnathan.ready_up.persistence.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {


}
