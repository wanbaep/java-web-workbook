package wanbaep.workbook.controls;

import wanbaep.workbook.dao.MemberDao;

import java.util.Map;

public class MemberListController implements Controller {
    @Override
    public String execute(Map<String, Object> model) throws Exception {
        MemberDao memberDao = (MemberDao) model.get("memberDao");
        model.put("members", memberDao.selectList());
        return "/member/MemberList.jsp";
    }
}
