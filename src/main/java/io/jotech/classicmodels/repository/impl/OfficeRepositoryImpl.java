package io.jotech.classicmodels.repository.impl;


import static jakarta.transaction.Transactional.TxType.REQUIRED;
import io.jotech.classicmodels.entity.Office;
import io.jotech.classicmodels.repository.OfficeRepository;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

public class OfficeRepositoryImpl extends JpaRepositoryImpl<Office, String> implements OfficeRepository {


    @Inject
    private EntityManager entityManager;

    protected OfficeRepositoryImpl() {
        super(Office.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    @Transactional(REQUIRED)
    public Office updateByOfficeCode(String officeCode, Office office) {
        Office officeToUpdate = this.read(officeCode);
        if(officeToUpdate!=null){
            officeToUpdate.setCity(office.getCity());
            officeToUpdate.setAddressLine1(office.getAddressLine1());
            this.update(officeToUpdate);
            return officeToUpdate;
        }
        throw new UnsupportedOperationException("Office with office code"+ officeCode+" does not exist");


    }
}
