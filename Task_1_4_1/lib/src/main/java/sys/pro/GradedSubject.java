package sys.pro;

abstract class GradedSubject extends Subject {
    public final Grade grade;

    protected GradedSubject(String name, Grade grade) {
        super(name);
        this.grade = grade;
    }
}
