package com.proejct.ClassActionClaim.dto.ResponseBody;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotesResponseDTO {

    String title;
    String content;

    public NotesResponseDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
