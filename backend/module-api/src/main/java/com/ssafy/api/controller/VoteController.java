package com.ssafy.api.controller;

import com.ssafy.api.request.DebateRegisterPostReq;
import com.ssafy.api.request.VoteRegisterPostReq;
import com.ssafy.api.response.EvaluationRes;
import com.ssafy.api.response.VoteRes;
import com.ssafy.api.service.UserService;
import com.ssafy.api.service.VoteService;
import com.ssafy.common.auth.CustomUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.entity.rdbms.Debate;
import com.ssafy.entity.rdbms.Vote;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 청중 투표 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "투표 API", tags = {"Debate"})
@RestController
@RequestMapping("/api/v1/vote")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    private final UserService userService;

    @PostMapping()
    @ApiOperation(value = "청중 투표 생성")
    public ResponseEntity<? extends BaseResponseBody> register(@ApiIgnore Authentication authentication,
                                                               @RequestBody @ApiParam(value="청중 투표 정보", required = true) VoteRegisterPostReq voteRegisterPostReq) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();

        Vote vote = voteService.createVote(voteRegisterPostReq, userId);
        return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
    }

    @DeleteMapping("/{voteId}")
    @ApiOperation(value = "청중 투표 삭제", notes="청중 투표 내용(1 row)를 삭제한다.")
    public ResponseEntity<? extends BaseResponseBody> delete (
            @PathVariable Long voteId){

        voteService.deleteVote(voteId);
        return ResponseEntity.status(204).body(BaseResponseBody.of(204, "Success"));
    }

    @GetMapping("/debates/{debateId}")
    @ApiOperation(value="청중 평가 결과 취합 조회")
    public ResponseEntity<?> getVotes(@PathVariable Long debateId){
        List<Vote> voteList = voteService.getVoteByDebateId(debateId);
        if(voteList.isEmpty())
            return ResponseEntity.status(204).body(BaseResponseBody.of(204, "Success"));

        VoteRes voteRes = VoteRes.of(voteList, debateId);

        return ResponseEntity.status(200).body(voteRes);
    }

}
