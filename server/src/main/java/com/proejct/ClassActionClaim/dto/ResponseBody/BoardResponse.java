package com.proejct.ClassActionClaim.dto.ResponseBody;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardResponse {

    String title;
    String content;

    public BoardResponse(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
