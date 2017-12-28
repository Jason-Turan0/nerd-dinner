package com.dbs.sae.training.nerddinner.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity(name = "NERD_DINNER_RESERVATIONS")
public class NerdDinnerReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NERD_DINNER_RESERVATION_PK")
    private Integer nerdDinnerReservationPk;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "NERD_FK")
    private Nerd nerd;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "NERD_DINNER_FK")
    private NerdDinner nerdDinner;

    @Column(name="RESERVATION_DATE")
    private Timestamp reservationDate;

    @Column(name="CANCELLATION_DATE")
    private Timestamp cancellationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NerdDinnerReservation that = (NerdDinnerReservation) o;

        return nerdDinnerReservationPk.equals(that.nerdDinnerReservationPk);
    }

    @Override
    public int hashCode() {
        return nerdDinnerReservationPk.hashCode();
    }
}
