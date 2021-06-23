package wanbaep.workbook.controls;

import wanbaep.workbook.bind.DataBinding;
import wanbaep.workbook.dao.MemberDao;
import wanbaep.workbook.vo.Member;

import java.util.Map;

public class MemberUpdateController implements Controller, DataBinding {
    MemberDao memberDao;

    public MemberUpdateController setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
        return this;
    }

    @Override
    public Object[] getDataBinders() {
        return new Object[]{
                "member", wanbaep.workbook.vo.Member.class,
                "no", Integer.class
        };
    }

    @Override
    public String execute(Map<String, Object> model) throws Exception {
        Member member = (Member) model.get("member");
        if(member.getEmail() != null) {
            memberDao.update(member);
            return "redirect:list.do";
        } else {
            Integer no = (Integer) model.get("no");
            Member selectedMember = memberDao.selectOne(no);
            if(selectedMember != null) {
                model.put("updateMember", selectedMember);
                return "/member/MemberUpdateForm.jsp";
            } else {
                return "/Error.jsp";
            }
        }
    }
}
