package com.proejct.ClassActionClaim.dto.ResponseBody;

import lombok.Getter;

@Getter
public class ClaimResponseDTO {

    Long id;
    String title;
    String content;
    Integer upVotes;

    public ClaimResponseDTO(Long id, String title, String content, Integer upVotes) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.upVotes = upVotes;
    }
}
