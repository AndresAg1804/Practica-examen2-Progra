package com.example.practica_examen2.data;

import com.example.practica_examen2.logic.User;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.ArrayList;
import java.util.List;

@Component("userRepository")
public class UserRepository {
    List<User> list;

    public User findById(String id) throws Exception{
        User r = list.stream()
                .filter( e-> e.getEmail().equals(id))
                .findFirst().get();
        return r.clone();
    }
    public List<User> findAll(){return list.stream().map(User::clone).toList();
    }

    public UserRepository() {
        list = new ArrayList<User>();
        var encoder = new BCryptPasswordEncoder();
        list.add(new User("mfreeman@hollywood.com", "Morgan Freeman","{bcrypt}"+encoder.encode("1"),"CLI"));
        list.add(new User("scalle@hollywood.com", "Sasha Calle","{bcrypt}"+encoder.encode("2"),"CLI"));
   }

}
