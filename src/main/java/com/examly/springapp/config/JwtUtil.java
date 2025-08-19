// package com.examly.springapp.config;

// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.security.Keys;
// import java.security.Key;
// import java.util.Date;
 
// public class JwtUtil {
 
//     // In real apps, store in config & keep secret
//     private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
 
//     public static String generateToken(String username) {
//         return Jwts.builder()
//                 .setSubject(username)
//                 .setIssuer("Chandramenan")
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1 hour
//                 .signWith(key)
//                 .compact();
//     }
// }



