// package com.examly.springapp.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.examly.springapp.config.JwtUtil;
// import com.examly.springapp.model.UserEntity;
// import com.examly.springapp.service.UserService;

// @RestController
// @RequestMapping("/auth")
// public class UserController {
//     @Autowired
//     UserService ser;
//     @PostMapping("/reg")
//     public String register(@RequestBody UserEntity ent){
//         ser.insert(ent);
//         return "User reg success";
//     }

//     @PostMapping("/login")
//     public String login(@RequestParam String email,@RequestParam String password){
//         UserEntity user = ser.authenticate(email,password);
//         if(user!=null){
//             String token = JwtUtil.generateToken(user.getEmail());
//             System.out.println("Generated JWT Token: " + token);
//             return "Logged in";
//         }
//         else{
//             return "Invalid";
//         }
//     }
// }


