package sys.pro;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Semester {
    private List<GradedSubject> gradedSubjects;

    public Semester() {
        gradedSubjects = new ArrayList<GradedSubject>();
    }

    public Semester(List<Subject> subjects) {
        gradedSubjects =
                subjects.stream()
                        .filter(s -> s instanceof GradedSubject)
                        .map(s -> (GradedSubject) s)
                        .collect(Collectors.toList());
    }

    public List<GradedSubject> gradedSubjects() {
        return gradedSubjects;
    }

    /**
     * Add a subject to the semester.
     *
     * @param subject - subject to add
     */
    public void addSubject(Subject subject) {
        if (subject instanceof GradedSubject) {
            gradedSubjects.add((GradedSubject) subject);
        }
    }

    /**
     * Check whether advanced scholarship is available after this semester.
     *
     * @return whether it is available or not.
     */
    public boolean grantsAdvancedScholarship() {
        return gradedSubjects.stream().allMatch(s -> s.grade == Grade.A);
    }
}
