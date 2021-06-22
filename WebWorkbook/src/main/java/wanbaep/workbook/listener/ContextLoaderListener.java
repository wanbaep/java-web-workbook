package wanbaep.workbook.listener;

import org.apache.commons.dbcp.BasicDataSource;
import wanbaep.workbook.controls.*;
import wanbaep.workbook.dao.MySqlMemberDao;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
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

            MySqlMemberDao memberDao = new MySqlMemberDao();
            memberDao.setDataSource(ds);

            sc.setAttribute("/auth/login.do", new LogInController().setMemberDao(memberDao));
            sc.setAttribute("/auth/logout.do", new LogOutController());
            sc.setAttribute("/member/list.do", new MemberListController().setMemberDao(memberDao));
            sc.setAttribute("/member/add.do", new MemberAddController().setMemberDao(memberDao));
            sc.setAttribute("/member/update.do", new MemberUpdateController().setMemberDao(memberDao));
            sc.setAttribute("/member/delete.do", new MemberDeleteController().setMemberDao(memberDao));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try { if (ds != null) ds.close(); } catch (SQLException e) {}
    }
}
