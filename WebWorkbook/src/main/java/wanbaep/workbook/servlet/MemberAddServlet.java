package wanbaep.workbook.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        RequestDispatcher rd = request.getRequestDispatcher("/member/MemberForm.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // CharacterEncodingFilter 에서 처리
        // req.setCharacterEncoding("UTF-8");

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            ServletContext sc = this.getServletContext();
            conn = (Connection) sc.getAttribute("conn");
            statement = conn.prepareStatement(
                    "INSERT INTO MEMBERS(EMAIL,PWD,MNAME,CRE_DATE,MOD_DATE)" +
                            " VALUES (?,?,?,NOW(),NOW())");
            statement.setString(1, request.getParameter("email"));
            statement.setString(2, request.getParameter("password"));
            statement.setString(3, request.getParameter("name"));
            statement.executeUpdate();  //SQL문을 DB서버에 보낸다.

            response.sendRedirect("list");
            // redirect 는 HTML을 출력하지 않는다.
            /* //Refresh 하는 방법
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>회원등록결과</title>");
            out.println("<meta http-equiv='Refresh' content='1; url=list'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>등록 성공입니다!</p>");
            out.println("</body></html>");

            //response.addHeader("Refresh", "1;url=list");
             */
        } catch (Exception e) {
            request.setAttribute("error", e);
            RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
            rd.forward(request, response);
        } finally {
            try {if (statement != null) statement.close();} catch (Exception e) {}
        }
    }
}
