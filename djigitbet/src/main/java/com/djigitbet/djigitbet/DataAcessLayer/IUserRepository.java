package com.djigitbet.djigitbet.DataAcessLayer;

import com.djigitbet.djigitbet.Model.Entity.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {


    List<User> findByUsername(String username);
}
