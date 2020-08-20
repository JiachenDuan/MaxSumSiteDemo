package com.example.demo;

import com.example.demo.models.*;
import com.example.demo.services.MyUserDetailsService;
import com.example.demo.utils.Utils;
import datastructure.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.utils.JwtUtil;

@RestController
public class DemoController {

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private JwtUtil jwtTokenUtil;

  @Autowired private Utils utils;

  @Autowired private MyUserDetailsService userDetailsService;

    /**
     * End point for authentication
     * @param authenticationRequest
     * @return
     * @throws Exception
     */
  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public ResponseEntity<?> authenticationToken(
      @RequestBody AuthenticationRequest authenticationRequest) throws Exception {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              authenticationRequest.getUserName(), authenticationRequest.getPassword()));
    } catch (BadCredentialsException e) {
      throw new Exception("Incorrect username or password", e);
    }
    final UserDetails userDetails =
        userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
    final String jwt = jwtTokenUtil.generateToken(userDetails);
    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }

    /**
     * End point for new user registration
     * @param authenticationRequest
     * @return
     * @throws Exception
     */
  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public ResponseEntity<?> registerAccount(@RequestBody AuthenticationRequest authenticationRequest)
      throws Exception {
    final UserDetails userDetails =
        userDetailsService.registerNewUserAccount(
            authenticationRequest.getUserName(), authenticationRequest.getPassword());
    final String jwt = jwtTokenUtil.generateToken(userDetails);
    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }

    /**
     * End point for calculating the maxsum
     * @param maxsumRequest
     * @return
     * @throws Exception
     */
  @RequestMapping(value = "/maxsum", method = RequestMethod.POST)
  public ResponseEntity<?> maxSum(@RequestBody MaxsumRequest maxsumRequest) throws Exception {
    final String serializedBinaryTree = maxsumRequest.getSerializedBinaryTree();
    final int maxSum = utils.getMaxSumFromSerializedBinaryTree(serializedBinaryTree);
    return ResponseEntity.ok(new MaxSumResponse(maxSum));
  }
}
