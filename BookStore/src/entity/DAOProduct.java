package entity;

import model.Book;
import model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DAOProduct {
    Connection connection;

    public DAOProduct(Connection connection) {
        this.connection = connection;
    }

    public int addProduct(Book book) {
        int n = 0;
        String sql = "INSERT INTO book(category_id, status_id, author_id, name, content_summary, publish_year, price," +
                " quantity, size, [weight]) VALUES (?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, book.getCategory_id());
            preparedStatement.setInt(2, book.getStatus_id());
            preparedStatement.setInt(3, book.getAuthor_id());
            preparedStatement.setString(4, book.getName());
            preparedStatement.setString(5, book.getContent_summary());
            preparedStatement.setInt(6, book.getPublish_year());
            preparedStatement.setDouble(7, book.getPrice());
            preparedStatement.setInt(8, book.getQuantity());
            preparedStatement.setString(9, book.getSize());
            preparedStatement.setString(10, book.getWeight());

            n = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }

    public int editProduct(Book book) {
        int n = 0;
        String sql = "UPDATE book SET category_id=?, status_id=?, author_id=?, name=?, content_summary=?, " +
                "publish_year=?, price=?,quantity=?, size=?, [weight]=? WHERE id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, book.getCategory_id());
            preparedStatement.setInt(2, book.getStatus_id());
            preparedStatement.setInt(3, book.getAuthor_id());
            preparedStatement.setString(4, book.getName());
            preparedStatement.setString(5, book.getContent_summary());
            preparedStatement.setInt(6, book.getPublish_year());
            preparedStatement.setDouble(7, book.getPrice());
            preparedStatement.setInt(8, book.getQuantity());
            preparedStatement.setString(9, book.getSize());
            preparedStatement.setString(10, book.getWeight());
            preparedStatement.setInt(11, book.getId());

            n = preparedStatement.executeUpdate();
            System.out.println("Edited successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }

    public int removeProduct(int id) {
        int n = 0;
        String sql = "DELETE FROM book WHERE id=" + id;

        try {
            Statement statement = connection.createStatement();
            n = statement.executeUpdate(sql);
            System.out.println("Delete successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }

    public void showABook(int idToShow) {
        String sql = "SELECT * FROM book WHERE id=" + idToShow;
        Statement statement;



        try {
//            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

//            if (!checkExistBook(idToShow).equals("")) {
//                ResultSet resultSet = statement.executeQuery(sql);
                ResultSet resultSet = getData(sql);

                showBook(resultSet);
//            } else {
//                System.out.println("Book not Found!");
//            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // display all books
    public void showAllBook() {
        String sql = "SELECT * FROM book";
//        Statement statement;
        ResultSet resultSet = getData(sql);

        try {
//            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            ResultSet resultSet = statement.executeQuery(sql);

            showBook(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // get books from database - extracted method
    private void showBook(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int categoryId = resultSet.getInt("category_id");
            int statusId = resultSet.getInt("status_id");
            int authorId = resultSet.getInt("author_id");
            String name = resultSet.getString("name");
            String contentSummary = resultSet.getString("content_summary");
            int publishYear = resultSet.getInt("publish_year");
            double price = resultSet.getDouble("price");
            int quantity = resultSet.getInt("quantity");
            String size = resultSet.getString("size");
            String weight = resultSet.getString("weight");

            System.out.println("ID: " + id + "\t | \t Category ID: " + categoryId + "\t | \t Status ID: " + statusId + "\t | \t Author ID: " + authorId + "\t | \t Name: " + name + "\t | \t Content Summary: " + contentSummary + "\t | \t Publish Year: " + publishYear + "\t | \t Price: " + price + "\t | \t Quantity: " + quantity + "\t | \t Size: " + size + "\t | \t Weight: " + weight);
        }
    }

    // check existed book by id
    public String checkExistBook(int id) {
        String sql = "SELECT * FROM book WHERE id=" + id;
        return getStringCheckBook(sql);
    }

    // check existed book by name
    public String checkExistBookByName(String name) {
        String sql = "SELECT * FROM book WHERE name='" + name + "'";
        return getStringCheckBook(sql);
    }

    public String checkExistCategory(String name) {
        String sql = "SELECT * FROM category WHERE name='" + name + "'";
        return getStringCheckBook(sql);
    }

    // get book name from database - extracted method
    private String getStringCheckBook(String sql) {
        String strToCheck = "";
        ResultSet resultSet = getData(sql);

        try {
//            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
//            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
//                int idToCheck = resultSet.getInt("id");
                resultSet.getString("name");
                strToCheck = resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return strToCheck;
    }

    // display category id from database
    public List<Integer> checkBookCategory() {
        String sql = "SELECT id FROM category";
        List<Integer> categoryIdToCheck = new ArrayList<>();

//        Statement statement = null;

        ResultSet resultSet = getData(sql);
        try {
//            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
//            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                categoryIdToCheck.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryIdToCheck;
    }

    // add a new category
    public int addCategory(Category category) {
        int n = 0;
        String sql = "INSERT INTO category(name) VALUES (?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, category.getName());

            n = preparedStatement.executeUpdate();
            System.out.println("Added category successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }

    // search book by it's name
    public Vector<Book> searchByName(String nameToSearch) {
        Vector<Book> vector = new Vector<Book>();
        String sql = "SELECT * FROM book WHERE name LIKE '%" + nameToSearch + "%'";

        return getBooks(vector, sql);
    }

    // get category name by it's id
    public int getCategoryID(String categoryName) {
        String sql = "SELECT * FROM category WHERE name = '" + categoryName + "'";
        int categoryID = 0;

        ResultSet resultSet = getData(sql);
        try {
//            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                categoryID = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryID;
    }

    // filter books by their category
    public Vector<Book> filterByType(String categoryName) {
        Vector<Book> vector = new Vector<Book>();

        String sql = "SELECT * FROM book WHERE category_id = " + getCategoryID(categoryName);
        return getBooks(vector, sql);

    }

    // get a vector of books from database
    private Vector<Book> getBooks(Vector<Book> vector, String sql) {
        ResultSet resultSet = getData(sql);

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int categoryId = resultSet.getInt("category_id");
                int statusId = resultSet.getInt("status_id");
                int authorId = resultSet.getInt("author_id");
                String name = resultSet.getString("name");
                String contentSummary = resultSet.getString("content_summary");
                int publishYear = resultSet.getInt("publish_year");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                String size = resultSet.getString("size");
                String weight = resultSet.getString("weight");

                Book book = new Book(id, categoryId, statusId, authorId, name, contentSummary, publishYear, price
                        , quantity, size, weight);

                vector.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vector;
    }



    // get category name by id
    public String getCategory(int id) {
        String sql = "SELECT * FROM category WHERE id = " + id;
        return getString(sql);
    }

    // get author name by id
    public String getAuthorName(int id) {
        String sql = "SELECT * FROM author WHERE id = " + id;
        return getString(sql);
    }

    // get name by id - extracted method (category and author are used)
    private String getString(String sql) {
        String authorName = "";
        ResultSet resultSet = getData(sql);
        try {
//            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
//            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                authorName = resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return authorName;
    }

    // get data from sql server
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
