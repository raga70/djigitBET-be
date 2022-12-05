package com.djigitbet.djigitbet.Repositories;

import com.djigitbet.djigitbet.Model.Entity.Player;
import com.djigitbet.djigitbet.Model.Entity.User;
import com.djigitbet.djigitbet.Model.Entity.UserType;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {


    List<User> findByUsername(String username);

    List<Player> findAllByType(UserType type, Pageable pageable);
}
