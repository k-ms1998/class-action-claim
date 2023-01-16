package com.proejct.ClassActionClaim.repository;

import com.proejct.ClassActionClaim.domain.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

@DataJpaTest
@DisplayName("[StudentRepositoryTest]")
class StudentRepositoryTest {

    private final StudentRepository sut;

    public StudentRepositoryTest(@Autowired StudentRepository sut) {
        this.sut = sut;
    }

    @Test
    @DisplayName("Creating Student - Success")
    void givenStudent_whenCreatingNewStudent_thenReturnsNothing() throws Exception {
        // Given
        String uuid = UUID.randomUUID().toString();
        String email = "student@sju.ac.kr";
        Student student = createStudent(uuid, email);

        // When
        Student saved = sut.save(student);

        // Then
        Assertions.assertThat(saved)
                .hasFieldOrPropertyWithValue("uuid", uuid)
                .hasFieldOrPropertyWithValue("email", email);
    }

    @Test
    @DisplayName("Finding Existing Student by Email - Success")
    void givenSavedStudent_whenFindingByEmail_thenReturnsSavedStudent() throws Exception {
        // Given
        String uuid = UUID.randomUUID().toString();
        String email = "student@sju.ac.kr";
        Student student = createStudent(uuid, email);
        Student saved = sut.save(student);

        // When
        Student byEmail = sut.findByEmail(email);

        // Then
        Assertions.assertThat(byEmail)
                .hasFieldOrPropertyWithValue("uuid", uuid)
                .hasFieldOrPropertyWithValue("email", email);
    }

    @Test
    @DisplayName("Finding Non-Existing Student by Email - Success")
    void givenUnSavedStudent_whenFindingByEmail_thenReturnsSavedStudent() throws Exception {
        // Given
        String email = "student@sju.ac.kr";

        // When
        Student byEmail = sut.findByEmail(email);

        // Then
        Assertions.assertThat(byEmail).isNull();
    }

    private static Student createStudent(String uuid, String email) {
        return Student.of(
                uuid,
                "kms",
                "password",
                email,
                false
        );
    }
}