package wanbaep.workbook.controls;

import wanbaep.workbook.dao.MemberDao;
import wanbaep.workbook.vo.Member;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class LogInController implements Controller {
    @Override
    public String execute(Map<String, Object> model) throws Exception {
        if(model.get("loginInfo") == null) {
            return "/auth/LogInForm.jsp";
        } else {
            MemberDao memberDao = (MemberDao) model.get("memberDao");
            Member member = (Member) model.get("loginInfo");
            member = memberDao.exist(member.getEmail(), member.getPassword());
            if(member != null) {
                HttpSession session = (HttpSession) model.get("session");
                session.setAttribute("member", member);
                return "redirect:../member/list.do";
            } else {
                return "/auth/LogInFail.jsp";
            }
        }
    }
}
