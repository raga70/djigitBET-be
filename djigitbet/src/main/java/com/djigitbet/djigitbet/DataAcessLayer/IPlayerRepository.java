package com.djigitbet.djigitbet.DataAcessLayer;

import com.djigitbet.djigitbet.Model.DTO.totalPlayerFundsDTO;
import com.djigitbet.djigitbet.Model.Entity.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface IPlayerRepository extends IUserRepository {
   @Query(value = "SELECT   SUM(funds_lost) as totalFundsPayedIn, SUM(funds_payed_out) as totalFundsPayedOut  FROM user inner join player p on user.userid = p.userid", nativeQuery = true)
   List<Double[]> getAggregatedStatistics(); //jpa cannot map to totalPlayerFundsDTO, and only gives you an object array without names
   
   
   
}
