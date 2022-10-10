package com.djigitbet.djigitbet.Services;

import com.djigitbet.djigitbet.DataAcessLayer.IPlayerRepository;
import com.djigitbet.djigitbet.DataAcessLayer.IUserRepository;
import com.djigitbet.djigitbet.Model.Entity.Player;
import com.djigitbet.djigitbet.Model.Entity.User;
import jakarta.persistence.OrderBy;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService  {
   
   @Autowired
   private IUserRepository userRepository;


    public List<User> GetAllUsers() {
        return userRepository.findAll();
    }
    
    
    public List<Player>GetAllUsersPagged(int pageNo, int pageSize, String sortBy) {
      
//        Pageable paging=PageRequest.of(pageNo, pageSize, Sort.TypedSort.sort(Player.class).by(sortBy));
//        Page<User> pagedResult = userRepository.findAll(paging); // THERE IS NO WAY TO SORT BY A COLUMN THAT IS NOT IN THE BASE ENTITY , 
//     Now entity framework doesn't have this problem, and doesn't force you to these bad dev practices

        Pageable paging=PageRequest.of(pageNo, pageSize);
        Page<User> pagedResult = userRepository.findAll(paging);
                //TODO: fix it doesnt give only players
        List <Player> players = pagedResult.toList().stream().filter(x->x instanceof Player).map(x->(Player)x).collect(Collectors.toList());//now this is unnecessarily complicated but works
        
        if(sortBy.equals("balance")  ) players.sort(Comparator.comparing(Player::getBalance).reversed());//THIS ONLY SORTS ON THE PAGE WE PULLED
        if(sortBy.equals("winCoefficient") ) players.sort(Comparator.comparing(Player::getWinCoefficient).reversed());
        if(sortBy.equals("fundsLost")) players.sort(Comparator.comparing(Player::getFundsLost).reversed());
        if(sortBy.equals("fundsPayedOut")) players.sort(Comparator.comparing(Player::getFundsPayedOut).reversed());
        //now this well this is stupid, in c# i would have used linq passed the field i want to sort by, and be done, but because java is java we need to create some bloatware
        
        
        return players;

    }
    
    
    
    public User GetUser(int id) {
        return userRepository.findById(id).get();
    }

    
    public User GetUser(String username) {
        return userRepository.findByUsername(username).get(0);
    }
    
    
     
    public User SaveUser(User user) {
       return userRepository.save(user);
    }
    
    
     
    public User UpdateUser(User newUser, int id) {
       //update the user by id
        if(newUser.getPassword() == null || newUser.getPassword().equals("")) {
            newUser.setPassword(GetUser(id).getPassword());  //JPA is a terrible ORM https://stackoverflow.com/questions/28595391/how-to-do-not-update-attributes-of-an-entry-if-such-value-is-null-jpa
        }
       userRepository.save(newUser);
       return userRepository.findById(id).get();

    }
    
    
    
     
    public void DeleteUser(int id){
        userRepository.deleteById(id);
    }
    

}
