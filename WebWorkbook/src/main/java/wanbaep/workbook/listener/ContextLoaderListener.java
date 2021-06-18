package wanbaep.workbook.listener;

import wanbaep.workbook.dao.MemberDao;
import wanbaep.workbook.util.DBConnectionPool;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
    DBConnectionPool connectionPool;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServletContext sc = sce.getServletContext();
            connectionPool = new DBConnectionPool(
                    sc.getInitParameter("driver"),
                    sc.getInitParameter("url"),
                    sc.getInitParameter("username"),
                    sc.getInitParameter("password"));

            MemberDao memberDao = new MemberDao();
            memberDao.setConnectionPool(connectionPool);
            sc.setAttribute("memberDao", memberDao);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        connectionPool.closeAll();
    }
}
