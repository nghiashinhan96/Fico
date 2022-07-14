package vn.com.tpf.microservices.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

//import vn.com.tpf.microservices.models.cancelF1.CancelResponseModel;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "dataentry")
public class Application {
    @Id
    private String id;

    private String applicationId;
    private String quickLeadId;
    private QuickLead quickLead;

    @Valid
    private ApplicationInformation applicationInformation;
    @Valid
    private LoanDetails loanDetails;
    @Valid
    private List<Reference> references;
    @Valid
    private List<DynamicForm> dynamicForm;

    private List<CommentModel> comment;
    private List<QLDocument> documents;

    private String status;
    private String description;
    private String stage;
    private String error;
    private String userName;
    private String userName_DE;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date lastModifiedDate;

    private String partnerId;
    private String partnerName;

//    private boolean isHolding = false;

    private String reasonCancel;
    private String createFrom;
    private Long routingId;
  //  private RoutingF1 routingF1;
    private String stageF1;
    private boolean isProcessing;
    private boolean calledPartner;
    private boolean callGetStatusF1;
    private String loanStageName;

//    @Getter(AccessLevel.NONE)
//    private List<CancelResponseModel> cancelResponseModels;
//    public List<CancelResponseModel> getCancelResponseModels(){
//        if (cancelResponseModels == null)
//            cancelResponseModels = new ArrayList<>();
//        return this.cancelResponseModels;
//    }

}