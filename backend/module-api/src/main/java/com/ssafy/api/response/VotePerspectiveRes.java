package com.ssafy.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("VotePerspectiveRes")
public class VotePerspectiveRes {
    @ApiModelProperty(name="Perspective Name")

    @JsonProperty("perspective_name")
    String perspectiveName;

    @ApiModelProperty(name="percent")
    Float percent;
}
