package wanbaep.workbook.servlet;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/member/list")
public class MemberListServlet extends GenericServlet {
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost/workbook_db", //JDBC URL
                    "wanbaep",  // DBMS 사용자 아이디
                    "1234"  // DBMS 사용자 암호
            );
            statement = conn.createStatement();
            resultSet = statement.executeQuery(
                    "select MNO, MNAME, EMAIL, CRE_DATE" +
                            " from MEMBERS" +
                            " order by MNO ASC;");

            servletResponse.setContentType("text/html; charset=UTF-8");
            PrintWriter out = servletResponse.getWriter();
            out.println("<html><head><title>회원목록</title></head>");
            out.println("<body><h1>회원목록</h1>");
            while(resultSet.next()) {
                out.println(
                        resultSet.getInt("MNO") + ", " +
                        resultSet.getString("MNAME") + ", " +
                        resultSet.getString("EMAIL") + ", " +
                        resultSet.getDate("CRE_DATE") + "<br>");
            }
            out.println("</body></html>");
        } catch (Exception e) {
            throw new ServletException();
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (Exception e) {}
            try { if (resultSet != null) statement.close(); } catch (Exception e) {}
            try { if(resultSet != null) conn.close(); } catch (Exception e) {}
        }
    }
}
