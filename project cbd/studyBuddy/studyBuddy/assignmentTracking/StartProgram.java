import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class StartProgram {
    static List<PlannerItem> plannerItems = new ArrayList<>();
    static int nextId = 1;

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Cute School Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500); // Increased size for better visibility

        // Set custom font and colors
        Font font = new Font("Comic Sans MS", Font.PLAIN, 16); // Increased font size
        Color pastelBlue = new Color(173, 216, 230);
        Color pastelPink = new Color(255, 182, 193);
        Color pastelYellow = new Color(255, 250, 205);
        Color pastelGreen = new Color(152, 251, 152);

        // Create table model and JTable
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "Course", "Assignment", "Due Date", "Completed"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) { // "Completed" column
                    return Boolean.class;
                }
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only "Completed" column is editable
            }
        };
        JTable table = new JTable(tableModel);
        table.setFont(font);
        table.setRowHeight(30); // Increased row height for better readability
        table.setSelectionBackground(pastelYellow);
        table.setSelectionForeground(Color.BLACK);

        // Set table header font
        table.getTableHeader().setFont(new Font("Comic Sans MS", Font.BOLD, 16));

        // Alternate row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? pastelBlue : pastelPink);
                } else {
                    c.setBackground(pastelYellow);
                }
                return c;
            }
        });

        // Adjust column widths
        adjustColumnWidths(table);

        // Listen for changes in the "Completed" checkbox
        tableModel.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (column == 4 && row >= 0) { // "Completed" column
                Boolean isCompleted = (Boolean) tableModel.getValueAt(row, column);
                if (row < plannerItems.size()) {
                    plannerItems.get(row).setCompleted(isCompleted);
                }
            }
        });

        // Input fields
        JTextField courseField = new JTextField(15);
        courseField.setFont(font);
        JTextField assignmentField = new JTextField(15);
        assignmentField.setFont(font);
        JTextField dueDateField = new JTextField(10);
        dueDateField.setFont(font);
        dueDateField.setToolTipText("Format: YYYY-MM-DD");

        // Buttons with rounded corners and colorful backgrounds
        JButton addButton = createRoundedButton("Add Item", pastelGreen, font);
        JButton deleteButton = createRoundedButton("Delete Selected", pastelPink, font);
        JButton editButton = createRoundedButton("Edit Selected", pastelBlue, font);
        JButton scheduleButton = createRoundedButton("View Schedule", pastelBlue, font);

        // Add button action
        addButton.addActionListener(e -> {
            String course = courseField.getText().trim();
            String assignment = assignmentField.getText().trim();
            String dueDate = dueDateField.getText().trim();

            if (!course.isEmpty() && !assignment.isEmpty() && !dueDate.isEmpty()) {
                PlannerItem newItem = new PlannerItem(nextId++, course, assignment, dueDate);
                plannerItems.add(newItem);
                tableModel.addRow(new Object[]{newItem.getId(), newItem.getCourse(), newItem.getAssignment(), newItem.getDueDate(), newItem.isCompleted()});

                // Clear fields
                courseField.setText("");
                assignmentField.setText("");
                dueDateField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "All fields must be filled out!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Delete button action
        deleteButton.addActionListener(e -> {
            int selectedIndex = table.getSelectedRow();
            if (selectedIndex != -1) {
                int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete the selected item?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    tableModel.removeRow(selectedIndex);
                    plannerItems.remove(selectedIndex);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Select an item to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Edit button action
        editButton.addActionListener(e -> {
            int selectedIndex = table.getSelectedRow();
            if (selectedIndex != -1) {
                PlannerItem selectedItem = plannerItems.get(selectedIndex);

                // Prompt user for new values
                String newCourse = JOptionPane.showInputDialog(frame, "New Course:", selectedItem.getCourse());
                String newAssignment = JOptionPane.showInputDialog(frame, "New Assignment:", selectedItem.getAssignment());
                String newDueDate = JOptionPane.showInputDialog(frame, "New Due Date (YYYY-MM-DD):", selectedItem.getDueDate());

                if (newCourse != null && newAssignment != null && newDueDate != null) {
                    // Update item and table
                    selectedItem.setCourse(newCourse.trim());
                    selectedItem.setAssignment(newAssignment.trim());
                    selectedItem.setDueDate(newDueDate.trim());

                    tableModel.setValueAt(newCourse.trim(), selectedIndex, 1);
                    tableModel.setValueAt(newAssignment.trim(), selectedIndex, 2);
                    tableModel.setValueAt(newDueDate.trim(), selectedIndex, 3);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Select an item to edit!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        scheduleButton.addActionListener(e -> {
            if (plannerItems.isEmpty()) {
               JOptionPane.showMessageDialog(frame, "No tasks available in the schedule!", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
               ScheduleWindow.showSchedule(frame, plannerItems);
    }});
        
        // Panel for inputs
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(pastelYellow);
        inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        inputPanel.add(new JLabel("Course:"));
        inputPanel.add(courseField);
        inputPanel.add(new JLabel("Assignment:"));
        inputPanel.add(assignmentField);
        inputPanel.add(new JLabel("Due Date:"));
        inputPanel.add(dueDateField);
        inputPanel.add(addButton);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(pastelYellow);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(scheduleButton);

        // Add components to main frame
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Display the frame
        frame.getContentPane().setBackground(pastelPink);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }

    /**
     * Creates a JButton with rounded corners and specified styles.
     *
     * @param text            The text to display on the button.
     * @param backgroundColor The background color of the button.
     * @param font            The font of the button text.
     * @return A styled JButton.
     */
    private static JButton createRoundedButton(String text, Color backgroundColor, Font font) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(getBackground());
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width = Math.max(size.width, 120);
                size.height = Math.max(size.height, 40);
                return size;
            }
        };
        button.setFont(font);
        button.setBackground(backgroundColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }

    /**
     * Adjusts the column widths of the table based on content.
     *
     * @param table The JTable to adjust.
     */
    private static void adjustColumnWidths(JTable table) {
        TableColumnModel columnModel = table.getColumnModel();
        // Set preferred widths for each column
        columnModel.getColumn(0).setPreferredWidth(50);  // ID
        columnModel.getColumn(1).setPreferredWidth(150); // Course
        columnModel.getColumn(2).setPreferredWidth(250); // Assignment
        columnModel.getColumn(3).setPreferredWidth(100); // Due Date
        columnModel.getColumn(4).setPreferredWidth(100); // Completed

        // Set renderer for "Completed" column to center the checkbox
        TableColumn completedColumn = columnModel.getColumn(4);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        completedColumn.setCellRenderer(centerRenderer);
    }
}
