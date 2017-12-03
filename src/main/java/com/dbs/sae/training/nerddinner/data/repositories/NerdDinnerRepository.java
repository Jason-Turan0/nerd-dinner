package com.dbs.sae.training.nerddinner.data.repositories;

import com.dbs.sae.training.nerddinner.data.models.NerdDinner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NerdDinnerRepository extends JpaRepository<NerdDinner, Integer> {

}
