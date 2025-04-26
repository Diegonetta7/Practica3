package es.uv.prnr.p3.spring.p3_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import es.uv.prnr.p3.spring.p3_spring.model.Project;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    // Punto 1
    List<Project> findByArea(String area);

    // Punto 2
    List<Project> findTop3ByOrderByBudgetDesc();

    // Punto 6
    @Query("SELECT SUM(p.budget) FROM Project p WHERE p.area = :area")
    BigDecimal findTotalBudgetByArea(@Param("area") String area);

    // Punto 7
    Project findFirstByOrderByBudgetAsc();

    // Punto 8
    List<Project> findByEndDateGreaterThanEqual(LocalDate currentDate);
}
