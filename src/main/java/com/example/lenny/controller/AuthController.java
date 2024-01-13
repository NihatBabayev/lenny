package com.example.lenny.controller;

import com.example.lenny.dto.AuthRequest;
import com.example.lenny.dto.ResponseModel;
import com.example.lenny.dto.UserDTO;
import com.example.lenny.exception.InvalidOtpCodeException;
import com.example.lenny.exception.UserAlreadyExistsException;
import com.example.lenny.security.JwtService;
import com.example.lenny.service.EmailService;
import com.example.lenny.service.UserService;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RedisTemplate<String, String> redisTemplate;
    private final EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseModel<String>> signupUser(@RequestBody UserDTO userDTO,
                                                            @RequestParam("type") String type, // "customer" or "merchant"
                                                            @RequestParam(value = "otp", required = false) String otpCode) {
        String email = userDTO.getEmail();

        if (userService.isUserExists(email)) {
            throw new UserAlreadyExistsException();
        }

        if (otpCode == null) {
            String randomOtp = String.valueOf(new Random().nextInt(9000) + 1000);
            redisTemplate.opsForValue().set(email, randomOtp, 1, TimeUnit.MINUTES);
            emailService.sendEmail(email, "OTP code", "Your OTP code is \n" + randomOtp + "\nDon't share it with others.");
            ResponseModel<String> responseModel = new ResponseModel<>();
            responseModel.setMessage("OTP sent to your email. Check your inbox.");
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        } else {
            String storedOtp = (String) redisTemplate.opsForValue().get(email);
            if (storedOtp != null && storedOtp.equals(otpCode)) {

                userService.saveUser(userDTO, type);
                String jwtToken = jwtService.generateToken(email);

                redisTemplate.delete(email);
                ResponseModel<String> responseModel = new ResponseModel<>();
                responseModel.setData(jwtToken);
                responseModel.setMessage("Successfully registered");
                return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
            } else {

                throw new InvalidOtpCodeException();
            }
        }
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseModel<String>> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String jwtToken = jwtService.generateToken(authRequest.getUsername());
            ResponseModel<String> responseModel = new ResponseModel<>();
            responseModel.setData(jwtToken);
            responseModel.setMessage("Successfully logged in");
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
}
