package services;

import configs.JDBCUtils;
import pojo.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    public static List<Category> getCategoryList() throws SQLException {
        List<Category> categoryList = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select * from category");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                categoryList.add(new Category(rs.getInt("CategoryId"), rs.getString("CategoryName")));
            }
        }
        return categoryList;
    }
}
