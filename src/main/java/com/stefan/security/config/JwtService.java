//package com.stefan.security.config;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//@Service
//public class JwtService {
//
//    private static final String SECRET_KEY = "InhiGdRTyi27remZbg0g25y7zy92TIVUZlXwsWPnmLOrXspG7aDopBqy2XFkLuPB\n";
//
//    public String extractUsername(String jwtToken) {
//        return extractClaim(jwtToken, Claims::getSubject);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    public String generateToken(UserDetails userDetails) //without extra claims
//    {
//        return generateToken(new HashMap<>(), userDetails);
//    }
//
//    public boolean isTokenValid(String token, UserDetails userDetails) {
//        final String userName = extractUsername(token);
//        return (userName.equals(userDetails.getUsername())) && isTokenExpired(token);
//    }
//
//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Date extractExpiration(String token) {
//        return extractClaim(token,Claims::getExpiration);
//    }
//
//    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
//        return Jwts
//                .builder()
//                .setClaims(extraClaims)
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
//                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public Claims extractAllClaims(String jwtToken) {
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(getSignInKey()) //secret key
//                .build()
//                .parseClaimsJws(jwtToken)
//                .getBody();
//    }
//
//    private Key getSignInKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//        return Keys.hmacShaKeyFor(keyBytes); //Creates a new SecretKey instance for use with HMAC-SHA algorithms based on the specified key byte array.
//    }
//}
