package wanbaep.workbook.listener;

import wanbaep.workbook.dao.MemberDao;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
    Connection connection;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServletContext sc = sce.getServletContext();
            Class.forName(sc.getInitParameter("driver"));
            connection = DriverManager.getConnection(
                    sc.getInitParameter("url"),
                    sc.getInitParameter("username"),
                    sc.getInitParameter("password"));
            MemberDao memberDao = new MemberDao();
            memberDao.setConnection(connection);
            sc.setAttribute("memberDao", memberDao);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
