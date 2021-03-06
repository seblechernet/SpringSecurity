package seb.com.springboot404;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class SSUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public SSUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

   @Override
    public UserDetails loadUserByUsername(String appUser) throws UsernameNotFoundException{
        try{
            User user = userRepository.findByUserName(appUser);
            if(user == null){
                System.out.println("User not found with the provided username" + user.toString());
                return null;
            }
            System.out.println("User from username" + user.toString());
            return  new CustomUserDetails(user,user.getPassword(),getAuthorities(user));
//                    user.getUserName(),
//                    user.getPasssword(),
//                    getAuthorities(user));
                    }catch (Exception e){
            throw new UsernameNotFoundException("User Not found");
        }
   }
   private Set<GrantedAuthority> getAuthorities(User appUser){
        Set<GrantedAuthority> authorities =new HashSet<GrantedAuthority>();
        for(Role role : appUser.getRoles()){
            GrantedAuthority grantedAuthority =new SimpleGrantedAuthority(role.getRole());
            authorities.add(grantedAuthority);
        }
       System.out.println("User authorities are"+ authorities.toString());
        return authorities;


   }

}

