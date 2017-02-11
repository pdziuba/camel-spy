package camelspy.web.rest;

import camelspy.domain.InterceptedEvent;
import camelspy.service.InterceptedEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by przemekd on 29.01.17.
 */
@RestController
@RequestMapping("/rest")
public class BacklogRestEnpoint {

    @Autowired
    private InterceptedEventsService interceptedEventsService;

    @RequestMapping("/messages/last")
    public ResponseEntity<InterceptedEvent> getLastMessage(){

            List<InterceptedEvent> interceptedEvents = interceptedEventsService.getInterceptedEvents();
            if (interceptedEvents.size() > 0){
                return new ResponseEntity<>(interceptedEvents.get(interceptedEvents.size() - 1), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
    }

    @RequestMapping("/messages/all")
    public ResponseEntity<List<InterceptedEvent>> getAllMessages(){
        List<InterceptedEvent> interceptedEvents = interceptedEventsService.getInterceptedEvents();
        return new ResponseEntity<>(interceptedEvents, HttpStatus.OK);
    }
}
