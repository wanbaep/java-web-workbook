package wanbaep.workbook.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<html><head><title>회원 등록</title></head>");
        out.println("<body><h1>회원 등록</h1>");
        out.println("<form action='add' method='post'>");
        out.println("이름: <input type='text' name='name'><br>");
        out.println("이메일: <input type='text' name='email'><br>");
        out.println("암호: <input type='password' name='password'><br>");
        out.println("<input type='submit' value='추가'>");
        out.println("<input type='reset' value='취소'");
        out.println("<input type='reset' value='취소'>");
        out.println("</form>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // CharacterEncodingFilter 에서 처리
        // req.setCharacterEncoding("UTF-8");

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            ServletContext sc = this.getServletContext();
            Class.forName(sc.getInitParameter("driver"));
            conn = DriverManager.getConnection(
                    sc.getInitParameter("url"),
                    sc.getInitParameter("username"),
                    sc.getInitParameter("password"));
            statement = conn.prepareStatement(
                    "INSERT INTO MEMBERS(EMAIL,PWD,MNAME,CRE_DATE,MOD_DATE)" +
                            " VALUES (?,?,?,NOW(),NOW())");
            statement.setString(1, req.getParameter("email"));
            statement.setString(2, req.getParameter("password"));
            statement.setString(3, req.getParameter("name"));
            statement.executeUpdate();  //SQL문을 DB서버에 보낸다.

            resp.sendRedirect("list");
            // redirect 는 HTML을 출력하지 않는다.
            /*
            resp.setContentType("text/html; charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<html><head><title>회원등록결과</title>");
            out.println("<meta http-equiv='Refresh' content='1; url=list'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>등록 성공입니다!</p>");
            out.println("</body></html>");

            //resp.addHeader("Refresh", "1;url=list");
             */
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {if (statement != null) statement.close();} catch (Exception e) {}
            try {if (conn != null) conn.close();} catch (Exception e) {}
        }
    }
}
