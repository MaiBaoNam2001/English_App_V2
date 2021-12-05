import configs.JDBCUtils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import pojo.Category;
import services.CategoryService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CategoryTester {
    @Test
    public void testUniqueNames() throws SQLException {
        List<Category> categoryList = CategoryService.getCategoryList();
        List<String> actual = new ArrayList<>();
        categoryList.forEach(category -> actual.add(category.getCategoryName()));
        Set<String> expected = new HashSet<>(actual);
        assertEquals(expected.size(), actual.size());
    }
}
