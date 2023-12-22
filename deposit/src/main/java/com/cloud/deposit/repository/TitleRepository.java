package com.cloud.deposit.repository;

import com.cloud.deposit.model.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title,Long> {
}
