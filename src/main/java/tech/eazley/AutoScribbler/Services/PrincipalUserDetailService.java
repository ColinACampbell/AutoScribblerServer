package tech.eazley.AutoScribbler.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.eazley.AutoScribbler.Models.HttpModels.Database.PrincipalUserDetails;
import tech.eazley.AutoScribbler.Models.HttpModels.Database.User;

@Service
public class PrincipalUserDetailService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findUserByEmail(username);

        return new PrincipalUserDetails(user);
    }
}
