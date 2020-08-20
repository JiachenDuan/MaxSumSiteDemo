package com.example.demo.services;

import com.example.demo.models.AuthUser;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

  @Autowired private UserRepository userRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  /**
   * Service for loading user by userName
   *
   * @param userName
   * @return
   * @throws UsernameNotFoundException
   */
  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

    AuthUser curUser = userRepository.findByUserName(userName);
    if (curUser == null) {
      throw new UsernameNotFoundException("User not found with username: " + userName);
    }
    return new User(curUser.getUserName(), curUser.getPassword(), new ArrayList<>());
  }

  /**
   * Service for new user account registration
   *
   * @param userName
   * @param password
   * @return
   * @throws Exception
   */
  public UserDetails registerNewUserAccount(String userName, String password) throws Exception {
    AuthUser curUser = userRepository.findByUserName(userName);
    if (curUser != null) {
      throw new Exception("Username exists " + curUser.getUserName());
    }
    AuthUser authUser = new AuthUser();
    authUser.setUserName(userName);
    authUser.setPassword(passwordEncoder.encode(password));
    userRepository.save(authUser);
    return new User(authUser.getUserName(), authUser.getPassword(), new ArrayList<>());
  }
}
