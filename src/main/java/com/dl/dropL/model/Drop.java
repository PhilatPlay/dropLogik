package com.dl.dropL.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "drops")
public class Drop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long dropId;

    @Column(name = "drop_title", nullable = false)
    private String dropTitle;

    @Column(name = "demand_description", nullable = false)
    private String demandDesc;

    @Column(name = "lat_drop", nullable = false)
    private double latdrop;

    @Column(name = "long_drop", nullable = false)
    private double longdrop;

    @Column(name = "lat_pickup")
    private double latpickup;

    @Column(name = "long_pickup")
    private double longpickup;

    @Column(name = "pay_offer")
    private float payOffer;

    @Column(name = "drop_by_date")
    private Date dropByDate;

    @Column(name = "date_posted", nullable = false)
    private Date datePosted;

    @Column(name = "last_modified", nullable = false)
    private Date lastModified;
}
