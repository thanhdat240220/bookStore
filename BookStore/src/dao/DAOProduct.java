package dao;

import controller.BaseController;
import model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DAOProduct extends BaseController {
    Connection connection;

    public DAOProduct(Connection connection) {
        super(connection);
        this.connection = connection;
    }

    public int addProduct(Book book) {
        int n = 0;
        String sql = "INSERT INTO book(category_id, status_id, name, content_summary, publish_year, price," +
                " quantity, size, [weight]) VALUES (?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, book.getCategory_id());
            preparedStatement.setInt(2, book.getStatus_id());
            preparedStatement.setString(3, book.getName());
            preparedStatement.setString(4, book.getContent_summary());
            preparedStatement.setInt(5, book.getPublish_year());
            preparedStatement.setDouble(6, book.getPrice());
            preparedStatement.setInt(7, book.getQuantity());
            preparedStatement.setString(8, book.getSize());
            preparedStatement.setString(9, book.getWeight());

            n = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }

    public int editProduct(Book book) {
        int n = 0;
        String sql = "UPDATE book SET category_id=?, status_id=?, name=?, content_summary=?, " +
                "publish_year=?, price=?,quantity=?, size=?, [weight]=? WHERE id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, book.getCategory_id());
            preparedStatement.setInt(2, book.getStatus_id());
            preparedStatement.setString(3, book.getName());
            preparedStatement.setString(4, book.getContent_summary());
            preparedStatement.setInt(5, book.getPublish_year());
            preparedStatement.setDouble(6, book.getPrice());
            preparedStatement.setInt(7, book.getQuantity());
            preparedStatement.setString(8, book.getSize());
            preparedStatement.setString(9, book.getWeight());
            preparedStatement.setInt(10, book.getId());

            n = preparedStatement.executeUpdate();
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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }

    public void showABook(int idToShow) {
        String sql = "SELECT * FROM book WHERE id=" + idToShow;

        try {
            ResultSet resultSet = getData(sql);
            showBook(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // display all books
    public void showAllBook() {
        String sql = "SELECT * FROM book";
        ResultSet resultSet = getData(sql);

        try {
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
            String name = resultSet.getString("name");
            String contentSummary = resultSet.getString("content_summary");
            int publishYear = resultSet.getInt("publish_year");
            double price = resultSet.getDouble("price");
            int quantity = resultSet.getInt("quantity");
            String size = resultSet.getString("size");
            String weight = resultSet.getString("weight");

            makeMenuHeader("Show a Book by ID");
            makeMenuRow("ID: " + id);
            makeMenuRow("Category ID: " + categoryId);
            makeMenuRow("Status ID: " + statusId);
            makeMenuRow("Name: " + name);
            makeMenuRow("Content Summary: " + contentSummary);
            makeMenuRow("Publish Year: " + publishYear);
            makeMenuRow("Price: " + price);
            makeMenuRow("Quantity: " + quantity);
            makeMenuRow("Size: " + size);
            makeMenuRow("Weight: " + weight);
            makeMenuRow("--------------------------");
            makeMenuFooter();
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
            while (resultSet.next()) {
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
        ResultSet resultSet = getData(sql);
        try {
            while (resultSet.next()) {
                categoryIdToCheck.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryIdToCheck;
    }

//    public ArrayList<Book> getBooksHasAuthor(String wheres) {
//        if (Empty(wheres)) {
//            wheres = "(1=1)";
//        }
//        String sql = "SELECT b.*,a.id AS author_id,a.name AS author_name FROM book b  INNER JOIN book_author ba ON ba" +
//                ".book_id = b.id LEFT JOIN author a ON a.id = ba.author_id WHERE " + wheres;
//
//        ResultSet resultSet = getData(sql);
//
//        ArrayList<Author> authors = new ArrayList<>();
//
//        try {
//            while (resultSet.next()) {
//                Book book = new Book();
//                Author author = new Author();
//                book.setId(resultSet.getInt("Id"));
//                book.setAuthor_id(resultSet.getInt("Author_id"));
//                book.setName(resultSet.getString("Name"));
//                book.setCategory_id(resultSet.getInt("Category_id"));
//                book.setContent_summary(resultSet.getString("Content_summary"));
//                book.setPrice(resultSet.getDouble("Price"));
//                book.setPublish_year(resultSet.getInt("Publish_year"));
//                book.setQuantity(resultSet.getInt("Quantity"));
//                book.setWeight(resultSet.getString("Weight"));
//                book.setSize(resultSet.getString("Size"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
////        public Vector<Book> searchByName (String nameToSearch){
////            String sql = "SELECT * FROM book WHERE name LIKE '%" + nameToSearch + "%'";
////            ResultSet resultSet = getData(sql);
////            Vector<Book> vector = new Vector<>();
////            ArrayList<Author> authors = new ArrayList<>();
//
//            try {
//                while (resultSet.next()) {
//                    int id = resultSet.getInt("id");
//                    int categoryId = resultSet.getInt("category_id");
//                    int statusId = resultSet.getInt("status_id");
//                    int authorId = resultSet.getInt("author_id");
//                    String name = resultSet.getString("name");
//                    String contentSummary = resultSet.getString("content_summary");
//                    int publishYear = resultSet.getInt("publish_year");
//                    double price = resultSet.getDouble("price");
//                    int quantity = resultSet.getInt("quantity");
//                    String size = resultSet.getString("size");
//                    String weight = resultSet.getString("weight");
//
//                    Book book = new Book(id, categoryId, statusId, authorId, name, contentSummary, publishYear, price
//                            , quantity, size, weight);
//
//                    vector.add(book);
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            return vector;
//        }

        // get a vector of books from database
        private Vector<Book> getBooks (Vector < Book > vector, String sql){
            ResultSet resultSet = getData(sql);

            try {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int categoryId = resultSet.getInt("category_id");
                    int statusId = resultSet.getInt("status_id");
                    String name = resultSet.getString("name");
                    String contentSummary = resultSet.getString("content_summary");
                    int publishYear = resultSet.getInt("publish_year");
                    double price = resultSet.getDouble("price");
                    int quantity = resultSet.getInt("quantity");
                    String size = resultSet.getString("size");
                    String weight = resultSet.getString("weight");

                    Book book = new Book(id, categoryId, statusId, name, contentSummary, publishYear, price
                            , quantity, size, weight);

                    vector.add(book);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return vector;
        }


        // get category name by id
        public String getCategory ( int id){
            String sql = "SELECT * FROM category WHERE id = " + id;
            return getString(sql);
        }

        // get author name by id
        public String getAuthorName ( int id){
            String sql = "SELECT * FROM author WHERE id = " + id;
            return getString(sql);
        }

        // get name by id - extracted method (category and author are used)
        private String getString (String sql){
            String authorName = "";
            ResultSet resultSet = getData(sql);
            try {
                while (resultSet.next()) {
                    authorName = resultSet.getString("name");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return authorName;
        }

        // get data from sql server
        public ResultSet getData (String sql){
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
