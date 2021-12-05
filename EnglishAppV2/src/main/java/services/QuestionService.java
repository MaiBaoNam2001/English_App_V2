package services;

import configs.JDBCUtils;
import pojo.Choice;
import pojo.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionService {
    private static final int CHOICE_NUMBER = 4;

    public static void addQuestion(Question question, List<Choice> choiceList) throws SQLException {
        if (choiceList.size() == CHOICE_NUMBER) {
            if (choiceList.stream().filter(choice -> choice.getIsCorrect()).count() != 1)
                throw new SQLIntegrityConstraintViolationException();
            else {
                try (Connection connection = JDBCUtils.getConnection()) {
                    connection.setAutoCommit(false);
                    PreparedStatement ps1 = connection.prepareStatement("insert into question values (?,?,?)");
                    ps1.setString(1, question.getQuestionId());
                    ps1.setString(2, question.getContent());
                    ps1.setInt(3, question.getCategoryId());
                    if (ps1.executeUpdate() > 0) {
                        for (Choice choice : choiceList) {
                            PreparedStatement ps2 = connection.prepareStatement("insert into choice values (?,?,?,?)");
                            ps2.setString(1, choice.getChoiceId());
                            ps2.setString(2, choice.getContent());
                            ps2.setBoolean(3, choice.getIsCorrect());
                            ps2.setString(4, choice.getQuestionId());
                            ps2.executeUpdate();
                        }
                    }
                    connection.commit();
                }
            }
        }
    }

    public static List<Question> getQuestionListByKeyword(String keyword) throws SQLException {
        List<Question> questionList = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            String sqlQuery = "select * from question";
            if (keyword != null && !keyword.isEmpty())
                sqlQuery += " where content like concat('%',?,'%')";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            if (keyword != null && !keyword.isEmpty())
                ps.setString(1, keyword);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                questionList.add(new Question(rs.getString("QuestionId"), rs.getString("Content"), rs.getInt("CategoryId")));
            }
        }
        return questionList;
    }

    public static Question getQuestionById(String questionId) throws SQLException {
        Question question = null;
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select * from question where QuestionId = ?");
            ps.setString(1, questionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                question = new Question(rs.getString("QuestionId"), rs.getString("Content"), rs.getInt("CategoryId"));
                break;
            }
        }
        return question;
    }

    public static List<Choice> getChoiceListByQuestionId(String questionId) throws SQLException {
        List<Choice> choiceList = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select * from choice where QuestionId = ?");
            ps.setString(1, questionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                choiceList.add(new Choice(rs.getString("ChoiceId"), rs.getString("Content"), rs.getBoolean("IsCorrect"), rs.getString("QuestionId")));
            }
        }
        return choiceList;
    }

    public static List<Question> getQuestionListByNumber(int number) throws SQLException {
        List<Question> questionList = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select * from question order by rand() limit ?");
            ps.setInt(1, number);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                questionList.add(new Question(rs.getString("QuestionId"), rs.getString("Content"), rs.getInt("CategoryId")));
            }
        }
        return questionList;
    }
}
