package com.ssafy.api.service;

import com.ssafy.api.request.UserModifyPasswordReq;
import com.ssafy.api.request.UserModifyPatchReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.request.UserReissuePostReq;
import com.ssafy.api.response.UserAuthPostRes;
import com.ssafy.common.auth.TokenInfo;
import com.ssafy.common.util.JwtTokenUtil;
import com.ssafy.entity.rdbms.Role;
import com.ssafy.entity.rdbms.User;
import com.ssafy.entity.redis.RefreshToken;
import com.ssafy.repository.RedisRepository;
import com.ssafy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

/**
 * 유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JwtTokenUtil jwtTokenUtil;

    private final RedisRepository redisRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserRegisterPostReq userRegisterInfo) {
        
        User user = User.builder()
                .userEmail(userRegisterInfo.getEmail())
                // 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
                .password(passwordEncoder.encode(userRegisterInfo.getPassword()))
                .name(userRegisterInfo.getName())
                .position(userRegisterInfo.getPosition())
                .grade(userRegisterInfo.getGrade())
                .classNum(userRegisterInfo.getClassNum())
                .department(userRegisterInfo.getDepartment())
                .role(Role.ROLE_USER)
                .build();
        try{
            userRepository.save(user);
        }catch (DuplicateKeyException e){
            throw new DuplicateKeyException("중복되는 아이디가 존재합니다.");
        }
        return user;
    }

    @Override
    public TokenInfo login(User user) {
        String accessToken = jwtTokenUtil.createAccessToken(user.getUserEmail());
        String refreshToken = jwtTokenUtil.createRefreshToken(user.getUserEmail());

        TokenInfo tokenInfo = jwtTokenUtil.generateToken(user.getUserEmail(), accessToken, refreshToken);

        // redis에 저장
        redisRepository.save(new RefreshToken(user.getUserEmail(), refreshToken, tokenInfo.getRefreshTokenExpirationTime()));

        return tokenInfo;
    }

    @Override
    public ResponseEntity<?> reissue(UserReissuePostReq userReissuePostReq) {
        if(!jwtTokenUtil.validateToken(userReissuePostReq.getRefreshToken())){
            return ResponseEntity.status(401).body(UserAuthPostRes.of(401, "Refresh Token 정보가 유효하지 않습니다.",null));
        }
        Authentication authentication = jwtTokenUtil.getAuthentication(userReissuePostReq.getRefreshToken());
        if(!redisRepository.findById(authentication.getName()).get().getRefreshToken().equals(userReissuePostReq.getRefreshToken())){
            return ResponseEntity.status(404).body(UserAuthPostRes.of(404, "RefreshToken 정보가 잘못되었습니다..",null));
        }
        String accessToken = jwtTokenUtil.createAccessToken(authentication.getName());
        String refreshToken = jwtTokenUtil.createRefreshToken(authentication.getName());
        TokenInfo tokenInfo = jwtTokenUtil.generateToken(authentication.getName(), accessToken, refreshToken);
        redisRepository.save(new RefreshToken(authentication.getName(), refreshToken ,tokenInfo.getRefreshTokenExpirationTime()));

        return ResponseEntity.ok(UserAuthPostRes.of(200, "Token 정보가 갱신되었습니다.", tokenInfo));
    }

    @Override
    public User getUserByUserEmail(String userEmail) {
        // 디비에 유저 정보 조회 (userId 를 통한 조회)
        return userRepository.findByUserEmail(userEmail).orElseThrow(NoSuchElementException::new);

    }


    @Override
    public void updateUser(String userEmail, UserModifyPatchReq req) {
        User user = userRepository.findByUserEmail(userEmail).orElseThrow(NoSuchElementException::new);
        user.update(req.getDepartment(), req.getGrade(), req.getClassNum());
        userRepository.save(user);
    }

    @Override
    public void modifyUserPassword(String userEmail, UserModifyPasswordReq req) {
        User user = userRepository.findByUserEmail(userEmail).orElseThrow(NoSuchElementException::new);
        if(!passwordEncoder.matches(req.getNowPassword(), user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void modifyUserPasswordByEmail(String userEmail, String password) {
        User user = userRepository.findByUserEmail(userEmail).orElseThrow(NoSuchElementException::new);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(String userEmail) {
        User user = userRepository.findByUserEmail(userEmail).orElseThrow(NoSuchElementException::new);
        userRepository.delete(user);
    }

    @Override
    public boolean checkExist(String userEmail) {
        User user = userRepository.findByUserEmail(userEmail).orElseThrow(NoSuchElementException::new);
        if(user != null){
            return true;
        }
        return false;
    }


}
