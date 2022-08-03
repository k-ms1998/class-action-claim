package com.proejct.ClassActionClaim.dto.RequestBody;

import com.proejct.ClassActionClaim.domain.enums.ClaimType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimRequestDTO {

    String title;
    String content;
    ClaimType claimType;
    Long lectureId;
    Long studentId;

    public ClaimRequestDTO(String title, String content, String claimType, Long lectureId, Long studentId) {
        this.title = title;
        this.content = content;
        this.claimType = convertStringToClaimType(claimType);
        this.lectureId = lectureId;
        this.studentId = studentId;
    }

    private ClaimType convertStringToClaimType(String strClaim) {
        if (strClaim.equals("MIDTERM")) {
            return ClaimType.MIDTERM;
        } else if (strClaim.equals("FINAL")) {
            return ClaimType.FINAL;
        } else if (strClaim.equals("ASSIGNMENT")) {
            return ClaimType.ASSIGNMENT;
        } else {
            return null;
        }
    }
}
