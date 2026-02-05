package learnify.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import learnify.database.DataManager;
import learnify.models.*;
import java.util.List;

public class StudentDashboard extends JFrame {
    private Student student;
    private DataManager dataManager;
    private JTable coursesTable;
    private DefaultTableModel tableModel;

    public StudentDashboard(Student student) {
        this.student = student;
        this.dataManager = DataManager.getInstance();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Learnify - Student Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(25, 78, 132));
        headerPanel.setPreferredSize(new Dimension(900, 60));
        JLabel welcomeLabel = new JLabel("Welcome, " + student.getUsername());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);
        headerPanel.add(welcomeLabel);

        JPanel navPanel = new JPanel();
        navPanel.setBackground(new Color(200, 220, 250));
        navPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton browseCoursesBtn = new JButton("Browse Courses");
        JButton myCoursesBtn = new JButton("My Courses");
        JButton myAssignmentsBtn = new JButton("My Assignments");
        JButton myQuizzesBtn = new JButton("My Quizzes");
        JButton myGradesBtn = new JButton("My Grades");
        JButton myCertificatesBtn = new JButton("My Certificates");
        JButton logoutBtn = new JButton("Logout");

        browseCoursesBtn.addActionListener(e -> displayBrowseCourses());
        myCoursesBtn.addActionListener(e -> displayMyEnrolledCourses());
        myAssignmentsBtn.addActionListener(e -> displayMyAssignments());
        myQuizzesBtn.addActionListener(e -> displayMyQuizzes());
        myGradesBtn.addActionListener(e -> displayGrades());
        myCertificatesBtn.addActionListener(e -> displayCertificates());
        logoutBtn.addActionListener(e -> handleLogout());

        navPanel.add(browseCoursesBtn);
        navPanel.add(myCoursesBtn);
        navPanel.add(myAssignmentsBtn);
        navPanel.add(myQuizzesBtn);
        navPanel.add(myGradesBtn);
        navPanel.add(myCertificatesBtn);
        navPanel.add(logoutBtn);

        JPanel contentPanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(
            new String[]{"Course ID", "Course Name", "Teacher", "Enrolled", "Action"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        coursesTable = new JTable(tableModel);
        coursesTable.setRowHeight(30);
        coursesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = coursesTable.columnAtPoint(e.getPoint());
                int row = coursesTable.rowAtPoint(e.getPoint());
                if (column == 4) {
                    handleActionButtonClick(row);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(coursesTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(navPanel, BorderLayout.PAGE_START);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
        displayBrowseCourses();
    }

    // ======================================================================
    // BROWSE COURSES  — shows Enroll OR Drop
    // ======================================================================
    private void displayBrowseCourses() {
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new String[]{
                "Course ID", "Course Name", "Teacher", "Enrolled", "Action"});

        List<Course> allCourses = dataManager.getAllCourses();

        for (Course course : allCourses) {
            boolean isEnrolled = student.getEnrolledCourses().contains(course.getCourseId());
            tableModel.addRow(new Object[]{
                    course.getCourseId(),
                    course.getCourseName(),
                    course.getTeacherId(),
                    isEnrolled ? "Yes" : "No",
                    isEnrolled ? "Drop" : "Enroll"
            });
        }
    }

    // ======================================================================
    // MY COURSES — added DROP button here also
    // ======================================================================
    private void displayMyEnrolledCourses() {
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new String[]{
                "Course ID", "Course Name", "Teacher", "Status", "Action"});

        for (String courseId : student.getEnrolledCourses()) {
            Course course = dataManager.getCourseById(courseId);
            if (course != null) {
                tableModel.addRow(new Object[]{
                        course.getCourseId(),
                        course.getCourseName(),
                        course.getTeacherId(),
                        "Enrolled",
                        "Drop"
                });
            }
        }
    }

    private void displayMyAssignments() {
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new String[]{
                "Assignment ID", "Title", "Course ID", "Due Date", "Total Marks"});

        for (String courseId : student.getEnrolledCourses()) {
            List<Assignment> assignments = dataManager.getAssignmentsByCourse(courseId);
            for (Assignment assignment : assignments) {
                tableModel.addRow(new Object[]{
                        assignment.getAssignmentId(),
                        assignment.getTitle(),
                        assignment.getCourseId(),
                        assignment.getDueDate(),
                        assignment.getTotalMarks()
                });
            }
        }
    }

    private void displayMyQuizzes() {
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new String[]{
                "Quiz ID", "Title", "Course ID", "Due Date", "Total Marks"});

        for (String courseId : student.getEnrolledCourses()) {
            List<Quiz> quizzes = dataManager.getQuizzesByCourse(courseId);
            for (Quiz quiz : quizzes) {
                tableModel.addRow(new Object[]{
                        quiz.getQuizId(),
                        quiz.getQuizTitle(),
                        quiz.getCourseId(),
                        quiz.getDueDate(),
                        quiz.getTotalMarks()
                });
            }
        }
    }

    private void displayGrades() {
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new String[]{"Course ID", "Grade", "Status"});

        for (String courseId : student.getEnrolledCourses()) {
            Double grade = student.getGrades().get(courseId);
            String gradeValue = grade != null ? String.format("%.2f", grade) : "Not Graded";
            String status = grade != null ? (grade >= 40 ? "Pass" : "Fail") : "Pending";
            tableModel.addRow(new Object[]{courseId, gradeValue, status});
        }
    }

    private void displayCertificates() {
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new String[]{"Certificate ID", "Course", "Issue Date"});

        List<Certificate> certificates = dataManager.getCertificatesByStudent(student.getUserId());
        for (Certificate cert : certificates) {
            tableModel.addRow(new Object[]{
                    cert.getCertificateId(),
                    cert.getCourseId(),
                    cert.getIssueDate()
            });
        }
    }

    // ======================================================================
    // HANDLE ENROLL / DROP CLICK
    // ======================================================================
    private void handleActionButtonClick(int row) {
        if (row < 0) return;

        String courseId = (String) tableModel.getValueAt(row, 0);
        String action = tableModel.getColumnCount() > 4
                ? (String) tableModel.getValueAt(row, 4)
                : "Drop";

        Course course = dataManager.getCourseById(courseId);
        if (course == null) {
            JOptionPane.showMessageDialog(this, "Course not found!");
            return;
        }

        if (action.equals("Enroll")) {
            student.enrollCourse(courseId);
            course.enrollStudent(student.getUserId());
            dataManager.updateUser(student);
            dataManager.updateCourse(course);
            JOptionPane.showMessageDialog(this, "Enrolled in " + course.getCourseName());
            displayBrowseCourses();
        }

        if (action.equals("Drop")) {

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to withdraw from " + course.getCourseName() + "?",
                    "Confirm Withdraw",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                student.dropCourse(courseId);
                course.removeStudent(student.getUserId());
                dataManager.updateUser(student);
                dataManager.updateCourse(course);

                JOptionPane.showMessageDialog(this,
                        "You have withdrawn from " + course.getCourseName());

                displayBrowseCourses();
            }
        }
    }

    private void handleLogout() {
        this.dispose();
        new LoginFrame().setVisible(true);
    }
}
