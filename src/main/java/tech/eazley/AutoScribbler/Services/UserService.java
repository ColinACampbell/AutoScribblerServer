package tech.eazley.AutoScribbler.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.eazley.AutoScribbler.Models.HttpModels.Database.User;
import tech.eazley.AutoScribbler.Repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    User findUserByEmail(String email)
    {
        return new User("prontobol@gmail.com","root");
    }
}
