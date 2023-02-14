package com.ssafy.api.controller;

import com.ssafy.api.request.UserReissuePostReq;
import com.ssafy.api.response.UserAuthPostRes;
import com.ssafy.common.auth.CustomUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.entity.rdbms.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ssafy.api.request.UserLoginPostReq;
import com.ssafy.api.service.UserService;
import com.ssafy.common.util.JwtTokenUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import com.ssafy.repository.RedisRepository;
import springfox.documentation.annotations.ApiIgnore;

import java.util.NoSuchElementException;

/**
 * 인증 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "인증 API", tags = {"Auth."})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final UserService userService;

	private final JwtTokenUtil jwtTokenUtil;

	private final PasswordEncoder passwordEncoder;

	private final RedisRepository redisRepository;

	@PostMapping("/login")
	@ApiOperation(value = "로그인", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공", response = UserAuthPostRes.class),
        @ApiResponse(code = 401, message = "인증 실패", response = UserAuthPostRes.class),
        @ApiResponse(code = 404, message = "사용자 없음", response = UserAuthPostRes.class),
        @ApiResponse(code = 500, message = "서버 오류", response = UserAuthPostRes.class)
    })
	public ResponseEntity<UserAuthPostRes> login(@RequestBody @ApiParam(value="로그인 정보", required = true) UserLoginPostReq loginInfo) {
		String userId = loginInfo.getId();
		String password = loginInfo.getPassword();
		try {
			User user = userService.getUserByUserEmail(userId);
			// 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
			if (!passwordEncoder.matches(password, user.getPassword())) {
				// 유효하지 않는 패스워드인 경우, 로그인 실패로 응답.
				return ResponseEntity.status(401).body(new UserAuthPostRes().of(401, "Invalid Password", null));
			}
			// 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
			return ResponseEntity.ok(new UserAuthPostRes().of(200, "Success", userService.login(user)));
		} catch (NoSuchElementException e){
			// 로그인 시도하려는 회원이 존재하지 않는 경우
			return ResponseEntity.status(404).body(new UserAuthPostRes().of(404, "존재하지 않는 회원입니다.", null));
		}
	}

	@PostMapping("/reissue")
	@ApiOperation(value = "토큰 재발급", notes = "<strong>AccessToken,RefreshToken</strong>을 받아 재발급 합니다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = UserAuthPostRes.class),
			@ApiResponse(code = 401, message = "RefreshToken 유효하지 않음", response = UserAuthPostRes.class),
			@ApiResponse(code = 404, message = "RefreshToken 정보가 잘못되었음", response = UserAuthPostRes.class),
			@ApiResponse(code = 500, message = "서버 오류", response = UserAuthPostRes.class)
	})
	public ResponseEntity<?> reissue(@RequestBody UserReissuePostReq userReissuePostReq){
		return userService.reissue(userReissuePostReq);
	}
	@GetMapping("/auth")
	@ApiOperation(value = "로그아웃", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = UserAuthPostRes.class),
			@ApiResponse(code = 401, message = "인증 실패", response = UserAuthPostRes.class),
			@ApiResponse(code = 404, message = "사용자 없음", response = UserAuthPostRes.class),
			@ApiResponse(code = 500, message = "서버 오류", response = UserAuthPostRes.class)
	})
	public ResponseEntity<?> logout(@ApiIgnore Authentication authentication) {

		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		String userId = userDetails.getUsername();
		if(userId != null){
			redisRepository.deleteById(userDetails.getUsername());
			return ResponseEntity.ok(BaseResponseBody.of(200,"Success"));
		}
		return ResponseEntity.status(404).body(BaseResponseBody.of(404,"로그아웃이 실패하였습니다."));

	}



}
