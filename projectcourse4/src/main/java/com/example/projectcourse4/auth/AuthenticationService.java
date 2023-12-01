package com.example.projectcourse4.auth;

import com.example.projectcourse4.entity.Customer;
import com.example.projectcourse4.entity.Supplier;
import com.example.projectcourse4.repository.CustomerRepository;
import com.example.projectcourse4.repository.SupplierRepository;
import com.example.projectcourse4.roles.Role;
import com.example.projectcourse4.token.Token;
import com.example.projectcourse4.token.TokenRepository;
import com.example.projectcourse4.token.TokenType;
import com.example.projectcourse4.entity.User;
import com.example.projectcourse4.repository.UserRepository;
import com.example.projectcourse4.configuration.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final CustomerRepository customerRepository;
  private final SupplierRepository supplierRepository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder()
        .firstName(request.getFirst_name())
        .lastName(request.getLast_name())
            .phoneNumber(request.getPhone_number())
            .username(request.getUsername())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .build();

    var savedUser = repository.save(user);
    Map<String, Object> extraClaims = new HashMap<>();
    extraClaims.put("role", user.getRole());
    var jwtToken = jwtService.generateToken(extraClaims,user);
//    var refreshToken = jwtService.generateRefreshToken(user);
    if(request.getRole().equals(Role.SUPPLIER)){
      var supplier = Supplier.builder()
              .supplierEmail(request.getEmail())
              .userId(repository.findUserByUsername(user.getUsername()).get().getUserId())
              .contactPerson(Long.valueOf(request.getPhone_number()))
              .supplierName(request.getUsername())
              .build();
      supplierRepository.save(supplier);
    } else {
      var customer = Customer.builder()
              .customerEmail(request.getEmail())
              .userId(repository.findUserByUsername(user.getUsername()).get().getUserId())
              .customerName(request.getUsername())
              .customerPhoneNumber(Long.valueOf(request.getPhone_number()))
              .build();
      customerRepository.save(customer);
    }
    saveUserToken(savedUser, jwtToken);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
//            .refreshToken(refreshToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()
        )
    );
    var user = repository.findUserByUsername(request.getUsername())
        .orElseThrow(null);
    Map<String, Object> extraClaims = new HashMap<>();
    extraClaims.put("role", user.getRole());
    var jwtToken = jwtService.generateToken(extraClaims,user);
//    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
//            .refreshToken(refreshToken)
        .build();
  }

  private void saveUserToken(User users, String jwtToken) {
    var token = Token.builder()
        .user_id(users)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userName;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userName = jwtService.extractUsername(refreshToken);
    if (userName != null) {
      var user = this.repository.findUserByUsername(userName)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
               .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
