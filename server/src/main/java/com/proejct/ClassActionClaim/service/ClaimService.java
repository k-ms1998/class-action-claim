package com.proejct.ClassActionClaim.service;

import com.proejct.ClassActionClaim.domain.Claim;
import com.proejct.ClassActionClaim.domain.Lecture;
import com.proejct.ClassActionClaim.domain.enums.ClaimType;
import com.proejct.ClassActionClaim.dto.RequestBody.ClaimRequestDTO;
import com.proejct.ClassActionClaim.dto.ResponseBody.ClaimResponseDTO;
import com.proejct.ClassActionClaim.dto.ResponseBody.ToClientResponse;
import com.proejct.ClassActionClaim.repository.ClaimRepository;
import com.proejct.ClassActionClaim.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final LectureRepository lectureRepository;
    
    public ToClientResponse<List<ClaimResponseDTO>> getClaims(ClaimRequestDTO claimRequestDTO) {
        /**
         * 강의 별로 중간, 기말, 과제에 따라 모든 Claim 반환
         */
        ClaimType claimType = claimRequestDTO.getClaimType();
        Long lectureId = claimRequestDTO.getLectureId();

        /**
         * Get Lecture
         */
        Optional<Lecture> lectureOptional = lectureRepository.findById(lectureId);
        if (lectureOptional == null) {
            return new ToClientResponse("Couldn't find lecture.", 0, null);
        }

        Lecture lecture = lectureOptional.get();

        /**
         * Get All Claims by Lecture and ClaimType; Order By upVotes desc
         */
        List<Claim> byLectureAndClaimType = claimRepository.findByLectureAndClaimType(lecture, claimType);
        Integer count = byLectureAndClaimType.size();

        List<ClaimResponseDTO> data = byLectureAndClaimType
                .stream().map(c -> {
                    Long id = c.getId();
                    String title = c.getTitle();
                    String content = c.getContent();
                    Integer upVotes = c.getUpVotes();

                    return new ClaimResponseDTO(id, title, content, upVotes);
                }).collect(Collectors.toList());

        return new ToClientResponse<List<ClaimResponseDTO>>("Fetched Claims.", count, data);
    }
}
