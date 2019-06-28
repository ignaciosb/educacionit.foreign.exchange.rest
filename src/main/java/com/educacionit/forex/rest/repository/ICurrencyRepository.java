

package com.educacionit.forex.rest.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educacionit.forex.rest.entity.CurrencyEntity;


@Repository
public interface ICurrencyRepository extends JpaRepository<CurrencyEntity, Long> {


    List<CurrencyEntity> findAllByEnabled (Boolean val);

    CurrencyEntity findTop1ByEnabledOrderByIdDesc (Boolean val);
}