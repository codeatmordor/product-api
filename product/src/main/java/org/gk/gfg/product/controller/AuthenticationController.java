package org.gk.gfg.product.controller;

import org.gk.gfg.product.authentication.JwtUtil;
import org.gk.gfg.product.entity.UserEntity;
import org.gk.gfg.product.exception.ProductServiceException;
import org.gk.gfg.product.model.JwtRequest;
import org.gk.gfg.product.model.JwtResponse;
import org.gk.gfg.product.model.User;
import org.gk.gfg.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtil jwtTokenUtil;

  @Autowired
  private UserService userDetailsService;

  @RequestMapping(value = "/gfg/v1/authenticate", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
      throws Exception {

    authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

    final UserDetails userDetails =
        userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

    final String token = jwtTokenUtil.generateToken(userDetails);

    return ResponseEntity.ok(new JwtResponse(token));
  }

  @RequestMapping(value = "/gfg/v1/register", method = RequestMethod.POST)
  public ResponseEntity<UserEntity> saveUser(@RequestBody User user)
      throws ProductServiceException {
    return new ResponseEntity<>(userDetailsService.save(user), HttpStatus.OK);
  }

  private void authenticate(String username, String password) throws ProductServiceException {
    try {
      authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new ProductServiceException("User is disabled. " + e.getMessage());
    } catch (BadCredentialsException e) {
      throw new ProductServiceException("Credentials are invalid. " + e.getMessage());
    }
  }
}

