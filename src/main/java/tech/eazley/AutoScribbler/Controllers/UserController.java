package tech.eazley.AutoScribbler.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.eazley.AutoScribbler.Filters.JWTFilter;
import tech.eazley.AutoScribbler.Models.Database.User;
import tech.eazley.AutoScribbler.Models.HttpModels.UserAuthBody;
import tech.eazley.AutoScribbler.Services.UserService;
import tech.eazley.AutoScribbler.Utils.JWTUtil;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JWTUtil jwtUtil;

    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody User user)
    {

        if (userService.findUserByEmail(user.getEmail()) != null)
        {
            return new ResponseEntity<>(null,HttpStatus.CONFLICT);
        }

        String oldPassword = user.getPassword();
        String newPassword = passwordEncoder.encode(oldPassword);
        user.updatePassword(newPassword);

        userService.createUser(user);

        String token = jwtUtil.generateToken(user);

        return new ResponseEntity<UserAuthBody>(new UserAuthBody(token,user), HttpStatus.CREATED);
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authUser(@RequestBody User loginUser)
    {
        User user = userService.findUserByEmail(loginUser.getEmail());

        if (user == null)
            return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);

        if (!passwordEncoder.matches(loginUser.getPassword(), user.getPassword()))
            return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);



        String token = jwtUtil.generateToken(user);

        return new ResponseEntity<UserAuthBody>(new UserAuthBody(token,user), HttpStatus.OK);
    }
}
