package com.djigitbet.djigitbet.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPlayerRepository extends IUserRepository {
    @Query(value = "SELECT   SUM(funds_lost) as totalFundsPayedIn, SUM(funds_payed_out) as totalFundsPayedOut  FROM user inner join player p on user.userid = p.userid", nativeQuery = true)
    List<Double[]> getAggregatedStatistics(); //jpa cannot map to totalPlayerFundsDTO, and only gives you an object array without names


}
