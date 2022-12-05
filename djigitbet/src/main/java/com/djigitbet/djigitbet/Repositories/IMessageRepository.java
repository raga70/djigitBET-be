package com.djigitbet.djigitbet.Repositories;

import com.djigitbet.djigitbet.Model.Entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Integer> {
    Message getMessageById(int id);

    Page<Message> findAll(Pageable paging);
}
