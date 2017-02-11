package camelspy.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

/**
 * Created by przemekd on 11.02.17.
 */
@Getter
@Builder
public class InterceptedExchange {

    private String exchangeId;
    private String from;
    private Date date;
    private List<InterceptedEvent> events;
}
