package com.geekster.BloggingPlatformAPI.service;

import com.geekster.BloggingPlatformAPI.model.AuthenticationToken;
import com.geekster.BloggingPlatformAPI.model.User;
import com.geekster.BloggingPlatformAPI.model.dto.AuthInput;
import com.geekster.BloggingPlatformAPI.repository.IAuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    IAuthRepo iAuthRepo;

    public void saveAuthToken(AuthenticationToken authToken) {
        iAuthRepo.save(authToken);
    }

    public boolean authenticateUser(AuthInput authInput) {
        String newTokenValue = authInput.getToken();

        AuthenticationToken authToken = iAuthRepo.findFirstByTokenValue(newTokenValue);
        if(authToken == null) {
            return false;
        }
        String tokenConnectedEmail = authToken.getTokenOwner().getUserEmail();
        return tokenConnectedEmail.equals(authInput.getEmail());
    }

    public AuthenticationToken findFirstByUser(User existingUser) {
        return iAuthRepo.findFirstByTokenOwner(existingUser);
    }

    public void remove(AuthenticationToken authToken) {
        iAuthRepo.delete(authToken);
    }

    public boolean authenticate(String email, String tokenValue) {
        AuthenticationToken authToken = iAuthRepo.findFirstByTokenValue(tokenValue);

        if(authToken == null)
        {
            return false;
        }

        String tokenConnectedEmail = authToken.getTokenOwner().getUserEmail();
        return tokenConnectedEmail.equals(email);
    }
}
