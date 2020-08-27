package vn.com.tpf.microservices.models.RaiseQuery;

import lombok.Builder;

@Builder
public class QueryModuleLocationDetailsVO{
    private String latitude;
    private String speed;
    private String provider;
    private String longitude;
    private String timeStamp;
    private String locationAddress;
    private String altitude;
}
