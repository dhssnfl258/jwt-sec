package com.example.jwtsecurityfin.config.auth;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    private final OAuthService oAuthService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }

    //@NoAuth
    @GetMapping("/{socialLoginType}") //GOOGLE이 들어올 것이다.
    public void socialLoginRedirect(@PathVariable(name="socialLoginType") String SocialLoginPath) throws IOException {
        SocialLoginType socialLoginType= SocialLoginType.valueOf(SocialLoginPath.toUpperCase());
        oAuthService.request(socialLoginType);
    }

    @ResponseBody
    @GetMapping(value = "/{socialLoginType}/callback")
    public ResponseEntity<String> callback (
        @PathVariable(name = "socialLoginType") String socialLoginPath,
        @RequestParam(name = "code") String code)throws IOException{
        System.out.println(">> 소셜 로그인 API 서버로부터 받은 code :"+ code);
        SocialLoginType socialLoginType= SocialLoginType.valueOf(socialLoginPath.toUpperCase());
        String jwtToken =oAuthService.oAuthLogin(socialLoginType,code);

        System.out.println("jwtToken :  " + jwtToken);
        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }
}
