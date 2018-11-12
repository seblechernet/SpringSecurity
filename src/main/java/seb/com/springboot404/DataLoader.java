package seb.com.springboot404;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner{
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;

    @Override
    public void run(String... strings) throws Exception{
        roleRepository.save(new Role("ADMIN"));
        roleRepository.save(new Role("USER"));
         Role adminRole=roleRepository.findByRole("ADMIN");
         Role userRole =roleRepository.findByRole("USER");

         User user=new User("jim@jim.com","password","Jim","Jimmerson",true,"jim");
         userService.saveUser(user);

        user=new User("bob@bob.com","password","Bob","Bobmerson",true,"bob");
        userService.saveUser(user);

        user=new User("admin@admin.com","password","Admin","User",true,"admin");
        userService.saveAdmin(user);

        user=new User("sam@everyman.com","password","Sam","Everyman",true,"sam");
        userService.saveAdmin(user);
    }
}
