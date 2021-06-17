package wanbaep.workbook.servlet;

import wanbaep.workbook.dao.MemberDao;
import wanbaep.workbook.vo.Member;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ServletContext sc = this.getServletContext();
            MemberDao memberDao = (MemberDao) sc.getAttribute("memberDao");
            Member member = new Member()
                    .setEmail(request.getParameter("email"))
                    .setName(request.getParameter("name"))
                    .setNo(Integer.parseInt(request.getParameter("no")));
            memberDao.update(member);

            response.sendRedirect("list");
        } catch (Exception e) {
            request.setAttribute("error", e);
            RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ServletContext sc = this.getServletContext();
            MemberDao memberDao = (MemberDao) sc.getAttribute("memberDao");
            Member member = memberDao.selectOne(Integer.parseInt(request.getParameter("no")));

            response.setContentType("text/html; charset=UTF-8");
            if(member != null) {
                request.setAttribute("updateMember", member);
                RequestDispatcher rd = request.getRequestDispatcher("/member/MemberUpdateForm.jsp");
                rd.forward(request, response);
            } else {
                RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
                rd.forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", e);
            RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
            rd.forward(request, response);
        }
    }
}
