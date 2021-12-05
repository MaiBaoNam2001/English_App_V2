package pojo;

import java.util.List;

public class Question {
    private String questionId;
    private String content;
    private int categoryId;
    private List<Choice> choiceList;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Question(String questionId, String content, int categoryId) {
        this.questionId = questionId;
        this.content = content;
        this.categoryId = categoryId;
    }

    public List<Choice> getChoiceList() {
        return choiceList;
    }

    public void setChoiceList(List<Choice> choiceList) {
        this.choiceList = choiceList;
    }
}
