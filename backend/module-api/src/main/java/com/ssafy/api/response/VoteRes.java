package com.ssafy.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.entity.rdbms.Vote;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 청중 투표 결과 조회 API ([GET] /api/v1/vote/debates/{:debateId}) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("VoteResponse")
public class VoteRes {
    @ApiModelProperty(name="토론 Id")
    @JsonProperty("debate_id")
    Long debateId;

    @ApiModelProperty(name="토론 제목")
    @JsonProperty("debate_title")
    String debateTitle;

    @ApiModelProperty(name="mvp Id")
    @JsonProperty("mvp_email")
    String mvpEmail;

    @ApiModelProperty(name="mvp 이름")
    @JsonProperty("mvp_name")
    String mvpName;


    @ApiModelProperty(name="청중 투표 결과 리스트")
    @JsonProperty("perspective_list")
    List<VotePerspectiveRes> votePerspectiveResList;

    public static VoteRes of(List<Vote> voteList, Long debateId){
        VoteRes voteRes = new VoteRes();
        voteRes.setDebateId(debateId);
        voteRes.setDebateTitle(voteList.get(0).getDebate().getTitle());

        Map<String, String> mvpNameMap = new HashMap<>();
        Map<String, Integer> mvpCountMap = new HashMap<>();
        Map<String, Integer> perspectiveCountMap = new HashMap<>();

        for(Vote vote : voteList){
            System.out.println(vote.getMvpUser());
            String mvpName = vote.getMvpUser().getName();
            String mvpEmail = vote.getMvpUser().getUserEmail();
            String perspectiveName = vote.getPerspective().getName();

            if(mvpCountMap.get(mvpEmail) == null) {
                mvpCountMap.put(mvpEmail, 1);
                mvpNameMap.put(mvpEmail, mvpName);
            }
            else {
                mvpCountMap.put(mvpEmail, mvpCountMap.get(mvpEmail) + 1);
            }
            if(perspectiveCountMap.get(perspectiveName) == null)
                perspectiveCountMap.put(perspectiveName, 1);
            else
                perspectiveCountMap.put(perspectiveName, perspectiveCountMap.get(perspectiveName)+1);
        }


        int mvpCountMax = -1;
        for(String email : mvpCountMap.keySet()){
            if(mvpCountMax >= mvpCountMap.get(email)) continue;
            mvpCountMax = mvpCountMap.get(email);
            voteRes.setMvpEmail(email);
            voteRes.setMvpName(mvpNameMap.get(email));
        }

        int voteCount = voteList.size();
        List<VotePerspectiveRes> votePerspectiveResList = new ArrayList<>();
        for(String perspectiveName : perspectiveCountMap.keySet()){
            VotePerspectiveRes votePerspectiveRes = new VotePerspectiveRes();
            votePerspectiveRes.setPerspectiveName(perspectiveName);
            votePerspectiveRes.setPercent((float)perspectiveCountMap.get(perspectiveName)/voteCount);
            votePerspectiveResList.add(votePerspectiveRes);
        }
        voteRes.setVotePerspectiveResList(votePerspectiveResList);
        return voteRes;
    }
}
