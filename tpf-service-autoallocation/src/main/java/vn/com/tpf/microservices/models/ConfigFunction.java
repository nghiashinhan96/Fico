package vn.com.tpf.microservices.models;

import lombok.Data;

@Data
public class ConfigFunction {

    private String fromTime;
    private String toTime;
    private String config;
    private int limit ;
}