package learnify.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import learnify.database.DataManager;
import learnify.models.*;
import java.util.List;

public class TeacherDashboard extends JFrame {
    private final Teacher teacher;
    private final DataManager dataManager;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private String currentView = ""; // ✅ Track what we're viewing

    public TeacherDashboard(Teacher teacher) {
        this.teacher = teacher;
        this.dataManager = DataManager.getInstance();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Learnify - Teacher Dashboard");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(34, 139, 34));
        headerPanel.setPreferredSize(new Dimension(950, 60));
        JLabel welcomeLabel = new JLabel("Teacher Dashboard - " + teacher.getUsername());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);
        headerPanel.add(welcomeLabel);

        JPanel navPanel = new JPanel();
        navPanel.setBackground(new Color(200, 250, 200));
        navPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton myCoursesBtn = new JButton("My Courses");
        JButton createQuizBtn = new JButton("Create Quiz");
        JButton viewAssignmentsBtn = new JButton("View Assignments");
        JButton createAssignmentBtn = new JButton("Create Assignment");
        JButton gradeSubmissionsBtn = new JButton("Grade Submissions");
        JButton viewProgressBtn = new JButton("Student Progress");
        JButton logoutBtn = new JButton("Logout");

        myCoursesBtn.addActionListener(e -> displayMyCourses());
        createQuizBtn.addActionListener(e -> openCreateQuizDialog());
        viewAssignmentsBtn.addActionListener(e -> displayAssignments());
        createAssignmentBtn.addActionListener(e -> openCreateAssignmentDialog());
        gradeSubmissionsBtn.addActionListener(e -> displayAssignmentsForGrading());
        viewProgressBtn.addActionListener(e -> displayStudentProgress());
        logoutBtn.addActionListener(e -> handleLogout());

        navPanel.add(myCoursesBtn);
        navPanel.add(createQuizBtn);
        navPanel.add(viewAssignmentsBtn);
        navPanel.add(createAssignmentBtn);
        navPanel.add(gradeSubmissionsBtn);
        navPanel.add(viewProgressBtn);
        navPanel.add(logoutBtn);

        JPanel contentPanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        dataTable.setRowHeight(25);

        // ✅ FIXED - Add mouse listener AFTER dataTable is created
        setupDeleteMouseListener();

        JScrollPane scrollPane = new JScrollPane(dataTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(navPanel, BorderLayout.PAGE_START);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);

