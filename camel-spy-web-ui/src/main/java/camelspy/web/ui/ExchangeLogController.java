package camelspy.web.ui;

import camelspy.domain.InterceptedExchange;
import camelspy.service.InterceptedEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

/**
 * Created by przemekd on 11.02.17.
 */
@Controller
public class ExchangeLogController {

    @Autowired
    private InterceptedEventsService service;

    @RequestMapping("/")
    public String getMainView(){
        return "redirect:log";
    }

    @RequestMapping("/log")
    public String getLogView(Model model){
        Collection<InterceptedExchange> interceptedExchanges = service.getInterceptedExchanges();

        model.addAttribute("exchanges", interceptedExchanges);
        return "log";
    }



}
