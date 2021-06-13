package wanbaep.workbook.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/member/delete")
public class MemberDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            ServletContext sc = this.getServletContext();
            Class.forName(sc.getInitParameter("driver"));
            conn = DriverManager.getConnection(
                    sc.getInitParameter("url"),
                    sc.getInitParameter("username"),
                    sc.getInitParameter("password"));
            stmt = conn.prepareStatement("DELETE FROM MEMBERS WHERE MNO=?");
            stmt.setInt(1, Integer.parseInt(req.getParameter("no")));
            stmt.executeUpdate();

            resp.sendRedirect("list");
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try { if(stmt != null) stmt.close(); } catch (Exception e) {}
            try { if(conn != null) stmt.close(); } catch (Exception e) {}
        }

    }
}
