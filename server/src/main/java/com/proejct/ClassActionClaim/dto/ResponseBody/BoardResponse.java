package com.proejct.ClassActionClaim.dto.ResponseBody;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardResponse {

    String title;
    String content;

    public static BoardResponse of(String title, String content) {
        return new BoardResponse(title, content);
    }
}
