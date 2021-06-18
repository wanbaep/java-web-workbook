package wanbaep.workbook.listener;

import org.apache.commons.dbcp.BasicDataSource;
import wanbaep.workbook.dao.MemberDao;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.SQLException;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
    BasicDataSource ds;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServletContext sc = sce.getServletContext();

            InitialContext initialContext = new InitialContext();
            DataSource ds = (DataSource) initialContext.lookup(
                    "java:comp/env/jdbc/workbook_db");

            MemberDao memberDao = new MemberDao();
            memberDao.setDataSource(ds);
            sc.setAttribute("memberDao", memberDao);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try { if (ds != null) ds.close(); } catch (SQLException e) {}
    }
}
