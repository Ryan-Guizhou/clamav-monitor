package com.clamav.backend.controller;

import com.clamav.backend.base.BaseController;
import com.clamav.backend.base.Response;
import com.clamav.backend.entity.User;
import com.clamav.backend.service.UserService;
import com.clamav.backend.utils.JwtTokenProvider;
import com.clamav.backend.vo.AuthenticationRequest;
import com.clamav.backend.vo.AuthenticationResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Slf4j
@Indexed
@RestController
@RequestMapping("/api")
@Api("登录认证管理")
public class AuthenticationController extends BaseController {


    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private JwtTokenProvider jwtTokenProvider;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/login")
    @ApiOperation("登录")
    public Response createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenProvider.generateToken(userDetails);
        User user = userService.findByUsername(authenticationRequest.getUsername());
        String avatar = user.getAvatar();
        if (avatar == null || avatar.isEmpty()) {
            avatar = user.getUsername().substring(0, Math.min(user.getUsername().length(), 3));
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Response.success().setData(new AuthenticationResponse(token, avatar,user.getUsername(),user.getCreateTime().format(formatter)));
    }

    @PostMapping(value = "/register")
    @ApiOperation("注册")
    public Response saveUser(@RequestBody User user) throws Exception {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return Response.success().setData(user);
    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}