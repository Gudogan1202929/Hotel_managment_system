package com.example.HotelManagementSystem.user.service;

import com.example.HotelManagementSystem.entity.Customer;
import com.example.HotelManagementSystem.repository.CustomerRepo;
import com.example.HotelManagementSystem.user.dto.ChangeRole;
import com.example.HotelManagementSystem.user.dto.ChangeUserName;
import com.example.HotelManagementSystem.user.dto.ChangeUserPassword;
import com.example.HotelManagementSystem.user.dto.Role;
import com.example.HotelManagementSystem.user.entity.User;
import com.example.HotelManagementSystem.user.jwt.Check;
import com.example.HotelManagementSystem.user.jwt.Create;
import com.example.HotelManagementSystem.user.repositorie.UserRepo;
import com.example.HotelManagementSystem.utils.encryption.Encryption;
import com.example.HotelManagementSystem.utils.encryption.Hash;
import org.apache.tomcat.util.buf.UDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final Create create;
    private final Check check;
    private final UserRepo userRepo;
    private final CustomerRepo customerRepo;

    @Autowired
    public UserService(UserRepo userRepo, Create create , Check check, CustomerRepo customerRepo) {
        this.create = create;
        this.check = check;
        this.userRepo = userRepo;
        this.customerRepo = customerRepo;
    }

    public String UserSignIn(User user) {
        try {
            String token = create.createJWT(user);
            if (token.equals("The user does not exist")) {
                return "User with email" + user.getUsername() + " does not exist";
            }
            if (token.equals("The password is incorrect")) {
                return "The password is incorrect";
            }
            return token;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public User UserSignUp(User user){
        try {
            if (check.CheckIfUserExists(user.getUsername()) != null) {
                return null;
            }
            return create.UserSignUp(user);
        } catch (Exception e) {
            return null;
        }
    }

    public String UserChangePassword(ChangeUserPassword changeUserPassword) {
        try {
            User user1 = userRepo.findByUsername(changeUserPassword.getUsername());
            if (user1 == null) {
                return "User with email " + changeUserPassword.getUsername() + " does not exist";
            }

            String decryptedOldPassword = Encryption.Decrypt(changeUserPassword.getOldPassword());
            String hashedOldPassword = Hash.hashing(decryptedOldPassword);

            if (hashedOldPassword.equals(user1.getPassword())) {
                String decryptedNewPassword = Encryption.Decrypt(changeUserPassword.getNewPassword());
                String newHashedPassword = Hash.hashing(decryptedNewPassword);
                user1.setPassword(newHashedPassword);
                userRepo.save(user1);
                return "User with email " + changeUserPassword.getUsername() + " changed password successfully";
            } else {
                return "The old password is incorrect";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public String UserChangeUserName(ChangeUserName changeUserName) {
        try {
            User user1 = userRepo.findByUsername(changeUserName.getUsername());
            if (user1 == null) {
                return "User with email " + changeUserName.getUsername() + " does not exist";
            }

            String decryptedOldPassword = Encryption.Decrypt(changeUserName.getPassword());
            String hashedOldPassword = Hash.hashing(decryptedOldPassword);

            if (hashedOldPassword.equals(user1.getPassword())) {
                user1.setUsername(changeUserName.getNewUserName());
                userRepo.save(user1);
                return "Username become " + changeUserName.getNewUserName() + " successfully";
            } else {
                return "The password is incorrect";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String UserChangeRole(ChangeRole changeRole) {
        try {
            User user1 = userRepo.findByUsername(changeRole.getUsername());
            if (user1 == null) {
                return "User with email " + changeRole.getUsername() + " does not exist";
            }

            String decryptedPassword = Encryption.Decrypt(changeRole.getPassword());
            String hashedPassword = Hash.hashing(decryptedPassword);

            if (hashedPassword.equals(user1.getPassword())) {
                Role newRole = Role.valueOf(changeRole.getNewRole().toUpperCase());
                user1.setRole(newRole);
                userRepo.save(user1);
                return "Role become " + changeRole.getNewRole() + " successfully";
            } else {
                return "The password is incorrect";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public User getUserById(Long id, String password) {
        try {
            User user = userRepo.findById(id).orElse(null);
            if (user == null) {
                return null;
            }
            String decryptedPassword = Encryption.Decrypt(password);
            String hashedPassword = Hash.hashing(decryptedPassword);
            if (hashedPassword.equals(user.getPassword())) {
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public String deleteUserById(Long id, String password) {
        try {
            User user = userRepo.findById(id).orElse(null);
            if (user == null) {
                return "User with id " + id + " does not exist";
            }
            String decryptedPassword = Encryption.Decrypt(password);
            String hashedPassword = Hash.hashing(decryptedPassword);
            if (hashedPassword.equals(user.getPassword())) {
                // Check for existing reservations
                Optional<Customer> customerOptional = customerRepo.findByUserId(id);
                if (customerOptional.isPresent()) {
                    Customer customer = customerOptional.get();
                    if (!customer.getReservations().isEmpty()) {
                        return "Cannot delete user because they have existing reservations";
                    }
                    customerRepo.delete(customer);
                }
                userRepo.delete(user);
                return "User with id " + id + " deleted successfully";
            } else {
                return "The password is incorrect";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
