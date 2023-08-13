package com.geekster.BloggingPlatformAPI.repository;

import com.geekster.BloggingPlatformAPI.model.AuthenticationToken;
import com.geekster.BloggingPlatformAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthRepo extends JpaRepository<AuthenticationToken, Integer> {

    AuthenticationToken findFirstByTokenValue(String newTokenValue);

    AuthenticationToken findFirstByTokenOwner(User existingUser);
}
