package pojo;

public class Choice {
    private String choiceId;
    private String content;
    private boolean isCorrect;
    private String questionId;

    public String getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(String choiceId) {
        this.choiceId = choiceId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Choice(String choiceId, String content, boolean isCorrect, String questionId) {
        this.choiceId = choiceId;
        this.content = content;
        this.isCorrect = isCorrect;
        this.questionId = questionId;
    }
}
