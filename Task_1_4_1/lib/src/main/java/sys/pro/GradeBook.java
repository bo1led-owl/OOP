package sys.pro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class GradeBook {
    private List<Semester> semesters;

    /** Create an empty grade book. */
    public GradeBook() {
        semesters = new ArrayList<Semester>();
    }

    /**
     * Create a grade book with given list of semesters.
     *
     * @param semesters - initial semesters
     */
    public GradeBook(List<Semester> semesters) {
        this.semesters = semesters;
    }

    /**
     * Add a semester to the grade book.
     *
     * @param semester - semester to add.
     */
    public void addSemester(Semester semester) {
        semesters.add(semester);
    }

    /**
     * Check whether advanced scholarship is available in the next semester.
     *
     * @return whether it is available or not.
     */
    public boolean canGetAdvancedScholarshipNextSemester() {
        return semesters.isEmpty() || semesters.get(semesters.size() - 1).grantsAdvancedScholarship();
    }

    /**
     * Compute the average grade of the student.
     *
     * @return the average.
     */
    public double averageGrade() {
        var amountOfSubjects = semesters
            .stream()
            .map(sem -> sem
                .subjects()
                .stream()
                .filter(subj -> subj.controlType() != ControlType.Credit)
                .count()
            ).reduce(0L, Long::sum);

        var gradesSum = semesters
            .stream()
            .flatMap(sem -> sem
                .subjects()
                .stream()
                .filter(subj -> subj.controlType() != ControlType.Credit)
                .map(subj -> subj.grade().value)
            )
            .reduce(0, Integer::sum);

        return (double) gradesSum / (double) amountOfSubjects;
    }

    /**
     * Add results of a subject to current semester. If there are no semesters, add one.
     *
     * @param subject - subject to add.
     */
    public void addSubjectToCurrentSemester(Subject subject) {
        if (semesters.isEmpty()) {
            semesters.add(new Semester());
        }

        semesters.get(semesters.size() - 1).addSubject(subject);
    }

    /**
     * Check whether the student can transfer to state-funded education.
     *
     * @throws NoSuchElementException the book has less than two semesters in it.
     * @return whether the transfer is possible.
     */
    public boolean canTransferToStateFundedEducation() {
        var firstSubjects = semesters.get(semesters.size() - 2).subjects().stream();
        var secondSubjects = semesters.get(semesters.size() - 1).subjects().stream();
        return Stream.concat(firstSubjects, secondSubjects)
                .filter(s -> s.controlType() == ControlType.Exam)
                .allMatch(s -> s.grade() != Grade.C);
    }

    /**
     * Collect all final grades in the book.
     *
     * @return a collection of final grades.
     */
    public Collection<Grade> getFinalGrades() {
        var grades = new ArrayList<Grade>();

        for (var semester : semesters) {
            semester.subjects().stream()
                    .filter(s -> s.isFinalGrade() && s.controlType() != ControlType.Credit)
                    .map(s -> s.grade())
                    .forEach(g -> grades.add(g));
        }

        return grades;
    }

    /**
     * Check whether the student can acquire an honors diploma.
     *
     * @return whether the honors diploma is possible or not.
     */
    public boolean canGetHonorsDiploma() {
        var finalGrades = getFinalGrades();
        var countOfAs = finalGrades.stream().filter(g -> g == Grade.A).count();
        var qualificationWorkGrade = Optional.<Grade>empty();
        if (!semesters.isEmpty()) {
            semesters.get(semesters.size() - 1).subjects().stream()
                    .filter(s -> s.controlType() == ControlType.QualificationWork)
                    .map(s -> s.grade())
                    .findFirst();
        }

        return (4 * countOfAs >= 3 * finalGrades.size())
                && finalGrades.stream().noneMatch(g -> g == Grade.C)
                && (!qualificationWorkGrade.isPresent() || qualificationWorkGrade.get() == Grade.A);
    }
}
