package com.dbs.sae.training.nerddinner;

import com.dbs.sae.training.nerddinner.data.configuration.NerdRepositoryFactoryBean;
import com.dbs.sae.training.nerddinner.data.models.Address;
import com.dbs.sae.training.nerddinner.data.models.Nerd;
import com.dbs.sae.training.nerddinner.data.models.NerdAddress;
import com.dbs.sae.training.nerddinner.data.models.NerdContactType;
import com.dbs.sae.training.nerddinner.data.repositories.NerdContactTypeRepository;
import com.dbs.sae.training.nerddinner.data.repositories.NerdRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableJpaRepositories(repositoryFactoryBeanClass = NerdRepositoryFactoryBean.class)
public class NerddinnerRepositoryTests {

	@Autowired
	private NerdRepository repo;

	@Autowired
	private NerdContactTypeRepository contactTypeRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void testAddNerd() {
		Nerd n = new Nerd();
		n.setFirstName("Jon");
		n.setLastName("Doe");
		n.setUserName(generateUniqueUserName());
		n.setPassword(generatePassword());
		Nerd savedNerd = this.repo.save(n);

		n.setNerdPk(savedNerd.getNerdPk());
		assertEquals(n, savedNerd);
	}

	@Test
	public void testAddAddressToNerd(){
		Nerd n = new Nerd();
		n.setFirstName("Jon");
		n.setLastName("Doe " + UUID.randomUUID().toString().substring(0, 5));
		n.setUserName(generateUniqueUserName());
		n.setPassword(generatePassword());

		NerdAddress home = CreateNewAddress(n, "Home");
		NerdAddress work = CreateNewAddress(n, "Work");
		n.setAddresses(Stream.of(home,work).collect(Collectors.toSet()));
		Nerd savedNerd = this.repo.save(n);
	}


	private NerdAddress CreateNewAddress(Nerd n, String contactType) {
		NerdContactType example = new NerdContactType();
		example.setContactTypeCode(contactType);
		NerdContactType ct = contactTypeRepo.findOne(Example.of(example));

		Address homeAddress = new Address();
		homeAddress.setStreetLine1(String.format("100 %s ST", contactType));
		homeAddress.setStreetLine2("");
		homeAddress.setCity("City");
		homeAddress.setState("MO");
		homeAddress.setZip("63132");
		homeAddress.setTimeZoneOffsetMinutes(-360);

		NerdAddress na = new NerdAddress();
		na.setAddress(homeAddress);
        na.setExpiredDate(null);
        na.setNerdContactType(ct);
        na.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
        na.setNerd(n);
		return na;
	}

	private String generateUniqueUserName(){
		return  "nerd_" + UUID.randomUUID().toString().substring(0, 5);
	}

	private String generatePassword(){
		String unencrypted = "password";
		String encoded =  passwordEncoder.encode(unencrypted);
		return encoded;
	}

}
