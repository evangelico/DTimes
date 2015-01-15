package it.fge.dtimes.business.plain;

import java.util.List;

import it.fge.dtimes.model.packageCourses.PackageCoursesDAO;
import it.fge.dtimes.model.packageCourses.PackageCoursesDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

public class PlainBusinessImpl implements PlainBusiness {

    @Autowired
    private PackageCoursesDAO packageCoursesDAO;

    @Override
    @Transactional
    public Page<PackageCoursesDTO> findAll(int page, int pageSize) throws Exception {
	PageRequest pageRequest = new PageRequest(page, pageSize);
	Page<PackageCoursesDTO> listPlainTmp = packageCoursesDAO.findAll(pageRequest);
	for (PackageCoursesDTO plain : listPlainTmp.getContent()) {
	    if (plain.getPayments() != null) {
		plain.getPayments().size();
	    }

	    if (plain.getSubscriptions() != null) {
		plain.getSubscriptions().size();
	    }
	}

	return listPlainTmp;
    }

    @Override
    @Transactional
    public PackageCoursesDTO findById(long id) throws Exception {
	PackageCoursesDTO plainTmp = packageCoursesDAO.findOne(id);
	if (plainTmp != null) {
	    if (plainTmp.getPayments() != null) {
		plainTmp.getPayments().size();
	    }
	    if (plainTmp.getSubscriptions() != null) {
		plainTmp.getSubscriptions().size();
	    }
	}
	return plainTmp;
    }

    @Override
    @Transactional
    public void save(PackageCoursesDTO plain) throws Exception {
	packageCoursesDAO.saveAndFlush(plain);
    }

    @Override
    @Transactional
    public void update(PackageCoursesDTO plainToUpdate) throws Exception {
	PackageCoursesDTO plainTmp = packageCoursesDAO.findOne(plainToUpdate.getId());
	plainTmp.setAmount(plainToUpdate.getAmount());
	plainTmp.setTeacherPercentage(plainToUpdate.getTeacherPercentage());
	plainTmp.setDescription(plainToUpdate.getDescription());
	plainTmp.setName(plainToUpdate.getName());
	plainTmp.setSubscriptionPlain(plainToUpdate.isSubscriptionPlain());
	plainTmp.setSingleLesson(plainToUpdate.isSingleLesson());
	plainTmp.setExpirationType(plainToUpdate.getExpirationType());
    }

    @Override
    @Transactional
    public void delete(long plainIDToDelete) throws Exception {
	packageCoursesDAO.delete(plainIDToDelete);
    }

    @Override
    @Transactional
    public Page<PackageCoursesDTO> findAllByName(String nameFilter, int page, int size) throws Exception {
	PageRequest pageRequest = new PageRequest(page, size);
	Page<PackageCoursesDTO> listPlainTmp = packageCoursesDAO.findAllLikeName(nameFilter, pageRequest);
	for (PackageCoursesDTO plain : listPlainTmp.getContent()) {
	    if (plain.getPayments() != null) {
		plain.getPayments().size();
	    }

	    if (plain.getSubscriptions() != null) {
		plain.getSubscriptions().size();
	    }
	}

	return listPlainTmp;
    }

    @Override
    @Transactional
    public List<PackageCoursesDTO> findAllToSignleLesson() throws Exception {
	return packageCoursesDAO.findAllToSignleLesson();
    }

}
