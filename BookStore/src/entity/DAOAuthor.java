package entity;

import model.Author;

import java.sql.*;

public class DAOAuthor {
    Connection connection;

    public DAOAuthor(Connection connection) {
        this.connection = connection;
    }

//    public DAOAuthor(Connection connection) {
//        this.connection = connection;
//    }

    public int addAuthor(Author author) {
        int n = 0;
        String sql = "INSERT INTO author(name, birth_date) VALUES (?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getBirth_date());

            n = preparedStatement.executeUpdate();
            System.out.println("Added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }

    public int editAuthor(Author author) {
        int n = 0;
        String sql = "UPDATE author SET name=?,birth_date=? WHERE id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getBirth_date());
            preparedStatement.setInt(3, author.getId());

            n = preparedStatement.executeUpdate();
            System.out.println("Edited successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }

    public int removeAuthor(int id) {
        int n = 0;
        String sql = "DELETE FROM author WHERE id=" + id;
        try {
            Statement statement = connection.createStatement();

            n = statement.executeUpdate(sql);
            System.out.println("Deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }

    public void showAnAuthor(int idToShow) {
        String sql = "SELECT * FROM author WHERE id=" + idToShow;
        Statement statement;

        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            if (!checkExistedAuthor(idToShow).equals("")) {
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String birth_date = resultSet.getString("birth_date");

                    System.out.println("ID: " + id + "\t | \t Name: " + name + "\t | \t Birth Date:" + birth_date);
                }
            } else {
                System.out.println("Author not Found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void showAllAuthor() {
        String sql = "SELECT * FROM author";
        Statement statement;

        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
//                String birth_date = resultSet.getString("birth_date");
                String birth_date = formatDate(resultSet.getString("birth_date"));

                System.out.println("ID: " + id + "\t | \t Name: " + name + "\t | \t Birth Date: " + birth_date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String checkExistedAuthor(int id) {
        String sql = "SELECT * FROM author WHERE id=" + id;
        String strToCheck = "";

        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(sql);

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

    public Author checkDuplicateAuthor() {
        String sql = "SELECT * FROM author";
        Author author = null;

        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String birth_date = formatDate(resultSet.getString("birth_date"));

                author = new Author(id, name, birth_date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return author;
    }

    public ResultSet getData(String sql) {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery(sql);
            System.out.println(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    // dumb
    public String dumb() {
        String sql = "SELECT CONVERT(varchar(10), cast(birth_date AS DATE), 103) AS date_of_birth FROM author";
        String birthDate = "";

        ResultSet resultSet = getData(sql);

        try {
            while (resultSet.next()) {
                birthDate = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return birthDate;
    }

    public String formatDate(String date) {
//        return String.join("/", (Collections.reverse(Arrays.asList(date.split("-")))));
//        System.out.println(Arrays.toString(date.split("-")));;

         String[] arrStr = date.split("-");

        for(int i=0; i<arrStr.length/2; i++){
            String temp = arrStr[i];
            arrStr[i] = arrStr[arrStr.length -i -1];
            arrStr[arrStr.length -i -1] = temp;
        }

        return String.join("/", arrStr);
    }
}
