package com.proejct.ClassActionClaim.repository.custom.customImpl;

import com.proejct.ClassActionClaim.domain.Board;
import com.proejct.ClassActionClaim.domain.Lecture;
import com.proejct.ClassActionClaim.domain.QBoard;
import com.proejct.ClassActionClaim.domain.Student;
import com.proejct.ClassActionClaim.repository.LectureRepository;
import com.proejct.ClassActionClaim.repository.StudentRepository;
import com.proejct.ClassActionClaim.repository.custom.BoardRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private final EntityManager entityManager;
    private final LectureRepository lectureRepository;
    private final StudentRepository studentRepository;

    /**
     * Find Notes By Week, Lecture, Student
     * 'select *from notes n where n.week = :week and n.lecture = :lecture and n.student = :student'
     */
    @Override
    public List<Board> findNotesByWeek(Long week, Long lectureId, String studentId) {
        QBoard board = QBoard.board;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        Lecture lecture = lectureRepository.findById(lectureId).get();
        Student student = studentRepository.findByUuid(studentId);

        return queryFactory
                .select(board)
                .from(board)
                .where(board.week.eq(Long.valueOf(week)))
                .where(board.lecture.eq(lecture), board.student.eq(student))
                .fetch();
    }
}
