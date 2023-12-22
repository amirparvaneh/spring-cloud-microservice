package com.cloud.deposit.repository;

import com.cloud.deposit.model.Deposit;
import com.cloud.deposit.model.DepositType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {

    Optional<Deposit> findDepositByDepositNumber(Integer depositNumber);

    List<Deposit> findAllByNationalCode(String nationalCode);

    void deleteByDepositNumber(Integer depositNumber);

    List<Deposit> findDepositsByDepositType(DepositType depositType);

}
