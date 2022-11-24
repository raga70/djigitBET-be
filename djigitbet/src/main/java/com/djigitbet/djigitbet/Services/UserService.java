package com.djigitbet.djigitbet.Services;

import com.djigitbet.djigitbet.DataAcessLayer.IUserRepository;
import com.djigitbet.djigitbet.Model.Entity.Player;
import com.djigitbet.djigitbet.Model.Entity.User;
import com.djigitbet.djigitbet.Model.Entity.UserType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> GetAllUsers() {
        return userRepository.findAll();
    }


    public List<Player> GetAllUsersPagged(int pageNo, int pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        List<Player> pagedResult = userRepository.findAllByType(UserType.PLAYER, paging); // jpa`s field detections is kinda broken and requires a separate method in the base repo of type the children  
        return pagedResult;
    }


    public User GetUser(int id) {
        var user = userRepository.findById(id);
        return user.orElse(null);
    }


    public User GetUser(String username) {
        return userRepository.findByUsername(username).get(0);
    }


    public User SaveUser(User user) {
        return userRepository.save(user);
    }


    public User UpdateUser(User newUser, int id) {
        //update the user by id
        if (newUser.getPassword() == null || newUser.getPassword().equals("")) {
            newUser.setPassword(GetUser(id).getPassword());  //JPA is a terrible ORM https://stackoverflow.com/questions/28595391/how-to-do-not-update-attributes-of-an-entry-if-such-value-is-null-jpa
        }
        return userRepository.save(newUser);
    }


    public void DeleteUser(int id) {
        userRepository.deleteById(id);
    }


}
