package wanbaep.workbook.servlet;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class HelloWorldGeneric extends GenericServlet {
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("service() 호출됨");
        System.out.println("GenericServlet 을 상속 받으면, abstract method인 service만 구현 해서 사용 가능하다");
    }
}
