package com.allMighty.enitity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "subscriber_request")
public class SubscriberRequestEntity  extends AbstractEntity{
    private String subscriberEmail;
}
