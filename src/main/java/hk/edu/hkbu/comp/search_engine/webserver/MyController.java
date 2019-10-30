package hk.edu.hkbu.comp.search_engine.webserver;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
    @GetMapping("/hello")
    @ResponseBody
    String load(HttpServletRequest request) {
        return "Hello World";
    }
}

