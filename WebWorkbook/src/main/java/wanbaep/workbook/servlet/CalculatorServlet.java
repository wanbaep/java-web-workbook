package wanbaep.workbook.servlet;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

//Servlet 3.0 사양부터는 애노테이션으로 서블릿 배치 정보를 설정할 수 있다.
@WebServlet(urlPatterns = {"/calc","/calc.do"})
public class CalculatorServlet extends GenericServlet {
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        int a = Integer.parseInt(servletRequest.getParameter("a"));
        int b = Integer.parseInt(servletRequest.getParameter("b"));

        servletResponse.setContentType("text/plain");
        servletResponse.setCharacterEncoding("UTF-8");
        PrintWriter writer = servletResponse.getWriter();
        writer.println("a=" + a + "," + "b=" + b + " 의 계산결과 입니다.");
        writer.println("a + b = " + (a+b));
        writer.println("a - b = " + (a-b));
        writer.println("a * b = " + (a*b));
        writer.println("a / b = " + ((float)a/(float)b));
        writer.println("a % b =" + (a % b));
    }
}
