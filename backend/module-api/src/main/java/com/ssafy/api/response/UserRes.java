package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.entity.rdbms.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserResponse")
public class UserRes extends BaseResponseBody {
	@ApiModelProperty(name="User Email")
	String userEmail;

	@ApiModelProperty(name="User 직위")
	String position;

	@ApiModelProperty(name="User 부서")
	String department;

	@ApiModelProperty(name="User 부서")
	int grade;

	@ApiModelProperty(name="User 부서")
	int classNum;

	@ApiModelProperty(name="User 이름")
	String name;

	@ApiModelProperty(name="User 프로필 Url")
	String profileUrl;


	public static UserRes of(User user) {
		UserRes res = new UserRes();
		res.setStatusCode(200);
		res.setMessage("Success");
		res.setUserEmail(user.getUserEmail());
		res.setPosition(user.getPosition());
		res.setGrade(user.getGrade());
		res.setClassNum(user.getClassNum());
		res.setDepartment(user.getDepartment());
		res.setName(user.getName());
		return res;
	}
}
