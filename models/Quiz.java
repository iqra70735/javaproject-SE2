package learnify.models;

import java.io.Serializable;
import java.util.*;

public class Quiz implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String quizId;
    private final String quizTitle;
    private final String courseId;
    private final List<String> questions;
    private final Map<String, String> answers;
    private final int totalMarks;
    private final String dueDate;
    private boolean published;

    public Quiz(String quizId, String quizTitle, String courseId, 
                String teacherId, int totalMarks, String dueDate) {
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.courseId = courseId;
        this.questions = new ArrayList<>();
        this.answers = new HashMap<>();
        this.totalMarks = totalMarks;
        this.dueDate = dueDate;
        this.published = false;
    }

    public void addQuestion(String question) { questions.add(question); }
    public void setAnswer(String questionId, String answer) { answers.put(questionId, answer); }
    public void publishQuiz() { this.published = true; }

    public String getQuizId() { return quizId; }
    public String getQuizTitle() { return quizTitle; }
    public String getCourseId() { return courseId; }
    public List<String> getQuestions() { return questions; }
    public Map<String, String> getAnswers() { return answers; }
    public int getTotalMarks() { return totalMarks; }
    public String getDueDate() { return dueDate; }
    public boolean isPublished() { return published; }

    public Object getTitle() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
