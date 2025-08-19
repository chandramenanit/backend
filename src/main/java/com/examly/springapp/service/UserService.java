// package com.examly.springapp.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import com.examly.springapp.model.UserEntity;
// import com.examly.springapp.repository.UserRepo;
// @Service
// public class UserService {

//     @Autowired
//     PasswordEncoder encoder;
//     @Autowired
//     UserRepo repo;
//     public void insert(UserEntity ent) {
//         ent.setPassword(encoder.encode(ent.getPassword()));
//         repo.save(ent);
//     }
//     public UserEntity authenticate(String email, String password) {
//         return repo.findByEmail(email).filter(user->encoder.matches(password,user.getPassword())).orElse(null);
//     }

    
// }


