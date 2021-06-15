package wanbaep.workbook.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.sql.Connection;
import java.sql.DriverManager;

public class AppInitServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        System.out.println("AppInitServlet 준비...");
        super.init();

        try {
            ServletContext sc = this.getServletContext();
            Class.forName(sc.getInitParameter("driver"));
            Connection conn = DriverManager.getConnection(
                    sc.getInitParameter("url"),
                    sc.getInitParameter("username"),
                    sc.getInitParameter("password"));
            sc.setAttribute("conn", conn);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    public void destroy() {
        System.out.println("AppInitServlet 종료...");
        super.destroy();
        Connection conn =  (Connection) this.getServletContext().getAttribute("conn");
        try {
            if(conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {}
    }
}
