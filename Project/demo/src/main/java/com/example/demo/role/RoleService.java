package com.example.demo.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public ResponseEntity<?> createRole(Role createrole) {

        Map<String, String> returnCreateArticle = new HashMap<>();

        try{
            roleRepository.save(createrole);
            returnCreateArticle.put("200", "Role created successfully");
            return ResponseEntity.ok().body(returnCreateArticle);

        } catch (Exception e)
        {
            returnCreateArticle.put("500","General Exception");
            return ResponseEntity.status(500).body(returnCreateArticle);
        }
    }

    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    public Role getRole(String id) {
        Long RoleId =Long.parseLong(id);
        return roleRepository.findById(RoleId).orElse(null);
    }

    public void updateRole(String id, Role data) {
        Long updateRole = Long.parseLong(id);
        Role role = roleRepository.findById(updateRole).orElse(null);

        if(role != null){
            role.setName(data.getName());
            roleRepository.save(role);
        }
    }

    public void deleteRole(String id) {
        Long deleteId = Long.parseLong(id);
        roleRepository.deleteById(deleteId);
    }
}
