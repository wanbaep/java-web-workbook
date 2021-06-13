package wanbaep.workbook.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            ServletContext sc = this.getServletContext();
            Class.forName(sc.getInitParameter("driver"));
            conn = DriverManager.getConnection(
                    sc.getInitParameter("url"), //JDBC URL
                    sc.getInitParameter("username"),  // DBMS 사용자 아이디
                    sc.getInitParameter("password")  // DBMS 사용자 암호
            );
            statement = conn.createStatement();
            resultSet = statement.executeQuery(
                    "select MNO, MNAME, EMAIL, CRE_DATE" +
                            " from MEMBERS" +
                            " order by MNO ASC;");

            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>회원목록</title></head>");
            out.println("<body><h1>회원목록</h1>");
            out.println("<p><a href='add'>신규 회원</a></p>");
            while(resultSet.next()) {
                out.println(
                        resultSet.getInt("MNO") + ", " +
                                "<a href='update?no=" + resultSet.getInt("MNO") + "'>" +
                                resultSet.getString("MNAME") + "</a>, " +
                                resultSet.getString("EMAIL") + ", " +
                                resultSet.getDate("CRE_DATE") +
                                "<a href='delete?no=" + resultSet.getInt("MNO") + "'>[삭제]</a>" + "<br>");
            }
            out.println("</body></html>");
        } catch (Exception e) {
            throw new ServletException();
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (Exception e) {}
            try { if (statement != null) statement.close(); } catch (Exception e) {}
            try { if(conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}
