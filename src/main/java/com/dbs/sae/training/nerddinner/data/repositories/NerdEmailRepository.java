package com.dbs.sae.training.nerddinner.data.repositories;

import com.dbs.sae.training.nerddinner.data.models.NerdDinnerReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NerdEmailRepository extends JpaRepository<NerdDinnerReservation, Integer> {

}
