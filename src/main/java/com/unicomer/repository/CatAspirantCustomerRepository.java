package com.unicomer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.unicomer.repository.domain.CatAspirantCustomer;

/**
 * Data Access layer for property information.
 */

public interface  CatAspirantCustomerRepository extends JpaRepository<CatAspirantCustomer, Long> {
	
	CatAspirantCustomer findByFirstName(final String firstName);
	
	CatAspirantCustomer findByCellphone(final Long cellphone);
	
	CatAspirantCustomer findByGender(final String gender);
}
