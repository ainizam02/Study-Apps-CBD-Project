public class PlannerItem {
    private int id;
    private String course;
    private String assignment;
    private String dueDate;
    private boolean completed;

    // Constructors
    public PlannerItem() {
        super();
    }

    public PlannerItem(int id, String course, String assignment, String dueDate) {
        super();
        this.id = id;
        this.course = course;
        this.assignment = assignment;
        this.dueDate = dueDate;
        this.completed = false; // Default to not completed
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    // Method to return details (optional, for debugging)
    public String returnPlannerDetails() {
        return "Course: " + this.course + ", Assignment: " + this.assignment + ", Due Date: " + this.dueDate
                + ", Completed: " + this.completed;
    }
}
