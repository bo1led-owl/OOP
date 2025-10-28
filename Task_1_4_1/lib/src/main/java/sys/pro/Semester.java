package sys.pro;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Semester {
    private List<Subject> subjects;

    public Semester() {
        subjects = new ArrayList<Subject>();
    }

    public Semester(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Subject> subjects() {
        return subjects;
    }

    public List<GradedSubject> gradedSubjects() {
        return subjects.stream()
                .filter(s -> s instanceof GradedSubject)
                .map(s -> (GradedSubject) s)
                .collect(Collectors.toList());
    }

    /**
     * Add a subject to the semester.
     *
     * @param subject - subject to add
     */
    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    /**
     * Check whether advanced scholarship is available after this semester.
     *
     * @return whether it is available or not.
     */
    public boolean grantsAdvancedScholarship() {
        return gradedSubjects().stream().allMatch(s -> s.grade == Grade.A);
    }
}
