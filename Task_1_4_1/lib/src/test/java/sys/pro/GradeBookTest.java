package sys.pro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class GradeBookTest {
    @Test
    void scholarship() {
        var book = new GradeBook();
        assertTrue(book.canGetAdvancedScholarshipNextSemester());

        book.addSemester(new Semester());
        assertTrue(book.canGetAdvancedScholarshipNextSemester());

        book.addSubjectToCurrentSemester(new Exam("Математический анализ", Grade.A));
        book.addSubjectToCurrentSemester(new Mark("История России", Grade.A));

        assertTrue(book.canGetAdvancedScholarshipNextSemester());

        book.addSubjectToCurrentSemester(new Exam("Аналитическая геометрия", Grade.B));
        assertFalse(book.canGetAdvancedScholarshipNextSemester());
    }

    @Test
    void average() {
        var subjects1 = new ArrayList<Subject>();
        subjects1.add(new Exam("ДМТА", Grade.A));
        subjects1.add(new Exam("Математический анализ", Grade.C));
        subjects1.add(new Credit("Физ-ра и спорт"));

        var subjects2 = new ArrayList<Subject>();
        subjects2.add(new Exam("Высшая алгебра", Grade.A));
        subjects2.add(new Exam("Математический анализ", Grade.B));
        subjects2.add(new Credit("Физ-ра и спорт"));

        var semesters = new ArrayList<Semester>();
        semesters.add(new Semester(subjects1));
        semesters.add(new Semester(subjects2));

        var book = new GradeBook(semesters);
        assertEquals((5 + 3 + 5 + 4) / 4.0, book.averageGrade());
    }

    @Test
    void stateFundedEducation() {
        var emptyBook = new GradeBook();
        assertThrows(
                IllegalArgumentException.class,
                () -> emptyBook.canTransferToStateFundedEducation());

        var subjects1 = new ArrayList<Subject>();
        subjects1.add(new Exam("ДМТА", Grade.A));
        subjects1.add(new Exam("Математический анализ", Grade.C));
        subjects1.add(new Mark("История России", Grade.C));

        var subjects2 = new ArrayList<Subject>();
        subjects2.add(new Exam("Высшая алгебра", Grade.A));
        subjects2.add(new Exam("Математический анализ", Grade.B));
        subjects2.add(new Exam("История России", Grade.B));

        var semesters = new ArrayList<Semester>();
        semesters.add(new Semester(subjects1));
        semesters.add(new Semester(subjects2));

        var book = new GradeBook(semesters);
        assertFalse(book.canTransferToStateFundedEducation());

        var subjects3 = new ArrayList<Subject>();
        subjects3.add(new Exam("Математическая логика", Grade.B));
        subjects3.add(new Exam("Математический анализ", Grade.B));
        subjects3.add(new Mark("Иностранный язык", Grade.C));
        book.addSemester(new Semester(subjects3));

        assertTrue(book.canTransferToStateFundedEducation());
    }

    @Test
    void finalGrades() {
        var subjects1 = new ArrayList<Subject>();
        subjects1.add(new Exam("ДМТА", Grade.A));
        subjects1.add(new Exam("Математический анализ", Grade.C));
        subjects1.add(new Mark("История России", Grade.C));

        var subjects2 = new ArrayList<Subject>();
        subjects2.add(new Exam("Высшая алгебра", Grade.A));
        subjects2.add(new Exam("Математический анализ", Grade.B));
        subjects2.add(new Exam("История России", Grade.B));

        var subjects3 = new ArrayList<Subject>();
        subjects3.add(new Exam("Математическая логика", Grade.A));
        subjects3.add(new Exam("Математический анализ", Grade.B));
        subjects3.add(new Exam("Теоретическая механика", Grade.C));

        var subjects4 = new ArrayList<Subject>();
        subjects4.add(new Exam("Математическая логика", Grade.A));
        subjects4.add(new Exam("Математический анализ", Grade.B));
        subjects4.add(new Exam("Теоретическая механика", Grade.B));

        var semesters = new ArrayList<Semester>();
        semesters.add(new Semester(subjects1));
        semesters.add(new Semester(subjects2));
        semesters.add(new Semester(subjects3));
        semesters.add(new Semester(subjects4));

        var book = new GradeBook(semesters);

        assertEquals(0, book.getFinalGrades().stream().filter(g -> g == Grade.C).count());
        assertEquals(3, book.getFinalGrades().stream().filter(g -> g == Grade.B).count());
        assertEquals(3, book.getFinalGrades().stream().filter(g -> g == Grade.A).count());
    }

    @Test
    void cannotGetHonorsDiploma() {
        var subjects1 = new ArrayList<Subject>();
        subjects1.add(new Exam("ДМТА", Grade.A));
        subjects1.add(new Exam("Математический анализ", Grade.C));
        subjects1.add(new Mark("История России", Grade.C));

        var subjects2 = new ArrayList<Subject>();
        subjects2.add(new Exam("Высшая алгебра", Grade.A));
        subjects2.add(new Exam("Математический анализ", Grade.B));
        subjects2.add(new Exam("История России", Grade.B));

        var semesters = new ArrayList<Semester>();
        semesters.add(new Semester(subjects1));
        semesters.add(new Semester(subjects2));

        var book = new GradeBook(semesters);
        assertFalse(book.canGetHonorsDiploma());
    }

    @Test
    void badQualificationWork() {
        var subjects = new ArrayList<Subject>();
        subjects.add(new Exam("Математический анализ", Grade.A));
        subjects.add(new Exam("Высшая алгебра", Grade.A));
        subjects.add(new Exam("Математическая логика", Grade.A));

        subjects.add(new QualificationWork(Grade.B));

        var book = new GradeBook();
        book.addSemester(new Semester(subjects));
        assertFalse(book.canGetHonorsDiploma());
    }

    @Test
    void badFinalGrades() {
        var subjects = new ArrayList<Subject>();
        subjects.add(new Exam("Математический анализ", Grade.C));
        subjects.add(new Exam("Высшая алгебра", Grade.A));
        subjects.add(new Exam("Математическая логика", Grade.A));

        subjects.add(new QualificationWork(Grade.A));

        var book = new GradeBook();
        book.addSemester(new Semester(subjects));
        assertFalse(book.canGetHonorsDiploma());
    }

    @Test
    void canGetHonorsDiploma() {
        var subjects1 = new ArrayList<Subject>();
        subjects1.add(new Exam("ДМТА", Grade.A));
        subjects1.add(new Exam("Математический анализ", Grade.C));
        subjects1.add(new Mark("История России", Grade.C));

        var subjects2 = new ArrayList<Subject>();
        subjects2.add(new Exam("Высшая алгебра", Grade.B));
        subjects2.add(new Exam("Математический анализ", Grade.A));
        subjects2.add(new Exam("История России", Grade.A));

        subjects2.add(new QualificationWork(Grade.A));

        var semesters = new ArrayList<Semester>();
        semesters.add(new Semester(subjects1));
        semesters.add(new Semester(subjects2));

        var book = new GradeBook(semesters);
        assertTrue(book.canGetHonorsDiploma());
    }
}
