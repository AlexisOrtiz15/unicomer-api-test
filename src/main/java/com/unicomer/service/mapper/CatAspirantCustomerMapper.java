package com.unicomer.service.mapper;

import org.mapstruct.Mapper;

import com.unicomer.controller.request.CatAspirantCustomerRequest;
import com.unicomer.controller.response.CatAspirantCustomerModel;
import com.unicomer.repository.domain.CatAspirantCustomer;

/**
 * Map Struct interface that handles conversions from and to AspirantCustomer
 * Entity.
 */
@Mapper(componentModel = "spring")
public interface CatAspirantCustomerMapper {

    /**
     * Converts from CatAspirantCustomer entity to CatAspirantCustomerModel.
     *
     * @param CatAspirantCustomer to be converted
     * @return CatAspirantCustomerModel the model
     */
	CatAspirantCustomerModel toCatAspirantCustomerModel(final CatAspirantCustomer catAspirantCustomer);

    /**
     * Converts from CatAspirantCustomeRequest to CatAspirantCustome entity.
     *
     * @param CatAspirantCustomeRequest to be converted
     * @return CatAspirantCustomer the entity
     */
	CatAspirantCustomer toCatAspirantCustomer(final CatAspirantCustomerRequest catAspirantCustomerRequest);

}
