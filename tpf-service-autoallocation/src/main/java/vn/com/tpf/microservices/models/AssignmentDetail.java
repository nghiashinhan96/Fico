package vn.com.tpf.microservices.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ALLOCATION_USER_DETAIL")
@Data
public class AssignmentDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Column(name = "APP_NUMBER")
    private String appNumber;

    @Column(name = "STAGE_NAME")
    private String stageName;

    @Column(name = "LAST_UPDATED_TIME")
    private String lastUpdateTime;

    @Column(name = "STAGE_ID")
    private String stageId;

    @Column(name = "ASSIGNEE")
    private String assignee;

    @Column(name = "ASSIGNED_TIME")
    private Timestamp assignedTime;

    @Column(name = "ASSIGNED_BY")
    private Timestamp assignedBy;

    @Column(name = "COMPLETED_TIME  ")
    private String completedTime;

    @Column(name = "STATUS")
    private String status;

}
