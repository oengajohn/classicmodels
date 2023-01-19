package io.jotech.classicmodels.repository.it;

import java.util.List;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import classicmodels.util.ArquillianWarUtils;
import io.jotech.classicmodels.entity.Office;
import io.jotech.classicmodels.producers.EntityManagerProducer;
import io.jotech.classicmodels.repository.JpaRepository;
import io.jotech.classicmodels.repository.OfficeRepository;
import io.jotech.classicmodels.repository.Repository;
import io.jotech.classicmodels.repository.impl.JpaRepositoryImpl;
import io.jotech.classicmodels.repository.impl.OfficeRepositoryImpl;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@ExtendWith(ArquillianExtension.class)
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OfficeRepositoryTest {


    @Deployment
    public static Archive<?> createDeployment() {
        WebArchive webArchive = ArquillianWarUtils
                .getBasicWebArchive("OfficeRepositoryTest.war")
                .addClasses(
                        OfficeRepository.class,
                        OfficeRepositoryImpl.class,
                        JpaRepositoryImpl.class,
                        JpaRepository.class,
                        Repository.class,
                        EntityManagerProducer.class
                )
                .addPackages(true, "io.jotech.classicmodels.entity");
        System.out.println(webArchive.toString(true));
        return webArchive;
    }

    @Inject
    private OfficeRepository officeRepository;

    @Test
    @DisplayName("confirmCorrectInjection")
    @Order(1)
    void confirmCorrectInjection() {
        Assertions.assertNotNull(officeRepository);
    }


    @Test
    @DisplayName("OfficeFindAll")
    @Order(2)
    void officeFindAll() {
        List<Office> offices = officeRepository.listAll(0, 100);
        int expected = 7;
        Assertions.assertEquals(expected, offices.size(), "Offices should be " + 7 + " at the point of invoking listAll()");
    }

    @Test
    @DisplayName("OfficeInsert")
    @Order(3)
    void officeInsert() {
        var office = Office.builder()
                .officeCode("8")
                .city("Nairobi")
                .phone("+254700596314")
                .addressLine1("Mayfair")
                .country("Kenya")
                .postalCode("047")
                .territory("EA")
                .build();
        Office insertedOffice = officeRepository.insert(office);
        Assertions.assertNotNull(insertedOffice.getOfficeCode());


    }

    @Test
    @DisplayName("OfficeFindAllAfterInsertion")
    @Order(4)
    void OfficeFindAllAfterInsertion() {
        List<Office> offices = officeRepository.listAll(0, 100);
        int expected = 8;
        Assertions.assertEquals(expected, offices.size(), "Offices should be " + 8 + " at the point of invoking listAll()");
    }

    @Test
    @DisplayName("officeUpdateOfficeWithGivenOfficeCode")
    @Order(5)
    void officeUpdateOfficeWithGivenOfficeCode() {
        var office = Office.builder()
                .city("Kisii")
                .addressLine1("Bobasi")
                .build();
        var officeCode = "8";

        Office updatedOffice = officeRepository.updateByOfficeCode(officeCode, office);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Kisii", updatedOffice.getCity()),
                () -> Assertions.assertEquals("Bobasi", updatedOffice.getAddressLine1()),
                () -> Assertions.assertEquals("8", updatedOffice.getOfficeCode())
        );

    }

    @Test
    @DisplayName("officeUpdateOfficeWithGivenWrongOfficeCode")
    @Order(6)
    void officeUpdateOfficeWithGivenWrongOfficeCode() {
        var office = Office.builder()
                .city("Kisii")
                .addressLine1("Bobasi")
                .build();
        var officeCode = "456665";

        Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> officeRepository.updateByOfficeCode(officeCode, office)
        );

    }
    @Test
    @DisplayName("DeletingAnOfficeWithGivenOfficeCode")
    void deletingAnOfficeWithGivenOfficeCode() {
        //given
        var officeCode = "8";
//        when
        boolean wasDeleted = officeRepository.deleteById(officeCode);
//        then
        Assertions.assertTrue(wasDeleted);

    }



}