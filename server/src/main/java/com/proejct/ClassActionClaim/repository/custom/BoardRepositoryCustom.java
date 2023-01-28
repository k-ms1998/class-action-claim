package com.proejct.ClassActionClaim.repository.custom;

import com.proejct.ClassActionClaim.domain.Board;

import java.util.List;

public interface BoardRepositoryCustom {

    List<Board> findNotesByWeek(Long week, Long lectureId, String studentId);
}
