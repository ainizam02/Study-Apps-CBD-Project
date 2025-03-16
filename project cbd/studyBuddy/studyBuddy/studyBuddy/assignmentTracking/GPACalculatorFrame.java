import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Main Class with all the components
public class GPACalculatorFrame extends JFrame {

    private static List<Grade> grades = new ArrayList<>();
    private static DefaultTableModel tableModel;

    public GPACalculatorFrame() {
        setTitle("GPA Calculator");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Input fields and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JTextField gradeField = new JTextField(5);
        gradeField.setToolTipText("Enter letter grade (A, B, C, D, F)");

        JTextField creditField = new JTextField(5);
        creditField.setToolTipText("Enter credit hours (e.g., 3, 4)");

        JButton addButton = new JButton("Add Grade");
        JButton calculateButton = new JButton("Calculate GPA");

        inputPanel.add(new JLabel("Grade:"));
        inputPanel.add(gradeField);
        inputPanel.add(new JLabel("Credit Hours:"));
        inputPanel.add(creditField);
        inputPanel.add(addButton);
        inputPanel.add(calculateButton);

        // Table to display grades
        String[] columns = {"Grade", "Credit Hours"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        table.setDefaultEditor(Object.class, null);  // Make the table non-editable
        JScrollPane tableScroll = new JScrollPane(table);

        // Add table and input panel to frame
        add(inputPanel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);

        // Add button action for adding grade
        addButton.addActionListener(e -> {
            String letterGrade = gradeField.getText().trim();
            String creditText = creditField.getText().trim();

            if (letterGrade.isEmpty() || creditText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Both fields must be filled out!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int creditHours = Integer.parseInt(creditText);

                // Validate letter grade and credit hours
                if (getLetterGradeValue(letterGrade) == -1 || creditHours <= 0) {
                    throw new IllegalArgumentException("Invalid grade or credit hours!");
                }

                // Add grade to list and table
                grades.add(new Grade(letterGrade, creditHours));
                tableModel.addRow(new Object[]{letterGrade, creditHours});

                // Clear input fields
                gradeField.setText("");
                creditField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add button action for calculating GPA
        calculateButton.addActionListener(e -> {
            try {
                GPAResponse response = calculateGPA(grades);
                JOptionPane.showMessageDialog(this, "Calculated GPA: " + response.getGpa(), "GPA Result", JOptionPane.INFORMATION_MESSAGE);

                // Optionally clear grades after calculation
                grades.clear();
                tableModel.setRowCount(0);  // Clear the table
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // GPA calculation logic
    private static GPAResponse calculateGPA(List<Grade> grades) {
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

        if (totalCreditHours == 0) {
            throw new ArithmeticException("No credit hours entered. Cannot calculate GPA.");
        }

        double gpa = (double) totalQualityPoints / totalCreditHours;
        return new GPAResponse(gpa);
    }

    // Convert letter grade to quality points
    private static int getLetterGradeValue(String letterGrade) {
        switch (letterGrade.toUpperCase()) {
            case "A": return 4;
            case "B": return 3;
            case "C": return 2;
            case "D": return 1;
            case "F": return 0;
            default: return -1; // Invalid grade
        }
    }

    // Grade class
    public static class Grade {
        private String letterGrade;
        private int creditHours;

        public Grade(String letterGrade, int creditHours) {
            this.letterGrade = letterGrade;
            this.creditHours = creditHours;
        }

        public String getLetterGrade() {
            return letterGrade;
        }

        public int getCreditHours() {
            return creditHours;
        }
    }

    // GPAResponse class
    public static class GPAResponse {
        private double gpa;

        public GPAResponse(double gpa) {
            this.gpa = gpa;
        }

        public double getGpa() {
            return gpa;
        }
    }

    // Main method to launch the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GPACalculatorFrame().setVisible(true);
            }
        });
    }
}