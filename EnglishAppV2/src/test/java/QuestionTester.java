import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.Choice;
import pojo.Question;
import services.QuestionService;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QuestionTester {
    private static Question question;
    private static List<Choice> choiceList;

    @BeforeAll
    public static void init() {
        question = new Question(UUID.randomUUID().toString(), "Test question", 1);
        choiceList = new ArrayList<>();
        choiceList.add(new Choice(UUID.randomUUID().toString(), "Choice a", true, question.getQuestionId()));
        choiceList.add(new Choice(UUID.randomUUID().toString(), "Choice b", false, question.getQuestionId()));
        choiceList.add(new Choice(UUID.randomUUID().toString(), "Choice c", false, question.getQuestionId()));
        choiceList.add(new Choice(UUID.randomUUID().toString(), "Choice d", false, question.getQuestionId()));
    }

    @Test
    public void testGetQuestionByInvalidId() throws SQLException {
        Question q = QuestionService.getQuestionById("0");
        assertNull(q);
    }

    @Test
    public void testGetQuestionByValidId() throws SQLException {
        Question q = QuestionService.getQuestionById("623f8c2c-7fa5-44bc-b8aa-230ab28d7de7");
        assertNotNull(q);
        assertEquals("She like ... TV", q.getContent());
        assertEquals(2, q.getCategoryId());
    }

    @Test
    public void testChoiceNumber() throws SQLException {
        Question question = QuestionService.getQuestionById("30ca5455-afcc-4766-8f05-ec9b27b0668b");
        List<Choice> choiceList = QuestionService.getChoiceListByQuestionId(question.getQuestionId());
        assertEquals(4, choiceList.size());
        assertEquals(1, choiceList.stream().filter(choice -> choice.getIsCorrect()).count());
    }

    @Test
    public void testAddInvalidQuestion() throws SQLException {
        List<Choice> choiceListClone = new ArrayList<>(choiceList);
        choiceListClone.remove(0);
        QuestionService.addQuestion(question, choiceListClone);
        Question questionTest = QuestionService.getQuestionById(question.getQuestionId());
        assertNull(questionTest);
    }

    @Test
    public void testAddValidQuestion() throws SQLException {
        QuestionService.addQuestion(question, choiceList);
        Question questionTest = QuestionService.getQuestionById(question.getQuestionId());
        assertNotNull(questionTest);
        assertEquals(question.getContent(), questionTest.getContent());
        assertEquals(question.getCategoryId(), questionTest.getCategoryId());
        assertEquals(4, QuestionService.getChoiceListByQuestionId(questionTest.getQuestionId()).size());
        assertEquals(1, QuestionService.getChoiceListByQuestionId(questionTest.getQuestionId()).stream().filter(choice -> choice.getIsCorrect()).count());
    }

    @Test
    public void testThrowValidException() throws SQLException {
        List<Choice> choiceListClone = new ArrayList<>(choiceList);
        choiceListClone.remove(1);
        choiceListClone.add(new Choice(UUID.randomUUID().toString(), "Choice e", true, question.getQuestionId()));
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            QuestionService.addQuestion(question, choiceListClone);
        });
    }
    @Test
    public void testReturnedQuestionNumber() throws SQLException {
        List<Question> questionList1 = QuestionService.getQuestionListByNumber(3);
        assertEquals(3, questionList1.size());
        List<Question> questionList2 = QuestionService.getQuestionListByNumber(10);
        assertEquals(7, questionList2.size());
    }
    @Test
    public void testReturnedRandomQuestionList() throws SQLException {
        List<Question> questionList = QuestionService.getQuestionListByNumber(3);
        assertNotEquals("This is ... question", questionList.get(0).getContent());
        assertNotEquals("She like ... TV", questionList.get(2).getContent());
    }
}
