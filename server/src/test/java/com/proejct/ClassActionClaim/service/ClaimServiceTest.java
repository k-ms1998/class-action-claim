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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ClaimServiceTest {

    @Autowired
    private ClaimService claimService;

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void givenClaimRequest_whenExecutingSaveClaims_thenSuccess() {
        // Given
        Lecture lecture = lectureRepository.save(new Lecture("001", "Lecture A", "Kim"));

        Student student = studentRepository.save(Student.of("StudentA", "passwordA", "test@sju.ac.kr"));

        Claim claim = new Claim("Claim Midterm", "Content Midterm", ClaimType.MIDTERM, lecture, student);

        // When
        ClaimRequestDTO claimRequestDTO = new ClaimRequestDTO(claim.getTitle(), claim.getContent(), "MIDTERM", lecture.getId(), student.getId());
        ToClientResponse<ClaimResponseDTO> result = claimService.saveClaims(claimRequestDTO);

        // Then
        /**
         * Check returned data
         */
        String message = result.getMessage();
        Assertions.assertThat(message).isEqualTo("Claim Saved.");

        Integer count = result.getCount();
        Assertions.assertThat(count).isEqualTo(1);

        ClaimResponseDTO data = result.getData();
        Assertions.assertThat(data.getTitle()).isEqualTo("Claim Midterm");

        /**
         * Check if the claim is saved
         */
        List<Claim> all = claimRepository.findAll();
        Assertions.assertThat(all).hasSize(1);

        Claim first = all.get(0);
        Assertions.assertThat(List.of(first.getTitle(), first.getContent(), first.getUpVotes(),
                        first.getClaimType(), first.getLecture().getId(), first.getStudent().getId()))
                .containsExactly(claim.getTitle(), claim.getContent(), claim.getUpVotes(), claim.getClaimType(), lecture.getId(), student.getId());

    }

    @Test
    void givenClaimRequest_whenExecutingGetClaims_thenSuccess() {
        // Given
        Lecture lectureA = lectureRepository.save(new Lecture("001", "Lecture A", "Kim"));
        Lecture lectureB = lectureRepository.save(new Lecture("002", "Lecture B", "Lee"));

        Student student = studentRepository.save(Student.of("StudentA", "passwordA", "test@sju.ac.kr"));

        Claim claimAA = claimRepository.save(new Claim("Claim AA", "Content AA", ClaimType.MIDTERM, lectureA, student));
        Claim claimAB = claimRepository.save(new Claim("Claim AB", "Content AB", ClaimType.MIDTERM, lectureA, student));
        Claim claimAC = claimRepository.save(new Claim("Claim AC", "Content AC", ClaimType.MIDTERM, lectureA, student));

        Claim claimAD = claimRepository.save(new Claim("Claim AD", "Content AD", ClaimType.FINAL, lectureA, student));
        Claim claimAE = claimRepository.save(new Claim("Claim AE", "Content AE", ClaimType.FINAL, lectureA, student));

        Claim claimBA = claimRepository.save(new Claim("Claim BA", "Content BA", ClaimType.MIDTERM, lectureB, student));


        ClaimRequestDTO requestA = new ClaimRequestDTO(null, null, "MIDTERM", lectureA.getId(), student.getId());
        ClaimRequestDTO requestB = new ClaimRequestDTO(null, null, "FINAL", lectureA.getId(), student.getId());
        ClaimRequestDTO requestC = new ClaimRequestDTO(null, null, "MIDTERM", lectureB.getId(), student.getId());

        // When
        ToClientResponse<List<ClaimResponseDTO>> resultA = claimService.getClaims(requestA);
        ToClientResponse<List<ClaimResponseDTO>> resultB = claimService.getClaims(requestB);
        ToClientResponse<List<ClaimResponseDTO>> resultC = claimService.getClaims(requestC);

        // Then
        /**
         * result A
         */
        String messageA = resultA.getMessage();
        Assertions.assertThat(messageA).isEqualTo("Fetched Claims.");

        Integer countA = resultA.getCount();
        Assertions.assertThat(countA).isEqualTo(3);

        List<ClaimResponseDTO> dataA = resultA.getData();
        Assertions.assertThat(dataA.stream().map(e -> e.getTitle())).contains("Claim AA", "Claim AB", "Claim AC");

        /**
         * result B
         */
        String messageB = resultB.getMessage();
        Assertions.assertThat(messageB).isEqualTo("Fetched Claims.");

        Integer countB = resultB.getCount();
        Assertions.assertThat(countB).isEqualTo(2);

        List<ClaimResponseDTO> dataB = resultB.getData();
        Assertions.assertThat(dataB.stream().map(e -> e.getTitle())).contains("Claim AD", "Claim AE");

        /**
         * result C
         */
        String messageC = resultC.getMessage();
        Assertions.assertThat(messageC).isEqualTo("Fetched Claims.");

        Integer countC = resultC.getCount();
        Assertions.assertThat(countC).isEqualTo(1);

        List<ClaimResponseDTO> dataC = resultC.getData();
        Assertions.assertThat(dataC.stream().map(e -> e.getTitle())).contains("Claim BA");


    }

}