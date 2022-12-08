package com.example.demo.user;

import com.example.demo.role.Role;
import com.example.demo.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByName(name);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);
    }

    public ResponseEntity<?> createUser(Form createUserForm) {

        Map<String, String> returnCreateUser = new HashMap<>();

        try {
            // check if there a USER = 1 role in database
            if (roleRepository.findById(createUserForm.getRole_id()).orElse(null) == null) {
                returnCreateUser.put("error", "There are no roles on DB");
                return ResponseEntity.status(404).body(returnCreateUser);
            }
            // check if the email is already used
            if (userRepository.findByEmail(createUserForm.getUser().getEmail()) != null) {
                returnCreateUser.put("400", "The email already used");
            }

            // check if the phone is already used
            if (userRepository.findByMobileNumber(createUserForm.getUser().getMobileNumber()) != null) {
                returnCreateUser.put("400", "The Mobile Number already used");
            }

            // check if the email or phone are already used
            if (!returnCreateUser.isEmpty()) {
                return ResponseEntity.status(400).body(returnCreateUser);
            }

            User user = createUserForm.getUser();
            Long role_id = createUserForm.getRole_id();
            Role role = roleRepository.findById(role_id).orElse(null);
            user.getRoles().add(role);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userRepository.save(user);
            returnCreateUser.put("200", "Account created successfully");
            return ResponseEntity.ok().body(returnCreateUser);

        } catch (Exception e) {
            returnCreateUser.put("500", "General Exception");
            return ResponseEntity.status(500).body(returnCreateUser);
        }

    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public ResponseEntity<?> getUser(String id) {
        Long userId = Long.parseLong(id);
        return ResponseEntity.ok().body(userRepository.findById(userId).orElse(null));
    }

    public ResponseEntity<?> updateUser(String id, User data) {

        Long updateUser = Long.parseLong(id);
        User user = userRepository.findById(updateUser).orElse(null);

        if (user != null) {
            user.setName(data.getName());
            user.setPassword(data.getPassword());
            user.setMobileNumber(data.getMobileNumber());
            user.setEmail(data.getEmail());
            userRepository.save(user);
            return ResponseEntity.ok().body(userRepository.save(user));
        }
        else {
            return ResponseEntity.status(404).body("User Not Found");
        }
    }

    public  ResponseEntity<?> deleteUser(String id) {

        try {
            Long user_id = Long.parseLong(id);
            userRepository.deleteById(user_id);
            return ResponseEntity.ok().body("User Deleted");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }



}
