package com.djigitbet.djigitbet.DataAcessLayer;

import com.djigitbet.djigitbet.Entity.impl.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    

}
