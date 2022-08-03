package com.proejct.ClassActionClaim.repository.custom;

import com.proejct.ClassActionClaim.domain.Notes;

import java.util.List;

public interface NotesRepositoryCustom {

    List<Notes> findNotesByWeek(Integer week, Long lectureId, Long studentId);
}
