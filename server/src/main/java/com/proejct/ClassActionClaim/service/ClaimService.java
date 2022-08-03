package com.proejct.ClassActionClaim.service;

import com.proejct.ClassActionClaim.domain.Claim;
import com.proejct.ClassActionClaim.domain.Lecture;
import com.proejct.ClassActionClaim.domain.Student;
import com.proejct.ClassActionClaim.domain.enums.ClaimType;
import com.proejct.ClassActionClaim.dto.RequestBody.ClaimRequestDTO;
import com.proejct.ClassActionClaim.dto.ResponseBody.ClaimResponseDTO;
import com.proejct.ClassActionClaim.dto.ResponseBody.ToClientResponse;
import com.proejct.ClassActionClaim.repository.ClaimRepository;
import com.proejct.ClassActionClaim.repository.LectureRepository;
import com.proejct.ClassActionClaim.repository.StudentRepository;
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
    private final StudentRepository studentRepository;

    public ToClientResponse<List<ClaimResponseDTO>> getClaims(ClaimRequestDTO claimRequestDTO) {
        /**
         * 강의 별로 중간, 기말, 과제에 따라 모든 Claim 반환
         */
        ClaimType claimType = claimRequestDTO.getClaimType();
        Long lectureId = claimRequestDTO.getLectureId();

        /**
         * Get Lecture
         */
        Lecture lecture = findAndCheckLectureId(lectureId);
        if (lecture == null) {
            return new ToClientResponse("Couldn't find lecture.", 0, null);
        }

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

    public ToClientResponse<ClaimResponseDTO> saveClaims(ClaimRequestDTO claimRequestDTO) {
        String title = claimRequestDTO.getTitle();
        String content = claimRequestDTO.getContent();
        ClaimType claimType = claimRequestDTO.getClaimType();

        Long lectureId = claimRequestDTO.getLectureId();
        Long studentId = claimRequestDTO.getStudentId();

        Lecture lecture = findAndCheckLectureId(lectureId);
        if (lecture == null) {
            return new ToClientResponse("Couldn't find lecture.", 0, null);
        }

        Student student = findAndCheckLStudentId(studentId);
        if (student == null) {
            return new ToClientResponse("Couldn't find student.", 0, null);
        }

        Claim claim = claimRepository.save(new Claim(title, content, claimType, lecture, student));
        ClaimResponseDTO claimResponseDTO = claimToClaimResponseDto(claim);

        return new ToClientResponse<>("Claim Saved.", 1, claimResponseDTO);
    }

    private Lecture findAndCheckLectureId(Long lectureId) {
        Optional<Lecture> lectureOptional = lectureRepository.findById(lectureId);
        if (lectureOptional == null) {
            return null;
        }

        return lectureOptional.get();
    }

    private Student findAndCheckLStudentId(Long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional == null) {
            return null;
        }

        return studentOptional.get();
    }

    private ClaimResponseDTO claimToClaimResponseDto(Claim claim) {
        return new ClaimResponseDTO(claim.getId(), claim.getTitle(), claim.getContent(), claim.getUpVotes());
    }
}
