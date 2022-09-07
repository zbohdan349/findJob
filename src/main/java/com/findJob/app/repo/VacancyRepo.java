package com.findJob.app.repo;

import com.findJob.app.model.Category;
import com.findJob.app.model.Company;
import com.findJob.app.model.Level;
import com.findJob.app.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface VacancyRepo extends JpaRepository<Vacancy,Integer> {
    @Query(value = "SELECT v FROM Vacancy v WHERE v.Salary >=:salary and v.level IN :levels")
    public List<Vacancy> getByFilter(
            @Param("salary")Integer salary,
            @Param("levels")List<Level> levels
    );
    public List<Vacancy> getByCompany(Company company);
}
