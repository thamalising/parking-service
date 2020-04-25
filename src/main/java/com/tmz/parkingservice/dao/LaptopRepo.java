package com.tmz.parkingservice.dao;

import com.tmz.parkingservice.data.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LaptopRepo extends JpaRepository<Laptop, Integer> {

    // findBy or getBy method name should be started
    List<Laptop> findByBrand(String brand);

//    @Query("from laptop where id=?1 order by brand")
//    List<Laptop> findByBrandSortedCustom(String brand);
}
