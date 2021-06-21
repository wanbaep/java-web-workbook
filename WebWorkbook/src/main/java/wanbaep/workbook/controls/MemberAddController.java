package wanbaep.workbook.controls;

import wanbaep.workbook.dao.MemberDao;
import wanbaep.workbook.vo.Member;

import java.util.Map;

public class MemberAddController implements Controller {
    @Override
    public String execute(Map<String, Object> model) throws Exception {
        if(model.get("member") == null) {
            return "/member/MemberForm.jsp";
        } else {
            MemberDao memberDao = (MemberDao) model.get("memberDao");
            Member member = (Member) model.get("member");
            memberDao.insert(member);

            return "redirect:list.do";
        }
    }
}
