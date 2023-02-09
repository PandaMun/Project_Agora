package com.ssafy.api.controller;

import com.ssafy.api.request.*;
import com.ssafy.api.service.FileService;
import com.ssafy.api.service.MailService;
import com.ssafy.api.service.UserFileManagerService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.entity.rdbms.FileManager;
import com.ssafy.entity.rdbms.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.CustomUserDetails;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
	

	private final UserService userService;

	private final UserFileManagerService userFileManagerService;

	private final MailService mailService;


	@PostMapping()
	@ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseBody> register(
			@RequestBody @ApiParam(value="회원가입 정보", required = true) UserRegisterPostReq registerInfo) {
		
		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.

		try {
			User user = userService.createUser(registerInfo);
		}catch (DuplicateKeyException e) {
			return ResponseEntity.status(500).body(BaseResponseBody.of(500,e.getMessage()));
		}
		return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
	}
	
	@GetMapping("/me")
	@ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<UserRes> getUserInfo(@ApiIgnore Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		String userId = userDetails.getUsername();
		User user = userService.getUserByUserEmail(userId);
		UserRes userRes = new UserRes().of(user);
		if(user.getFileManager() != null) {
			userRes.setProfileUrl((userFileManagerService.getProfileUrl(user.getFileManager())).getSavedPath());
		}
		System.out.println(userRes.getProfileUrl());
		return ResponseEntity.status(200).body(userRes);
	}

	@GetMapping("/{userId}")
	@ApiOperation(value = "회원 ID 중복 조회", notes = "회원 가입시 ID 중복 확인한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<BaseResponseBody> checkUserInfo(@PathVariable String userId) {

		if(userService.checkExist(userId)) {
			return ResponseEntity.status(409).body(BaseResponseBody.of(409, "이미 존재하는 사용자 ID 입니다."));
		}
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/info")
	@ApiOperation(value = "회원 본인 정보 수정", notes = "로그인한 회원 본인의 정보를 수정한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<?> modifyUserInfo(@ApiIgnore Authentication authentication, @RequestBody UserModifyPatchReq req) {

		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		if (!req.getUserEmail().equals(userDetails.getUsername())) {
			return ResponseEntity.status(409).body(BaseResponseBody.of(409,"인증에 실패하셨습니다."));
		}
		try {
			userService.updateUser(userDetails.getUsername(), req);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(404).body(BaseResponseBody.of(404,"사용자가 존재하지 않습니다."));
		}catch (Exception e){
			return ResponseEntity.status(500).body(BaseResponseBody.of(500,"서버에 문제가 발생했습니다."));
		}
		return ResponseEntity.status(200).body(BaseResponseBody.of(200,"Success"));
	}

	@PatchMapping("/profile")
	@ApiOperation(value = "회원 본인 프로필 수정", notes = "로그인한 회원 본인의 프로필 이미지를 수정한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<?> modifyUserProfile(@ApiIgnore Authentication authentication, MultipartFile file) {

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		try {
			FileManager fileManager = userFileManagerService.getFileManager(userDetails.getUsername());
			userFileManagerService.saveFile(file, fileManager);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(401).body(BaseResponseBody.of(401,"인증에 실패하셨습니다."));
		}catch (IOException e){
			return ResponseEntity.status(500).body(BaseResponseBody.of(500,"파일 저장에 실패하셨습니다."));
		}
		return ResponseEntity.status(200).body(BaseResponseBody.of(200,"파일 저장에 성공하셨습니다."));
	}

	@PatchMapping("/password")
	@ApiOperation(value = "회원 비밀번호 수정", notes = "로그인한 회원의 비밀번호 정보를 수정한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<?> modifyUserPassword(@ApiIgnore Authentication authentication,
											@RequestBody UserModifyPasswordReq req) {

		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		try{
			userService.modifyUserPassword(userDetails.getUsername(), req);
		}catch (IllegalArgumentException e) {
			return ResponseEntity.status(409).body(BaseResponseBody.of(409,"비밀번호 인증에 실패하셨습니다."));
		}catch (NoSuchElementException e){
			return ResponseEntity.status(404).body(BaseResponseBody.of(404,"사용자가 존재하지 않습니다."));
		}catch (Exception e) {
			return ResponseEntity.status(500).body(BaseResponseBody.of(500,"서버에 문제가 발생했습니다."));
		}

		return ResponseEntity.status(200).body(BaseResponseBody.of(200,"비밀번호 변경에 성공하셨습니다."));
	}
	@PatchMapping("/email/password")
	@ApiOperation(value = "이메일 링크를 통한 비밀번호 수정", notes = "인증 받은 이메일 링크를 통해 회원의 비밀번호 정보를 수정한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<?> modifyUserPasswordByEmail(@ApiIgnore Authentication authentication,
													   @RequestBody EmailAuthPasswordReq emailAuthPasswordReq) {

		if(emailAuthPasswordReq.getPassword() == null) {
			return ResponseEntity.status(404).body(BaseResponseBody.of(401,"비밀번호가 올바르지 않습니다."));
		}
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		try{
			userService.modifyUserPasswordByEmail(userDetails.getUsername(), emailAuthPasswordReq.getPassword());
		}catch (NoSuchElementException e){
			return ResponseEntity.status(404).body(BaseResponseBody.of(404,"사용자가 존재하지 않습니다."));
		}catch (Exception e) {
			return ResponseEntity.status(500).body(BaseResponseBody.of(500,"서버에 문제가 발생했습니다."));
		}

		return ResponseEntity.status(200).body(BaseResponseBody.of(200,"비밀번호 변경에 성공하셨습니다."));
	}

	@PostMapping("/email")
	@ApiOperation(value = "이메일 인증", notes = "이메일 인증")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
public ResponseEntity<BaseResponseBody> checkEmail(@RequestBody UserEmailReq userEmailReq) {

		if(userEmailReq.getUserEmail() == null){
			return ResponseEntity.status(401).body(BaseResponseBody.of(401,"잘못된 요청입니다."));
		}
		try{
			MailDto mailDto = mailService.makeLinkMail(userEmailReq);
			mailService.sendMail(mailDto);
		}catch (NoSuchElementException e){
			return ResponseEntity.status(401).body(BaseResponseBody.of(401,"해당 메일을 가진 사용자가 존재하지 않습니다."));
		}catch (Exception e){
			return ResponseEntity.status(404).body(BaseResponseBody.of(404,"메일전송에 실패"));
		}
		return ResponseEntity.status(200).body(BaseResponseBody.of(200,"메일전송에 성공하셨습니다."));
	}

	@DeleteMapping("/{userId}")
	@ApiOperation(value = "회원 본인 탈퇴", notes = "로그인한 회원 본인을 탈퇴한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<BaseResponseBody> deleteUser(@ApiIgnore Authentication authentication,
													   @PathVariable String userId) {

		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		if (!userId.equals(userDetails.getUsername())) {
			return ResponseEntity.badRequest().build();
		}

		userService.deleteUser(userId);
		return ResponseEntity.status(204).body(BaseResponseBody.of(204,"Success"));
	}




}
