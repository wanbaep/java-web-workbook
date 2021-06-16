package wanbaep.workbook.servlet;

import wanbaep.workbook.vo.Member;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            ServletContext sc = this.getServletContext();
            conn = (Connection) sc.getAttribute("conn");
            stmt = conn.prepareStatement("UPDATE MEMBERS SET EMAIL=?,MNAME=?,MOD_DATE=NOW()"
                    + " WHERE MNO=?");
            stmt.setString(1, request.getParameter("email"));
            stmt.setString(2, request.getParameter("name"));
            stmt.setInt(3, Integer.parseInt(request.getParameter("no")));
            stmt.executeUpdate();

            response.sendRedirect("list");
        } catch (Exception e) {
            request.setAttribute("error", e);
            RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
            rd.forward(request, response);
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            ServletContext sc = this.getServletContext();
            conn = (Connection) sc.getAttribute("conn");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(
                    "select MNO, EMAIL, MNAME, CRE_DATE from MEMBERS" +
                            " where MNO=" + request.getParameter("no"));

            response.setContentType("text/html; charset=UTF-8");
            if(rs.next()) {
                Member member = new Member()
                        .setNo(rs.getInt("MNO"))
                        .setName(rs.getString("MNAME"))
                        .setEmail(rs.getString("EMAIL"))
                        .setCreatedDate(rs.getDate("CRE_DATE"));
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
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace();}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
        }
    }
}
