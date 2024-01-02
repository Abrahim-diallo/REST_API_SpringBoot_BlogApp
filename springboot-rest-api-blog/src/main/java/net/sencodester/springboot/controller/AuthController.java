package net.sencodester.springboot.controller;

import net.sencodester.springboot.Security.JwtTokenProvider;
import net.sencodester.springboot.entites.Role;
import net.sencodester.springboot.entites.User;
import net.sencodester.springboot.payload.JWTAuthResponse;
import net.sencodester.springboot.payload.LoginDto;
import net.sencodester.springboot.payload.SignUpDto;
import net.sencodester.springboot.repositories.RoleRepository;
import net.sencodester.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

import java.util.Collections;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authentUser(@RequestBody LoginDto loginDto) throws Exception {
        String usernameOrEmail = loginDto.getUsernameOrEmail();
        String password = loginDto.getPassword();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernameOrEmail, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //GET token from token provider
         String token = jwtTokenProvider.generateToken(authentication);
         return ResponseEntity.ok(new JWTAuthResponse(token));
    }
    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<?> signUp(@RequestBody SignUpDto signUpDto) {
        // Verrificatio si l'username existe déjà dans la base de données.'
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }
        // Verrificatio si l'email existe déjà dans la base de données.'
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
        }
        // Création de l'utilisateur'
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        // setter et l'Encodage du mot de passe.
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        //setter le email
        user.setEmail(signUpDto.getEmail());
        userRepository.save(user);
        // Création de la rôle 'ROLE_ADMIN'
        Role role = roleRepository.findByName("ROLE_ADMIN").get();
        // setter dans Collection singleton
        user.setRoles(Collections.singleton(role));
        // Sauvegarde de l'utilisateur dans la base de données.'
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }
}
