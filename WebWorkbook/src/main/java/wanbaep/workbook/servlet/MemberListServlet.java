package wanbaep.workbook.servlet;

import wanbaep.workbook.dao.MemberDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ServletContext sc = this.getServletContext();
            MemberDao memberDao = (MemberDao) sc.getAttribute("memberDao");
            request.setAttribute("members", memberDao.selectList());
            response.setContentType("text/html; charset=UTF-8");
            RequestDispatcher rd = request.getRequestDispatcher("/member/MemberList.jsp");
            rd.include(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e);
            RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
            rd.forward(request, response);  //error인 경우 forward 로 Error.jsp 서블릿에게 작업 위임
        }
    }
}
