package com.dbs.sae.training.nerddinner.data.repositories;
import com.dbs.sae.training.nerddinner.data.models.Nerd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NerdRepository extends JpaRepository<Nerd, Integer> {

}
