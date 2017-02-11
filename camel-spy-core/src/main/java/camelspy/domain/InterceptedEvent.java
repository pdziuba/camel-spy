package camelspy.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@Builder
public class InterceptedEvent {

    private String exchangeId;
    private String routeId;
    private String from;
    private String to;
    private Date date;
    private Map<String, String> properties;
    private Map<String, String> headers;
    private String body;

}
