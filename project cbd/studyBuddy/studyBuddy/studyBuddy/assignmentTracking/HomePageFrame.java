import javax.swing.*;
import java.awt.*;

public class HomePageFrame extends JFrame {
    public HomePageFrame() {
        setTitle ("Study Buddy Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Study Buddy Dashboard", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(headerLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));

        JButton assignmentButton = new JButton("Assignment Tracking");
        JButton gpaCalculatorButton = new JButton("GPA Calculator");

        buttonPanel.add(assignmentButton);
        buttonPanel.add(gpaCalculatorButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Action for Assignment Tracking button
        assignmentButton.addActionListener(e -> {
            try {
                // Launch the Assignment Tracking system
                StartProgram.main(new String[]{}); // Calls the main method of StartProgram
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error launching Assignment Tracking: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action for GPA Calculator button
        gpaCalculatorButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new GPACalculatorFrame().setVisible(true));
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomePageFrame().setVisible(true));
    }
}
