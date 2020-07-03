package vn.com.tpf.microservices.models.Automation;

import lombok.Data;

import java.util.List;

@Data
public class Reference {

    public String name;
    public String relationship;
    public List<Phonenumber> phoneNumbers = null;

}