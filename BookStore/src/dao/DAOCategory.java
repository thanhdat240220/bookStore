package dao;

import controller.BaseController;
import model.Category;

import java.sql.*;

public class DAOCategory extends BaseController {

    Connection connection;

    public DAOCategory(Connection connection) {
        super(connection);
        this.connection = connection;
    }

    public int addCategory(Category category) {
        int n = 0;
        String sql = "INSERT INTO category(name) VALUES (?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, category.getName());

            n = preparedStatement.executeUpdate();
            System.out.println("Added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }

    public int editCategory(Category category) {
        int n = 0;
        String sql = "UPDATE category SET name=? WHERE id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, category.getName());
            preparedStatement.setInt(2, category.getId());

            n = preparedStatement.executeUpdate();
            System.out.println("Edited successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }

    public int removeCategory(int id) {
        int n = 0;
        String sql = "DELETE FROM category WHERE id=" + id;
        try {
            Statement statement = connection.createStatement();

            n = statement.executeUpdate(sql);
            System.out.println("Deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Deleted fail!");
        }

        return n;
    }

    public void showACategory(int idToShow) {
        String sql = "SELECT * FROM category WHERE id=" + idToShow;

        if (!checkExistedCategory(idToShow).equals("")) {
            showCategory(sql);

        } else {
            makeRow("Category not Found!");
        }
    }

    public void showAllCategories() {
        String sql = "SELECT * FROM category";
        showCategory(sql);
    }

    public void showCategory(String sql) {
        ResultSet resultSet = getData(sql);
        makeMenuHeader("Show all Categories");

        try {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                makeRow("ID: " + id);
                makeRow("Name: " + name);
                makeRow("--------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        makeMenuFooter();

    }

    public boolean checkCategoryHasBook(int id) {
        String sql = "SELECT * FROM book WHERE category_id=" + id;
        ResultSet resultSet = getData(sql);

        try {
            while (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String checkExistedCategory(int id) {
        String sql = "SELECT * FROM category WHERE id=" + id;
        String strToCheck = "";
        ResultSet resultSet = getData(sql);

        try {
            while (resultSet.next()) {
                resultSet.getString("name");
                strToCheck = resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return strToCheck;
    }

    public Category checkDuplicateCategory() {
        String sql = "SELECT * FROM category";
        Category category = null;
        ResultSet resultSet = getData(sql);

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                category = new Category(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }

    public ResultSet getData(String sql) {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }
}
