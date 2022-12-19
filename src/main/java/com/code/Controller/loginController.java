package com.code.Controller;

import com.code.Model.jwtRequest;
import com.code.Model.jwtResponse;
import com.code.Security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class loginController {
    @Autowired
    private AuthenticationManager authenticationManager ;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @PostMapping(value = "/auth")
    public ResponseEntity<?> createToken(@RequestBody jwtRequest jwtRequest) throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        }
        catch (BadCredentialsException e){
            throw new Exception("Incorect....",e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(
                jwtRequest.getUsername()
        );
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new jwtResponse(jwt));
    }
}
