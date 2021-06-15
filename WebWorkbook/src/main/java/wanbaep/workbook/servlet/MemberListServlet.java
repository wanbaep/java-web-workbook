package wanbaep.workbook.servlet;

import wanbaep.workbook.vo.Member;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

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
            ArrayList<Member> members = new ArrayList<>();

            while(resultSet.next()) {
                members.add(new Member()
                        .setNo(resultSet.getInt("MNO"))
                        .setName(resultSet.getString("MNAME"))
                        .setEmail(resultSet.getString("EMAIL"))
                        .setCreatedDate(resultSet.getDate("CRE_DATE")));
            }

            request.setAttribute("members", members);
            RequestDispatcher rd = request.getRequestDispatcher("/member/MemberList.jsp");
            rd.include(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e);
            RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
            rd.forward(request, response);  //error인 경우 forward 로 Error.jsp 서블릿에게 작업 위임
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (Exception e) {}
            try { if (statement != null) statement.close(); } catch (Exception e) {}
            try { if(conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}
