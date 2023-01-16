package com.proejct.ClassActionClaim.dto.RequestBody;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailLinkRequest {

    private String uuid;
    private String email;
    private LocalDateTime requestTime;

    public EmailLinkRequest of(String uuid, String email, LocalDateTime requestTime) {
        return new EmailLinkRequest(uuid, email, requestTime);
    }
}
