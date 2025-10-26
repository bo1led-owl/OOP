package sys.pro;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GradeBookTest {
    @Test
    void scholarship() {
        var book = new GradeBook();
        assertTrue(book.canGetAdvancedScholarshipNextSemester());

        book.addSemester(new Semester());
        assertTrue(book.canGetAdvancedScholarshipNextSemester());

        book.addSubjectToCurrentSemester(
            new Subject("Математический анализ", ControlType.Exam, false, Grade.A)
        );
        book.addSubjectToCurrentSemester(
            new Subject("История России", ControlType.Mark, false, Grade.A)
        );

        assertTrue(book.canGetAdvancedScholarshipNextSemester());

        book.addSubjectToCurrentSemester(
                new Subject("Аналитическая геометрия", ControlType.Exam, false, Grade.B)
        );
        assertFalse(book.canGetAdvancedScholarshipNextSemester());
    }

    @Test
    void average() {
        var subjects1 = new ArrayList<Subject>();
        subjects1.add(new Subject("ДМТА", ControlType.Exam, true, Grade.A));
        subjects1.add(new Subject("Математический анализ", ControlType.Exam, false, Grade.C));
        subjects1.add(new Subject("Физ-ра и спорт", ControlType.Credit, false, Grade.A));

        var subjects2 = new ArrayList<Subject>();
        subjects2.add(new Subject("Высшая алгебра", ControlType.Exam, true, Grade.A));
        subjects2.add(new Subject("Математический анализ", ControlType.Exam, false, Grade.B));
        subjects2.add(new Subject("Физ-ра и спорт", ControlType.Credit, false, Grade.A));
        
        var semesters = new ArrayList<Semester>();
        semesters.add(new Semester(subjects1));
        semesters.add(new Semester(subjects2));
        
        var book = new GradeBook(semesters);
        assertEquals((5 + 3 + 5 + 4) / 4.0, book.averageGrade());
    }

    @Test
    void stateFundedEducation() {
        var subjects1 = new ArrayList<Subject>();
        subjects1.add(new Subject("ДМТА", ControlType.Exam, true, Grade.A));
        subjects1.add(new Subject("Математический анализ", ControlType.Exam, false, Grade.C));
        subjects1.add(new Subject("История России", ControlType.Mark, false, Grade.C));

        var subjects2 = new ArrayList<Subject>();
        subjects2.add(new Subject("Высшая алгебра", ControlType.Exam, true, Grade.A));
        subjects2.add(new Subject("Математический анализ", ControlType.Exam, false, Grade.B));
        subjects2.add(new Subject("История России", ControlType.Exam, false, Grade.B));
        
        var semesters = new ArrayList<Semester>();
        semesters.add(new Semester(subjects1));
        semesters.add(new Semester(subjects2));
        
        var book = new GradeBook(semesters);
        assertFalse(book.canTransferToStateFundedEducation());

        var subjects3 = new ArrayList<Subject>();
        subjects3.add(new Subject("Математическая логика", ControlType.Exam, false, Grade.B));
        subjects3.add(new Subject("Математический анализ", ControlType.Exam, false, Grade.B));
        subjects3.add(new Subject("Иностранный язык", ControlType.Mark, false, Grade.C));
        book.addSemester(new Semester(subjects3));

        assertTrue(book.canTransferToStateFundedEducation());
    }

    @Test
    void finalGrades() {
        var subjects1 = new ArrayList<Subject>();
        subjects1.add(new Subject("ДМТА", ControlType.Exam, true, Grade.A));
        subjects1.add(new Subject("Математический анализ", ControlType.Exam, false, Grade.C));
        subjects1.add(new Subject("История России", ControlType.Mark, false, Grade.C));

        var subjects2 = new ArrayList<Subject>();
        subjects2.add(new Subject("Высшая алгебра", ControlType.Exam, true, Grade.A));
        subjects2.add(new Subject("Математический анализ", ControlType.Exam, false, Grade.B));
        subjects2.add(new Subject("История России", ControlType.Exam, true, Grade.B));

        var subjects3 = new ArrayList<Subject>();
        subjects3.add(new Subject("Математическая логика", ControlType.Exam, false, Grade.A));
        subjects3.add(new Subject("Математический анализ", ControlType.Exam, false, Grade.B));
        subjects3.add(new Subject("Теоретическая механика", ControlType.Exam, false, Grade.C));

        var subjects4 = new ArrayList<Subject>();
        subjects4.add(new Subject("Математическая логика", ControlType.Exam, true, Grade.A));
        subjects4.add(new Subject("Математический анализ", ControlType.Exam, true, Grade.B));
        subjects4.add(new Subject("Теоретическая механика", ControlType.Exam, true, Grade.B));
        
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
        subjects1.add(new Subject("ДМТА", ControlType.Exam, true, Grade.A));
        subjects1.add(new Subject("Математический анализ", ControlType.Exam, false, Grade.C));
        subjects1.add(new Subject("История России", ControlType.Mark, false, Grade.C));

        var subjects2 = new ArrayList<Subject>();
        subjects2.add(new Subject("Высшая алгебра", ControlType.Exam, true, Grade.A));
        subjects2.add(new Subject("Математический анализ", ControlType.Exam, true, Grade.B));
        subjects2.add(new Subject("История России", ControlType.Exam, true, Grade.B));
        
        var semesters = new ArrayList<Semester>();
        semesters.add(new Semester(subjects1));
        semesters.add(new Semester(subjects2));

        var book = new GradeBook(semesters);
        assertFalse(book.canGetHonorsDiploma());
    }

    @Test
    void badQualificationWork() {
        var subjects1 = new ArrayList<Subject>();
        subjects1.add(new Subject("ДМТА", ControlType.Exam, true, Grade.A));
        subjects1.add(new Subject("Математический анализ", ControlType.Exam, false, Grade.C));
        subjects1.add(new Subject("История России", ControlType.Mark, false, Grade.C));

        var subjects2 = new ArrayList<Subject>();
        subjects2.add(new Subject("Высшая алгебра", ControlType.Exam, true, Grade.A));
        subjects2.add(new Subject("Математический анализ", ControlType.Exam, true, Grade.A));
        subjects2.add(new Subject("История России", ControlType.Exam, true, Grade.A));

        subjects2.add(new Subject("ВКР", ControlType.QualificationWork, true, Grade.C));
        
        var semesters = new ArrayList<Semester>();
        semesters.add(new Semester(subjects1));
        semesters.add(new Semester(subjects2));

        var book = new GradeBook(semesters);
        assertFalse(book.canGetHonorsDiploma());
    }

    @Test
    void canGetHonorsDiploma() {
        var subjects1 = new ArrayList<Subject>();
        subjects1.add(new Subject("ДМТА", ControlType.Exam, true, Grade.A));
        subjects1.add(new Subject("Математический анализ", ControlType.Exam, false, Grade.C));
        subjects1.add(new Subject("История России", ControlType.Mark, false, Grade.C));

        var subjects2 = new ArrayList<Subject>();
        subjects2.add(new Subject("Высшая алгебра", ControlType.Exam, true, Grade.B));
        subjects2.add(new Subject("Математический анализ", ControlType.Exam, true, Grade.A));
        subjects2.add(new Subject("История России", ControlType.Exam, true, Grade.A));

        subjects2.add(new Subject("ВКР", ControlType.QualificationWork, true, Grade.A));
        
        var semesters = new ArrayList<Semester>();
        semesters.add(new Semester(subjects1));
        semesters.add(new Semester(subjects2));

        var book = new GradeBook(semesters);
        assertTrue(book.canGetHonorsDiploma());
    }
}