        displayMyCourses();
    }

    // ✅ FIXED - Mouse Listener as a separate method
    private void setupDeleteMouseListener() {
        dataTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = dataTable.getSelectedRow();
                int col = dataTable.getSelectedColumn();

                // Check if clicked on Action column (last column)
                if (col != tableModel.getColumnCount() - 1) return;
                if (row < 0) return;

                String id = tableModel.getValueAt(row, 0).toString();

                int confirm = JOptionPane.showConfirmDialog(
                        TeacherDashboard.this,
                        "Are you sure you want to delete this " + currentView + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm != JOptionPane.YES_OPTION) return;

                try {
                    switch (currentView) {
                        case "Course" -> {
                            dataManager.deleteCourse(id);
                            JOptionPane.showMessageDialog(TeacherDashboard.this, "Course deleted successfully!");
                            displayMyCourses();
                        }

                        case "Quiz" -> {
                            dataManager.deleteQuiz(id);
                            JOptionPane.showMessageDialog(TeacherDashboard.this, "Quiz deleted successfully!");
                            displayQuizzes();
                        }

                        case "Assignment" -> {
                            dataManager.deleteAssignment(id);
                            JOptionPane.showMessageDialog(TeacherDashboard.this, "Assignment deleted successfully!");
                            displayAssignments();
                        }

                        default -> JOptionPane.showMessageDialog(TeacherDashboard.this, "Delete not supported for this view.");
                    }
                } catch (HeadlessException ex) {
                    JOptionPane.showMessageDialog(TeacherDashboard.this, "Error deleting: " + ex.getMessage());
                }
            }
        });
    }

    // ---------------------- Display My Courses ----------------------
    private void displayMyCourses() {
        currentView = "Course";  // ✅ Set current view
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new String[]{"Course ID", "Course Name", "Students", "Action"});

        List<String> courseIds = teacher.getAssignedCourses();

        for (String courseId : courseIds) {
            Course course = dataManager.getCourseById(courseId);
            if (course != null) {
                tableModel.addRow(new Object[]{
                    course.getCourseId(),
                    course.getCourseName(),
                    course.getEnrollmentCount(),
                    "Delete"
                });
            }
        }
    }

    // ✅ NEW METHOD - Display All Quizzes
    private void displayQuizzes() {
        currentView = "Quiz";  // ✅ Set current view
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(
            new String[]{"Quiz ID", "Title", "Course", "Total Marks", "Action"}
        );

        List<String> assignedCourses = teacher.getAssignedCourses();
        
        // Debug info
        System.out.println("=== VIEW QUIZZES DEBUG ===");
        System.out.println("Teacher ID: " + teacher.getUserId());
        System.out.println("Assigned Courses: " + assignedCourses);
        
        int quizCount = 0;
        
        for (String courseId : assignedCourses) {
            List<Quiz> quizzes = dataManager.getQuizzesByCourse(courseId);
            System.out.println("Course " + courseId + " has " + quizzes.size() + " quizzes");
            
            for (Quiz quiz : quizzes) {
                tableModel.addRow(new Object[]{
                    quiz.getQuizId(),
                    quiz.getTitle(),
                    quiz.getCourseId(),
                    quiz.getTotalMarks(),
                    "Delete"
                });
                quizCount++;
            }
        }
        
        System.out.println("Total quizzes displayed: " + quizCount);
        System.out.println("Total quizzes in system: " + dataManager.getAllQuizzes().size());
        
        if (quizCount == 0) {
            JOptionPane.showMessageDialog(this, 
                "No quizzes found.\nMake sure to create a quiz for one of your assigned courses.",
                "No Quizzes",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ---------------------- Display Assignments ----------------------
    private void displayAssignments() {
        currentView = "Assignment";  // ✅ Set current view
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(
            new String[]{"Assignment ID", "Title", "Course", "Marks", "Action"}
        );

        for (String courseId : teacher.getAssignedCourses()) {
            List<Assignment> assignments = dataManager.getAssignmentsByCourse(courseId);

            for (Assignment assignment : assignments) {
                tableModel.addRow(new Object[]{
                    assignment.getAssignmentId(),
                    assignment.getTitle(),
                    assignment.getCourseId(),
                    assignment.getTotalMarks(),
                    "Delete"
                });
            }
        }
    }

    // ---------------------- Create Quiz Dialog ----------------------
    private void openCreateQuizDialog() {
        JDialog dialog = new JDialog(this, "Create Quiz", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JTextField quizTitleField = new JTextField(20);
        JTextField totalMarksField = new JTextField(20);
        JComboBox<String> courseCombo = new JComboBox<>();
        teacher.getAssignedCourses().forEach(courseCombo::addItem);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Quiz Title:"), gbc);
        gbc.gridx = 1; panel.add(quizTitleField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Total Marks:"), gbc);
        gbc.gridx = 1; panel.add(totalMarksField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Course:"), gbc);
        gbc.gridx = 1; panel.add(courseCombo, gbc);

        JButton submitButton = new JButton("Create");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;

        submitButton.addActionListener((ActionEvent e) -> {
            try {
                String quizId = "QUIZ_" + System.currentTimeMillis();
                
                Quiz quiz = new Quiz(
                        quizId,
                        quizTitleField.getText(),
                        (String) courseCombo.getSelectedItem(),
                        teacher.getUserId(),
                        Integer.parseInt(totalMarksField.getText()),
                        "2025-12-30"
                );
                
                dataManager.addQuiz(quiz);
                
                JOptionPane.showMessageDialog(dialog, "Quiz created successfully!");
                dialog.dispose();
                
            } catch (HeadlessException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });

        panel.add(submitButton, gbc);
        dialog.add(panel);
        dialog.setVisible(true);
    }

    // ---------------------- Create Assignment Dialog ----------------------
    private void openCreateAssignmentDialog() {
        JDialog dialog = new JDialog(this, "Create Assignment", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JTextField titleField = new JTextField(20);
        JTextField descriptionField = new JTextField(20);
        JTextField marksField = new JTextField(20);
        JComboBox<String> courseCombo = new JComboBox<>();

        teacher.getAssignedCourses().forEach(courseCombo::addItem);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1; panel.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1; panel.add(descriptionField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Total Marks:"), gbc);
        gbc.gridx = 1; panel.add(marksField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Course:"), gbc);
        gbc.gridx = 1; panel.add(courseCombo, gbc);

        JButton submitButton = new JButton("Create");
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;

        submitButton.addActionListener((ActionEvent e) -> {
            try {
                String assignmentId = "ASSIGN_" + System.currentTimeMillis();
                
                Assignment assignment = new Assignment(
                        assignmentId,
                        titleField.getText(),
                        (String) courseCombo.getSelectedItem(),
                        descriptionField.getText(),
                        "2025-12-30",
                        Integer.parseInt(marksField.getText())
                );
                
                dataManager.addAssignment(assignment);
                
                JOptionPane.showMessageDialog(dialog, "Assignment created successfully!");
                dialog.dispose();
                
            } catch (HeadlessException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });

        panel.add(submitButton, gbc);
        dialog.add(panel);
        dialog.setVisible(true);
    }

    // ---------------------- Display Assignments for Grading ----------------------
    private void displayAssignmentsForGrading() {
        currentView = "Grading";  // ✅ Set current view
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new String[]{"Assignment ID", "Title", "Submissions"});

        for (String courseId : teacher.getAssignedCourses()) {
            List<Assignment> assignments = dataManager.getAssignmentsByCourse(courseId);

            for (Assignment assignment : assignments) {
                tableModel.addRow(new Object[]{
                    assignment.getAssignmentId(),
                    assignment.getTitle(),
                    assignment.getSubmissions().size()
                });
            }
        }
    }

    // ---------------------- Display Student Progress ----------------------
    private void displayStudentProgress() {
        currentView = "Progress";  // ✅ Set current view
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new String[]{"Course", "Student", "Grade", "Status"});

        for (String courseId : teacher.getAssignedCourses()) {
            Course course = dataManager.getCourseById(courseId);

            if (course != null) {
                for (String studentId : course.getEnrolledStudents()) {
                    tableModel.addRow(new Object[]{
                        courseId,
                        studentId,
                        "In Progress",
                        "Active"
                    });
                }
            }
        }
    }

    // ---------------------- Logout ----------------------
    private void handleLogout() {
        this.dispose();
        new LoginFrame().setVisible(true);
    }
}