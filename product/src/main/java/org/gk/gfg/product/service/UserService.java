package org.gk.gfg.product.service;

import java.util.ArrayList;
import org.gk.gfg.product.model.User;
import org.gk.gfg.product.model.UserEntity;
import org.gk.gfg.product.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private PasswordEncoder bcryptEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity user = userRepo.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }
    return new org.springframework.security.core.userdetails.User(user.getUsername(),
        user.getPassword(), new ArrayList<>());
  }

  public UserEntity save(User user) {
    UserEntity newUser = new UserEntity();
    newUser.setUsername(user.getUsername());
    newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
    return userRepo.save(newUser);
  }
}
