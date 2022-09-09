package co.javiermantilla.auth.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.javiermantilla.auth.authservice.dto.JwtRequest;
import co.javiermantilla.auth.authservice.dto.JwtResponse;
import co.javiermantilla.auth.authservice.entity.User;
import co.javiermantilla.auth.authservice.helper.JwtUtil;
import co.javiermantilla.auth.authservice.service.CustomUserService;

@RestController
public class AuthController {

	@Autowired
    private CustomUserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest) throws Exception {

        System.out.println(jwtRequest);
        try{
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));

        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Bad Cred!!");
        }

        //fine code

        UserDetails user = this.userService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtil.generateToken(user);
        System.out.println("Token = "+token);

        return ResponseEntity.ok(new JwtResponse(token));

    }
    
    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody User user){

        return ResponseEntity.ok(userService.saveUser(user));
    }
    
    //Only ADMIN role can access this
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/welcome")
    public String test(){
        return "Welcome with token user ROL ADMIN !!";
    }
    
    //Only USER role can access this
    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping(value = "/welcomeuser")
    public String testuser(){
        return "Welcome with token USER !!";
    }
    
    

	
}
