package com.likjelion.besession.week09_domain.auth.service;

import com.likjelion.besession.global.exception.CustomException;
import com.likjelion.besession.global.sequrity.CustomUserDetails;
import com.likjelion.besession.global.sequrity.CustomUserDetailsService;
import com.likjelion.besession.global.sequrity.JwtProvider;
import com.likjelion.besession.week09_domain.auth.dto.request.LoginRequest;
import com.likjelion.besession.week09_domain.auth.dto.request.SignUpRequest;
import com.likjelion.besession.week09_domain.auth.dto.request.TokenRequest;
import com.likjelion.besession.week09_domain.auth.dto.response.LoginResponse;
import com.likjelion.besession.week09_domain.auth.dto.response.LogoutResponse;
import com.likjelion.besession.week09_domain.auth.dto.response.SignUpResponse;
import com.likjelion.besession.week09_domain.auth.dto.response.TokenResponse;
import com.likjelion.besession.week09_domain.auth.entity.RefreshToken;
import com.likjelion.besession.week09_domain.auth.exception.AuthErrorCode;
import com.likjelion.besession.week09_domain.auth.repository.RefreshTokenRepository;
import com.likjelion.besession.week09_domain.user.entity.User;
import com.likjelion.besession.week09_domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AuthService {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    //회원가입
    @Transactional
    public SignUpResponse signup(SignUpRequest signUpRequest) {

        //기존에 가입된 이메일이라면
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new CustomException(AuthErrorCode.DUPLICATE_EMAIL);
        }

        //User 생성
        User user = User.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .name(signUpRequest.getName())
                .gender(signUpRequest.getGender())
                .img(signUpRequest.getImg())
                .build();

        //User 저장
        User savedUser = userRepository.save(user);

        //ResponseDTO 생성
        return SignUpResponse.builder()
                .userId(savedUser.getId())
                .gender(savedUser.getGender())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .build();
    }

    //로그인
    public LoginResponse login(LoginRequest request) {
        //가입된 이메일인지 확인
        User findUser = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(AuthErrorCode.NOT_FOUND_EMAIL));

        //비밀번호가 일치하는지 확인
        if(!passwordEncoder.matches(request.getPassword(), findUser.getPassword())) {
            throw new CustomException(AuthErrorCode.OUTMATCH_PASSWORD);
        }

        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(request.getEmail());

        //token 발급
        String accessToken = jwtProvider.createAccessToken(userDetails);
        String refreshToken = jwtProvider.createRefreshToken(userDetails);

        //LoginResponse 변환
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    //로그아웃 ( user가 가지고 있는 refreshToken을 삭제 )
    @Transactional
    public LogoutResponse logout(CustomUserDetails customUserDetails) {
        //user 조회
        User user = customUserDetails.getUser();

        //db에서 해당 user의 refreshToken 조회
        RefreshToken refreshToken = refreshTokenRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(AuthErrorCode.NOT_FOUNT_REFRESH_TOKEN));

        //token 삭제
        refreshTokenRepository.delete(refreshToken);

        return LogoutResponse.builder()
                .message("로그아웃 성공")
                .build();
    }

    //토큰 갱신(리프레쉬 토큰이 유효한지 검사 후 새로운 엑세스 토큰 생성 후 반환)
    @Transactional
    public TokenResponse refresh(CustomUserDetails customUserDetails, TokenRequest tokenRequest) {

        //tokenRequest가 유효한지 검사
        if(!jwtProvider.validateToken(tokenRequest.getRefreshToken())) {
            throw new CustomException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        //해당 user 조회
        User user = customUserDetails.getUser();

        //user의 유효한 refreshToken 조회
        RefreshToken refreshToken = refreshTokenRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(AuthErrorCode.NOT_FOUNT_REFRESH_TOKEN));

        //조회한 token과 넘어온 토큰이 일치하는지 비교
        if(!refreshToken.getToken().equals(tokenRequest.getRefreshToken())) {
            throw new CustomException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        //새로운 accessToken 발급
        String accessToken = jwtProvider.createAccessToken(customUserDetails);

        //응답 변환
        return TokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
