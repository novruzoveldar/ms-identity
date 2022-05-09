package com.guavapay.repository;

import com.guavapay.model.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {

    @Query("FROM Courier c where c.account.id = :accountId")
    Optional<Courier> findByAccountId(@Param("accountId") Long accountId);
}
