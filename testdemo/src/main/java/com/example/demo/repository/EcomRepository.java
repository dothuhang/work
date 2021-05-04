package com.example.demo.repository;

import com.example.demo.tactmodel.Ecom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface EcomRepository extends JpaRepository<Ecom, Integer> {

    @Query("SELECT e FROM Ecom e JOIN User u ON e.userid = u.userid WHERE u.username = ?1")
    List<Ecom> findByUsername(String username);

    @Query("SELECT e FROM Ecom e WHERE e.id =?1")
    Optional<Ecom> findByProjectId(long id);

    @Query(value = "call post_upload_csv(:id);", nativeQuery = true)
    void postUploadCsv(@Param("id") long id);



}
