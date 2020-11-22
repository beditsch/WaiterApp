package com.beditsch.project.repository;

import com.beditsch.project.model.Restaurant;
import com.beditsch.project.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<Table, Integer> {

}