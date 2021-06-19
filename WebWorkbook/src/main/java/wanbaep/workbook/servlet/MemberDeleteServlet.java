package wanbaep.workbook.servlet;

import wanbaep.workbook.dao.MemberDao;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/member/delete")
public class MemberDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ServletContext sc = this.getServletContext();
            MemberDao memberDao = (MemberDao) sc.getAttribute("memberDao");

            int no = Integer.parseInt(request.getParameter("no"));
            int result = memberDao.delete(no);
            System.out.println("delete result : " + result);
            request.setAttribute("viewUrl", "redirect:list.do");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
