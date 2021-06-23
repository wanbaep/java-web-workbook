package wanbaep.workbook.controls;

import wanbaep.workbook.bind.DataBinding;
import wanbaep.workbook.dao.MemberDao;
import wanbaep.workbook.vo.Member;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class LogInController implements Controller, DataBinding {
    MemberDao memberDao;

    public LogInController setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
        return this;
    }

    @Override
    public Object[] getDataBinders() {
        return new Object[]{
                "loginInfo", wanbaep.workbook.vo.Member.class
        };
    }

    @Override
    public String execute(Map<String, Object> model) throws Exception {
        Member member = (Member) model.get("loginInfo");
        if(member.getEmail() == null) {
            return "/auth/LogInForm.jsp";
        } else {
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
