import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class GPAController {

    @PostMapping("/calculate-gpa")
    public GPAResponse calculateGPA(@RequestBody List<Grade> grades) {
        int totalQualityPoints = 0;
        int totalCreditHours = 0;

        for (Grade grade : grades) {
            int qualityPoints = getLetterGradeValue(grade.getLetterGrade());
            if (qualityPoints == -1 || grade.getCreditHours() < 0) {
                throw new IllegalArgumentException("Invalid grade or credit hours");
            }
            totalQualityPoints += qualityPoints * grade.getCreditHours();
            totalCreditHours += grade.getCreditHours();
        }

        double gpa = (double) totalQualityPoints / totalCreditHours;
        return new GPAResponse(gpa);
    }

    private int getLetterGradeValue(char letterGrade) {
        switch (Character.toLowerCase(letterGrade)) {
            case 'a':
                return 4;
            case 'b':
                return 3;
            case 'c':
                return 2;
            case 'd':
                return 1;
            case 'f':
                return 0;
            default:
                return -1;
        }
    }

    static class Grade {
        private char letterGrade;
        private int creditHours;

        public char getLetterGrade() {
            return letterGrade;
        }

        public void setLetterGrade(char letterGrade) {
            this.letterGrade = letterGrade;
        }

        public int getCreditHours() {
            return creditHours;
        }

        public void setCreditHours(int creditHours) {
            this.creditHours = creditHours;
        }
    }

    static class GPAResponse {
        private double gpa;

        public GPAResponse(double gpa) {
            this.gpa = gpa;
        }

        public double getGpa() {
            return gpa;
        }

        public void setGpa(double gpa) {
            this.gpa = gpa;
        }
    }
}
