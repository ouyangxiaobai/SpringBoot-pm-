package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.CommonProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommonProblemDao extends JpaRepository<CommonProblem, Long> {

    @Query("select p from CommonProblem p where p.id = :id")
    CommonProblem find(@Param("id")Long id);


    CommonProblem findByName(String name);

    List<CommonProblem> findTop8ByOrderByIdDesc();
}
