package it.fge.dtimes.model.packageCourses;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PackageCoursesDAO extends JpaRepository<PackageCoursesDTO, Long> {

    @Query(value = "SELECT pc FROM PackageCoursesDTO pc WHERE pc.name LIKE %:nominative% OR pc.description LIKE %:nominative%", countQuery = "SELECT COUNT(pc.id) FROM PackageCoursesDTO pc WHERE pc.name LIKE %:nominative% OR pc.description LIKE %:nominative%")
    Page<PackageCoursesDTO> findAllLikeName(@Param("nominative") String nameFilter, Pageable paramPageable);
   
    @Query(value = "SELECT pc FROM PackageCoursesDTO pc WHERE pc.singleLesson = 'T'")
    List<PackageCoursesDTO> findAllToSignleLesson();

}
