package com.stefan.security.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
public interface JwtTokenRepository extends JpaRepository<Token,Integer> {
    @Query("SELECT tkn                                      "
          +  " FROM Token tkn                               "
          +  " INNER JOIN User usr ON tkn.user.id = usr.id  "
          +  " WHERE usr.id = :userId                       "
          +  " AND (tkn.isExpired = false OR tkn.isRevoked = false)")

    List<Token> findAllValidTokensByUserId(Integer userId);

    Optional<Token> findByToken(String jwtToken);
}
