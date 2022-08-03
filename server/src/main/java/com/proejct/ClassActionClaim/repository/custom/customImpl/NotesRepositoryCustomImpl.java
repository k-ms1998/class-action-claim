package com.proejct.ClassActionClaim.repository.custom.customImpl;

import com.proejct.ClassActionClaim.domain.Lecture;
import com.proejct.ClassActionClaim.domain.Notes;
import com.proejct.ClassActionClaim.domain.QNotes;
import com.proejct.ClassActionClaim.domain.Student;
import com.proejct.ClassActionClaim.repository.LectureRepository;
import com.proejct.ClassActionClaim.repository.StudentRepository;
import com.proejct.ClassActionClaim.repository.custom.NotesRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class NotesRepositoryCustomImpl implements NotesRepositoryCustom {

    private final EntityManager entityManager;
    private final LectureRepository lectureRepository;
    private final StudentRepository studentRepository;

    /**
     * Find Notes By Week, Lecture, Student
     * 'select *from notes n where n.week = :week and n.lecture = :lecture and n.student = :student'
     */
    @Override

    public List<Notes> findNotesByWeek(Integer week, Long lectureId, Long studentId) {
        QNotes notes = QNotes.notes;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        Lecture lecture = lectureRepository.findById(lectureId).get();
        Student student = studentRepository.findById(studentId).get();

        return queryFactory
                .select(notes)
                .from(notes)
                .where(notes.week.eq(week))
                .where(notes.lecture.eq(lecture), notes.student.eq(student))
                .fetch();
    }
}
