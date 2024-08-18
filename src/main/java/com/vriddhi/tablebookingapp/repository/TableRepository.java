package com.vriddhi.tablebookingapp.repository;

import com.vriddhi.tablebookingapp.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<Table, Long> {
}
