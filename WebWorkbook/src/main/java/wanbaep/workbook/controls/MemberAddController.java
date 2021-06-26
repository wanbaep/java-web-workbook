package wanbaep.workbook.controls;

import wanbaep.workbook.annotation.Component;
import wanbaep.workbook.bind.DataBinding;
import wanbaep.workbook.dao.MemberDao;
import wanbaep.workbook.vo.Member;

import java.util.Map;

@Component("/member/add.do")
public class MemberAddController implements Controller, DataBinding {
    MemberDao memberDao;

    public MemberAddController setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
        return this;
    }

    @Override
    public Object[] getDataBinders() {
        return new Object[]{
                "member", wanbaep.workbook.vo.Member.class
        };
    }

    @Override
    public String execute(Map<String, Object> model) throws Exception {
        Member member = (Member) model.get("member");
        if(member.getEmail() == null) { //프론트 컨트롤러가 VO 객체를 무조건 생성하기 때문에 Member 있는지 여부로 판단 불가.
            return "/member/MemberForm.jsp";
        } else {
            memberDao.insert(member);
            return "redirect:list.do";
        }
    }
}
