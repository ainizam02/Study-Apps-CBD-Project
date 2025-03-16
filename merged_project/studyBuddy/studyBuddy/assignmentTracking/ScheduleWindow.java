import java.awt.*;
import java.util.List;
import javax.swing.*;

public class ScheduleWindow {
    public static void showSchedule(JFrame parent, List<PlannerItem> plannerItems) {
        // Create a new frame for the schedule
        JFrame scheduleFrame = new JFrame("Task Prioritization");
        scheduleFrame.setSize(600, 400);
        scheduleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        scheduleFrame.setLocationRelativeTo(parent);

        // Create a JPanel for the tasks
        JPanel schedulePanel = new JPanel();
        schedulePanel.setLayout(new BoxLayout(schedulePanel, BoxLayout.Y_AXIS));
        schedulePanel.setBackground(new Color(255, 250, 205)); // Pastel Yellow

        // Sort the tasks by due date
        plannerItems.sort((item1, item2) -> item1.getDueDate().compareTo(item2.getDueDate()));

        // Display each task
        for (PlannerItem item : plannerItems) {
            String taskText = String.format(
                    "<html>Course: <b>%s</b><br>Assignment: <b>%s</b><br>Due Date: <b>%s</b><br>Completed: <b>%s</b></html>",
                    item.getCourse(), item.getAssignment(), item.getDueDate(), item.isCompleted() ? "Yes" : "No"
            );
            JLabel taskLabel = new JLabel(taskText);
            taskLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            taskLabel.setOpaque(true);
            taskLabel.setBackground(item.isCompleted() ? new Color(152, 251, 152) : new Color(255, 182, 193));
            taskLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
            schedulePanel.add(taskLabel);
        }

        // Add the panel to a scroll pane for scrolling if tasks exceed the window height
        JScrollPane scrollPane = new JScrollPane(schedulePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        scheduleFrame.add(scrollPane);
        scheduleFrame.setVisible(true);
    }
}
