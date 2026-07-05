package de.htw_berlin.tableplanner.web;

import de.htw_berlin.tableplanner.dto.AuthResponse;
import de.htw_berlin.tableplanner.dto.LoginRequest;
import de.htw_berlin.tableplanner.dto.RegisterRequest;
import de.htw_berlin.tableplanner.model.Restaurant;
import de.htw_berlin.tableplanner.repository.RestaurantRepository;
import de.htw_berlin.tableplanner.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        if (restaurantRepository.existsByName(request.name())) {
            log.info("Registrierung fehlgeschlagen, Name bereits vergeben: {}", request.name());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (restaurantRepository.existsByEmail(request.email())) {
            log.info("Registrierung fehlgeschlagen, E-Mail bereits vergeben: {}", request.email());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setName(request.name());
        restaurant.setEmail(request.email());
        restaurant.setPasswordHash(passwordEncoder.encode(request.password()));
        restaurantRepository.save(restaurant);

        log.info("Neues Restaurant registriert: {}", restaurant.getName());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Optional<Restaurant> restaurantOpt = request.identifier().contains("@")
                ? restaurantRepository.findByEmail(request.identifier())
                : restaurantRepository.findByName(request.identifier());

        if (restaurantOpt.isEmpty()) {
            log.info("Login fehlgeschlagen, unbekannt: {}", request.identifier());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Restaurant restaurant = restaurantOpt.get();

        if (!passwordEncoder.matches(request.password(), restaurant.getPasswordHash())) {
            log.info("Login fehlgeschlagen, falsches Passwort: {}", request.identifier());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        log.info("Login erfolgreich: {}", restaurant.getName());

        String token = jwtUtil.generateToken(restaurant.getId(), restaurant.getName());
        return ResponseEntity.ok(new AuthResponse(token, restaurant.getName()));
    }
}