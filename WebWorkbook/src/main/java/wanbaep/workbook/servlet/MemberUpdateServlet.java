package wanbaep.workbook.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class MemberUpdateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(this.getInitParameter("driver"));
            conn = DriverManager.getConnection(
                    this.getInitParameter("url"),
                    this.getInitParameter("username"),
                    this.getInitParameter("password"));
            stmt = conn.prepareStatement("UPDATE MEMBERS SET EMAIL=?,MNAME=?,MOD_DATE=NOW()"
                    + " WHERE MNO=?");
            stmt.setString(1, request.getParameter("email"));
            stmt.setString(2, request.getParameter("name"));
            stmt.setInt(3, Integer.parseInt(request.getParameter("no")));
            stmt.executeUpdate();

            response.sendRedirect("list");
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName(this.getInitParameter("driver"));
            conn = DriverManager.getConnection(
                    this.getInitParameter("url"),
                    this.getInitParameter("username"),
                    this.getInitParameter("password"));
            stmt = conn.createStatement();
            rs = stmt.executeQuery(
                    "select MNO, EMAIL, MNAME, CRE_DATE from MEMBERS" +
                            " where MNO=" + request.getParameter("no"));
            rs.next();

            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>회원정보</title></head>");
            out.println("<body><h1>회원정보</h1>");
            out.println("<form action='update' method='post'>");
            out.println("번호: <input type='text' name='no' value'" +
                    " value='" + request.getParameter("no") + "'readonly><br>");
            out.println("이름: *<input type='text' name='name'" + " value='" + rs.getString("MNAME") + "'><br>");
            out.println("이메일: <input type='text' name='email'" + " value='" + rs.getString("EMAIL") + "'><br>");
            out.println("가입일: " + rs.getDate("CRE_DATE") + "<br>");
            out.println("<input type='submit' value='저장'>");
            out.println("<input type='button' value='취소'" + " onclick='location.href=\"list\"'>");
            out.println("</form>");
            out.println("</body></html>");
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace();}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}
