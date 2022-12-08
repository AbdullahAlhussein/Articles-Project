package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody Form createUserForm)
    {
        return userService.createUser(createUserForm);
    }

    @GetMapping
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id){
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody User user){
       return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>  deleteUser(@PathVariable String id){
       return userService.deleteUser(id);
    }
}
class Form {
    private User user;
    private Long role_id;

    public User getUser() {
        return user;
    }

    public Long getRole_id() {
        return role_id;
    }
}