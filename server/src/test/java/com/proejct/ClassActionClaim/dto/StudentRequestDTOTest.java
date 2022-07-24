package com.proejct.ClassActionClaim.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class StudentRequestDTOTest {

    @ParameterizedTest
    @MethodSource
    void givenNull_whenCreatingStudentDTO_thenReturnError(String username, String password, String email, String expected) {
        // Given
        StudentRequestDTO studentRequestDTO = StudentRequestDTO.of(username, password, email);

        // When & Then
        System.out.println("studentRequestDTO = " + studentRequestDTO);
    }

    static Stream<Arguments> givenNull_whenCreatingStudentDTO_thenReturnError() {
        return Stream.of(
                Arguments.arguments(null, null, null, null)
        );
    }

}