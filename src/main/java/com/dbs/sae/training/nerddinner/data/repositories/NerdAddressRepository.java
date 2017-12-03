package com.dbs.sae.training.nerddinner.data.repositories;

import com.dbs.sae.training.nerddinner.data.models.NerdAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NerdAddressRepository extends JpaRepository<NerdAddress, Integer> {

}
