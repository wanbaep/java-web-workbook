package wanbaep.workbook.servlet;

import wanbaep.workbook.dao.MemberDao;
import wanbaep.workbook.vo.Member;

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
            Member member = (Member) request.getAttribute("member");
            memberDao.update(member);
            request.setAttribute("viewUrl", "redirect:list.do");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ServletContext sc = this.getServletContext();
            MemberDao memberDao = (MemberDao) sc.getAttribute("memberDao");
            Member member = memberDao.selectOne(Integer.parseInt(request.getParameter("no")));

            if(member != null) {
                request.setAttribute("updateMember", member);
                request.setAttribute("viewUrl", "/member/MemberUpdateForm.jsp");
            } else {
                throw new ServletException("member not found");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
