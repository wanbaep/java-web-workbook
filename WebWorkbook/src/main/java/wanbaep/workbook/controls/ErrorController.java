package wanbaep.workbook.controls;

import java.util.Map;

public class ErrorController implements Controller {
    @Override
    public String execute(Map<String, Object> model) throws Exception {
        return "/Error.jsp";
    }
}
