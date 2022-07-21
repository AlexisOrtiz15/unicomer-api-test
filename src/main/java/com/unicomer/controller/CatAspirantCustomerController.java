package com.unicomer.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.unicomer.controller.request.CatAspirantCustomerRequest;
import com.unicomer.controller.response.CatAspirantCustomerModel;
import com.unicomer.service.CatAspirantCustomerService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Endpoint that expose the CatAspirantCustomer resources.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api-aspirant-customer")
public class CatAspirantCustomerController {

	private final @NonNull CatAspirantCustomerService catAspirantCustomerService;

	/**
	 * GET a Aspirant Customer from the repository.
	 *
	 * @return
	 */

	@GetMapping
	public PagedModel<CatAspirantCustomerModel> getCatAspirantCustomer(final Pageable pageable) {
		return catAspirantCustomerService.getCatAspirantCustomer(pageable);
	}

	/**
	 * GET a Aspirant Customer from the repository using the unique identifier.
	 *
	 * @param id unique identifier of the Aspirant Customer.
	 * @return
	 */
	@GetMapping("/{id}")
	public CatAspirantCustomerModel getCatAspirantCustomer(@PathVariable final Long id) {
		return catAspirantCustomerService.getCatAspirantCustomer(id);
	}

	/**
	 * Creates a new Aspirant Customer based on the information given.
	 * 
	 * @param request with the information to be created.
	 *
	 * @return
	 */

	@PostMapping
	@ResponseStatus(CREATED)
	public CatAspirantCustomerModel createCatAspirantCustomer(
			@Valid @RequestBody final CatAspirantCustomerRequest request) {
		return catAspirantCustomerService.createCatAspirantCustomer(request);
	}

	/**
	 * Updates Aspirant Customer based on the unique identifier.
	 * 
	 * @param request with the information to be update.
	 *
	 * @return
	 */

	@PutMapping(path = "/{id}")
	@ResponseStatus(OK)
	public CatAspirantCustomerModel updateCatAspirantCustomer(@PathVariable final Long id,
			@Valid @RequestBody final CatAspirantCustomerRequest request) {

		return catAspirantCustomerService.updateCatAspirantCustomer(id, request);
	}
}
