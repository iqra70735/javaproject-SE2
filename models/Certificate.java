package learnify.models;

import java.io.Serializable;
import java.util.Date;

public class Certificate implements Serializable {
    private static final long serialVersionUID = 1L;
    private String certificateId;
    private String studentId;
    private String courseId;
    private Date issueDate;
    private String certificateContent;

    public Certificate(String certificateId, String studentId, String courseId) {
        this.certificateId = certificateId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.issueDate = new Date();
        this.certificateContent = "";
    }

    public String getCertificateId() { return certificateId; }
    public String getStudentId() { return studentId; }
    public String getCourseId() { return courseId; }
    public Date getIssueDate() { return issueDate; }
    public String getCertificateContent() { return certificateContent; }
    public void setCertificateContent(String content) { this.certificateContent = content; }

    public void generateCertificateContent() {
        this.certificateContent = "Certificate of Completion\n" +
                                 "Student ID: " + studentId + "\n" +
                                 "Course ID: " + courseId + "\n" +
                                 "Date: " + issueDate;
    }
}