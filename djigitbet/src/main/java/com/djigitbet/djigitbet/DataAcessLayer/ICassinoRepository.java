package com.djigitbet.djigitbet.DataAcessLayer;

import com.djigitbet.djigitbet.Model.Entity.Casino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICassinoRepository extends JpaRepository<Casino, Integer> {
Casino getCasinoById(int id);
}
