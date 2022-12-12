package tech.eazley.AutoScribbler.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.eazley.AutoScribbler.Models.Database.User;
import tech.eazley.AutoScribbler.Repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findUserByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    public User createUser(User user)
    {
        userRepository.save(user);
        return user;
    }
}
