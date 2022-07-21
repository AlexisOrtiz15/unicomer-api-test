package com.unicomer.service;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.unicomer.controller.request.CatAspirantCustomerRequest;
import com.unicomer.controller.response.CatAspirantCustomerModel;

/**
 * Business Logic to expose Aspirant Customer information.
 */
public interface CatAspirantCustomerService {

	/**
	 * Get all the Aspirant Customer from the repository.
	 *
	 * @return a collection of all the Aspirant Customer found in the repository
	 */
	PagedModel<CatAspirantCustomerModel> getCatAspirantCustomer(final Pageable pageable);

	/**
	 * Get an specific CatAspirantCustomer based on the ID.
	 * 
	 * @param id unique identifier of the Aspirant Customer
	 * @return null if the aspirantCustomer does not exist with the give ID
	 *         otherwise the CatAspirantCustomer found
	 */
	CatAspirantCustomerModel getCatAspirantCustomer(final Long id);

	/**
	 * Creates a new CatAspirantCustomer into the repository.
	 *
	 * @param catAspirantCustomerRequest entity to be created
	 *
	 * @return CatAspirantCustomerModel created
	 */
	CatAspirantCustomerModel createCatAspirantCustomer(final CatAspirantCustomerRequest catAspirantCustomerRequest);

	/**
	 * Update a CatAspirantCustomer into the repository.
	 *
	 * @param catAspirantCustomerRequest entity to be Update, id unique identifier
	 *                                   of the Aspirant Customer
	 *
	 * @return void
	 */

	CatAspirantCustomerModel updateCatAspirantCustomer(final Long id,
			CatAspirantCustomerRequest catAspirantCustomerRequest);

}
