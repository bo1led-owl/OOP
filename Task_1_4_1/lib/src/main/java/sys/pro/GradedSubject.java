package sys.pro;

abstract class GradedSubject extends Subject {
    public final Grade grade;

    public GradedSubject(String name, Grade grade) {
        super(name);
        this.grade = grade;
    }
}
