package camelspy.service;

import camelspy.domain.InterceptedEvent;
import camelspy.domain.InterceptedExchange;

import java.util.*;

public class InterceptedEventsInMemoryService implements InterceptedEventsService {

    private LinkedList<InterceptedEvent> events = new LinkedList<>();
    private Map<String, InterceptedExchange> exchangeMap = new LinkedHashMap<>();


    @Override
    public void saveEvent(InterceptedEvent event){
        String exchangeId = event.getExchangeId();
        InterceptedExchange exchange;
        if (exchangeMap.containsKey(exchangeId)){
            exchange = exchangeMap.get(exchangeId);
        }else{
            exchange = InterceptedExchange.builder()
                    .exchangeId(exchangeId)
                    .from(event.getFrom())
                    .date(event.getDate())
                    .events(new ArrayList<>())
                    .build();
            exchangeMap.put(exchangeId, exchange);
        }
        exchange.getEvents().add(event);
    }

    @Override
    public List<InterceptedEvent> getInterceptedEvents(){
        return new ArrayList<>(events);
    }

    @Override
    public Collection<InterceptedExchange> getInterceptedExchanges() {
        return exchangeMap.values();
    }

}
