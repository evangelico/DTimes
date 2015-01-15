package it.fge.dtimes.business.plain;

import java.util.List;

import it.fge.dtimes.model.packageCourses.PackageCoursesDTO;

import org.springframework.data.domain.Page;

public interface PlainBusiness {

    Page<PackageCoursesDTO> findAll(int page, int pageSize) throws Exception;

    PackageCoursesDTO findById(long id) throws Exception;

    void save(PackageCoursesDTO plain) throws Exception;

    void update(PackageCoursesDTO plainToUpdate) throws Exception;
    
    void delete(long plainIDToDelete) throws Exception;

    Page<PackageCoursesDTO> findAllByName(String nameFilter, int page, int size) throws Exception;

    List<PackageCoursesDTO> findAllToSignleLesson() throws Exception;

}
