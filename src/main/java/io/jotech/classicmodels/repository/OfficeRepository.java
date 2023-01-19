package io.jotech.classicmodels.repository;


import io.jotech.classicmodels.entity.Office;

public interface OfficeRepository extends JpaRepository<Office,String> {
    Office updateByOfficeCode(String officeCode, Office office);
}
