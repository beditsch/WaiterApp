package com.beditsch.project.service;

import com.beditsch.project.exception.RestaurantNotFoundException;
import com.beditsch.project.exception.TableNotFoundException;
import com.beditsch.project.model.Restaurant;
import com.beditsch.project.model.Table;
import com.beditsch.project.repository.RestaurantRepository;
import com.beditsch.project.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TableService {

    @Autowired
    private TableRepository tableRepository;

    public Table getTableById(Integer tableId){
        Optional<Table> table = tableRepository.findById(tableId);
        if (table.isPresent())
            return table.get();
        else
            throw new TableNotFoundException();
    }

    public Table createTable(Table table) {
        return tableRepository.save(table);
    }


}

