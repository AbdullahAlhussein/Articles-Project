package com.example.demo.role;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(path = "roles")
@CrossOrigin("*")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<?> createRole(@Valid  @RequestBody Role createrole){
        return roleService.createRole(createrole);
    }

    @GetMapping
    public List<Role> getAllRole(){
        return roleService.getAllRole();
    }

    @GetMapping("/{id}")
    public Role getRole(@PathVariable String id){
        return roleService.getRole(id);
    }

    @PutMapping("/{id}")
    public void updateRole(@PathVariable String id, @RequestBody Role role){
        roleService.updateRole(id, role);
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable String id){
        roleService.deleteRole(id);
    }

}
