package com.example.jwtsecurityfin.config.auth;

import com.example.jwtsecurityfin.domain.user.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {
	private final GoogleOauth googleOauth;
	private final HttpServletResponse response;
	private final AuthenticationService authenticationService;

	private final UserRepository repository;


	public void request(SocialLoginType socialLoginType) throws IOException {
		String redirectURL;
		switch (socialLoginType){
			case GOOGLE:{
				//각 소셜 로그인을 요청하면 소셜로그인 페이지로 리다이렉트 해주는 프로세스이다.
				redirectURL= googleOauth.getOauthRedirectURL();
			}break;
			default:{
				throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
			}

		}

		response.sendRedirect(redirectURL);
	}

	public String oAuthLogin(SocialLoginType socialLoginType, String code) throws IOException {
		String result = null;
		switch (socialLoginType){
			case GOOGLE:{
				ResponseEntity<String> accessTokenResponse= googleOauth.requestAccessToken(code);
				//응답 객체가 JSON형식으로 되어 있으므로, 이를 deserialization해서 자바 객체에 담을 것이다.
				GoogleOAuthToken oAuthToken=googleOauth.getAccessToken(accessTokenResponse);

				//액세스 토큰을 다시 구글로 보내 구글에 저장된 사용자 정보가 담긴 응답 객체를 받아온다.
				ResponseEntity<String> userInfoResponse=googleOauth.requestUserInfo(oAuthToken);
				//다시 JSON 형식의 응답 객체를 자바 객체로 역직렬화한다.
				GoogleUser googleUser= googleOauth.getUserInfo(userInfoResponse);

				String email =googleUser.getEmail();
				String name = googleUser.getName();

				RegisterRequest request = RegisterRequest.builder().email(email).name(name).build();

				/*
					소셜 로그인을 (email, name)을 통해서 jwtToken을 발급 받는것이다.
					그이후에 프로세스는 매우 복잡하다. 별도 요청 없으면 X
				 */
				result = authenticationService.registerV2(request).getToken();

			}
		}

		return result;
	}

}
