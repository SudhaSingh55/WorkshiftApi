package org.workshift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.workshift.model.Workshift;

import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Workshift, String> {
    @Query("select workshift FROM Workshift workshift where workshift.userId = :userId")
    List<Workshift> findByUserId(@Param("userId") String userId);
}
