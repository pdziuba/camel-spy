package camelspy.service;

import camelspy.domain.InterceptedEvent;
import camelspy.domain.InterceptedExchange;

import java.util.Collection;
import java.util.List;

/**
 * Created by przemekd on 11.02.17.
 */
public interface InterceptedEventsService {

    void saveEvent(InterceptedEvent event);

    List<InterceptedEvent> getInterceptedEvents();

    Collection<InterceptedExchange> getInterceptedExchanges();
}
