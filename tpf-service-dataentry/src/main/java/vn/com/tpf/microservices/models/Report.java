package vn.com.tpf.microservices.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Data
@Document(collection = "dataentry_report")
public class Report {
    @Id
    private String id;
    private String applicationId;
    private String status;
    private String function;
    private String createdBy;
    private String processBy;
    private Date createdDate;

}