package sys.pro;

import java.util.ArrayList;
import java.util.List;

public record Semester(List<Subject> subjects) {
    public Semester() {
        this(new ArrayList<Subject>());
    }

    /**
     * Add a subject to the semester.
     *
     * @param subject - subject to add
     */
    void addSubject(Subject subject) {
        subjects.add(subject);
    }

    /**
     * Check whether advanced scholarship is available after this semester.
     *
     * @returns whether it is available or not.
     */
    boolean grantsAdvancedScholarship() {
        return subjects.stream().allMatch(s -> s.grade() == Grade.A);
    }
}
