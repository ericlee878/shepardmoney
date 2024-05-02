package com.shepherdmoney.interviewproject.controller;

import com.shepherdmoney.interviewproject.vo.request.CreateUserPayload;

// import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shepherdmoney.interviewproject.model.User;
import com.shepherdmoney.interviewproject.repository.UserRepository;

@RestController
public class UserController {

    // TODO: wire in the user repository (~ 1 line)
    private UserRepository userRepository;

    @PutMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody CreateUserPayload payload) {
        // TODO: Create an user entity with information given in the payload, store it in the database
        //       and return the id of the user in 200 OK response
        try{
            User user = new User();

            user.setName(payload.getName());
            user.setEmail(payload.getEmail());

            User savedUser = userRepository.save(user);

            return ResponseEntity.ok(savedUser.getId());
        }
        catch(Exception e){
            System.out.println("Exception occurred while creating user: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user.");
        }
    }

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestParam int userId) {
        // TODO: Return 200 OK if a user with the given ID exists, and the deletion is successful
        //       Return 400 Bad Request if a user with the ID does not exist
        //       The response body could be anything you consider appropriate
        // Check if a user with the given ID exists
        if (userRepository.existsById(userId)) {
            try {
                // Delete the user from the database
                userRepository.deleteById(userId);
                return ResponseEntity.ok("User deleted successfully.");
            } catch (Exception e) {
                // If there is an exception during deletion, log it and return a server error
                System.out.println("Error deleting user: "+  e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the user.");
            }
        } else {
            // If no user with the given ID exists, return a bad request
            return ResponseEntity.badRequest().body("No user found with ID: " + userId);
        }
        
    }
}
