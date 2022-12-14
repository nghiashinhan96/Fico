package vn.com.tpf.microservices.models.Automation;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiscFtp_ProductDetails {
    private String model;
    private String goodCode;
    private String goodType;
    private String quantity;
    private String goodPrice;
}
