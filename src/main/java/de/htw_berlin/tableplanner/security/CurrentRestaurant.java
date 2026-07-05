package de.htw_berlin.tableplanner.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentRestaurant {

    public Long getId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Long restaurantId)) {
            throw new IllegalStateException("No authenticated restaurant found");
        }
        return restaurantId;
    }
}