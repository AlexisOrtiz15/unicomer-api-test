package com.unicomer.service.implementation;

import java.util.Objects;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unicomer.controller.request.CatAspirantCustomerRequest;
import com.unicomer.controller.response.CatAspirantCustomerModel;
import com.unicomer.repository.CatAspirantCustomerRepository;
import com.unicomer.repository.domain.CatAspirantCustomer;
import com.unicomer.service.CatAspirantCustomerService;
import com.unicomer.service.assembler.CatAspirantCustomerAssembler;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CatAspirantCustomerServiceImpl implements CatAspirantCustomerService {

	@NonNull
	private final CatAspirantCustomerAssembler catAspirantCustomerAssembler;

	@NonNull
	private final CatAspirantCustomerRepository catAspirantCustomerRepository;

	@NonNull
	private final PagedResourcesAssembler<CatAspirantCustomer> pagedResourcesAssembler;

	@Override
	public PagedModel<CatAspirantCustomerModel> getCatAspirantCustomer(final Pageable pageable) {

		return pagedResourcesAssembler.toModel(catAspirantCustomerRepository.findAll(pageable),
				catAspirantCustomerAssembler);
	}

	@Override
	public CatAspirantCustomerModel getCatAspirantCustomer(final Long id) {

		return catAspirantCustomerAssembler.toModel(catAspirantCustomerRepository.findById(id).orElseThrow());
	}

	@Override
	@Transactional
	public CatAspirantCustomerModel createCatAspirantCustomer(
			final CatAspirantCustomerRequest catAspirantCustomerRequest) {
		CatAspirantCustomer catAspirantCustomer = catAspirantCustomerAssembler.getCatAspirantCustomerMapper()
				.toCatAspirantCustomer(catAspirantCustomerRequest);
		catAspirantCustomerRepository.save(catAspirantCustomer);
		return catAspirantCustomerAssembler.toModel(catAspirantCustomer);
	}

	@Override
	@Transactional
	public CatAspirantCustomerModel updateCatAspirantCustomer(final Long id,
			final CatAspirantCustomerRequest catAspirantCustomerRequest) {

		CatAspirantCustomer catAspirantCustomer = fechCatAspirantCustomer(id);

		if (Objects.nonNull(catAspirantCustomerRequest.getFirstName())) {
			catAspirantCustomer.setFirstName(catAspirantCustomerRequest.getFirstName());
		}

		if (Objects.nonNull(catAspirantCustomerRequest.getLastName())) {
			catAspirantCustomer.setLastName(catAspirantCustomerRequest.getLastName());
		}

		if (Objects.nonNull(catAspirantCustomerRequest.getBirthday())) {
			catAspirantCustomer.setBirthday(catAspirantCustomerRequest.getBirthday());
		}

		if (Objects.nonNull(catAspirantCustomerRequest.getGender())) {
			catAspirantCustomer.setGender(catAspirantCustomerRequest.getGender());
		}

		if (Objects.nonNull(catAspirantCustomerRequest.getCellphone())) {
			catAspirantCustomer.setCellphone(catAspirantCustomerRequest.getCellphone());
		}

		if (Objects.nonNull(catAspirantCustomerRequest.getPhone())) {
			catAspirantCustomer.setPhone(catAspirantCustomerRequest.getPhone());
		}

		if (Objects.nonNull(catAspirantCustomerRequest.getAddress())) {
			catAspirantCustomer.setAddress(catAspirantCustomerRequest.getAddress());
		}

		if (Objects.nonNull(catAspirantCustomerRequest.getProfession())) {
			catAspirantCustomer.setProfession(catAspirantCustomerRequest.getProfession());
		}

		if (Objects.nonNull(catAspirantCustomerRequest.getIncomes())) {
			catAspirantCustomer.setIncomes(catAspirantCustomerRequest.getIncomes());
		}

		catAspirantCustomerRepository.save(catAspirantCustomer);

		return catAspirantCustomerAssembler.toModel(catAspirantCustomer);
	}

	private CatAspirantCustomer fechCatAspirantCustomer(final Long id) {
		return catAspirantCustomerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Termino y condicion no encontrado para id" + id));
	}

}
