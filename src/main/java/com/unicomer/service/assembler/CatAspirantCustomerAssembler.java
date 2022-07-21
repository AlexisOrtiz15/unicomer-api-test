package com.unicomer.service.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.Optional;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import com.unicomer.controller.CatAspirantCustomerController;
import com.unicomer.controller.response.CatAspirantCustomerModel;
import com.unicomer.repository.domain.CatAspirantCustomer;
import com.unicomer.service.mapper.CatAspirantCustomerMapper;


/**
 * Handles the conversion from AspirantCustomer domain object into a model
 * as well to add the basics for a Hal Representation Model.
 */
@Component
public class CatAspirantCustomerAssembler extends RepresentationModelAssemblerSupport<CatAspirantCustomer, CatAspirantCustomerModel> {

	@Getter
	private final @NonNull CatAspirantCustomerMapper catAspirantCustomerMapper;
	
	public CatAspirantCustomerAssembler(final @NonNull CatAspirantCustomerMapper catAspirantCustomerMapper) {
		super(CatAspirantCustomerController.class, CatAspirantCustomerModel.class);
		this.catAspirantCustomerMapper = catAspirantCustomerMapper;
	}
	
	@Override
	public CatAspirantCustomerModel toModel(CatAspirantCustomer entity) {
		return Optional.ofNullable(entity).map(value -> {
			CatAspirantCustomerModel model = this.catAspirantCustomerMapper.toCatAspirantCustomerModel(value);
			model.add(linkTo(getControllerClass()).slash(model.getId()).withSelfRel());
			return model;
		}).orElse(null);
	}

  
}
