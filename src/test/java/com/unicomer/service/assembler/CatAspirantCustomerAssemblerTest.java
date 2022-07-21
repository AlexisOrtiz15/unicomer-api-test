package com.unicomer.service.assembler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.unicomer.controller.response.CatAspirantCustomerModel;
import com.unicomer.repository.domain.CatAspirantCustomer;
import com.unicomer.service.mapper.CatAspirantCustomerMapper;

/**
 * Will Unit Test the CatAspirantCustomerAssembler created to
 * convert from CatAspirantCustomer to CatAspirantCustomer.
 */
public class CatAspirantCustomerAssemblerTest {

    private CatAspirantCustomer catAspirantCustomer;
    private CatAspirantCustomerMapper catAspirantCustomerMapper;
    private CatAspirantCustomerAssembler catAspirantCustomerAssembler;

    @BeforeEach
    void initialization() {
    	catAspirantCustomerMapper = Mappers.getMapper(CatAspirantCustomerMapper.class);
    	catAspirantCustomerAssembler = new CatAspirantCustomerAssembler(catAspirantCustomerMapper);
    	catAspirantCustomer = CatAspirantCustomer.builder().
    			id(20L).
    			firstName("Juan").
				lastName("Perez").
				birthday(LocalDate.now()).
				gender("Masculino").
				cellphone("75689423").
				phone("22367896").
				address("San Salvador").
				profession("Abogado").
				incomes(3705700.782).
    			build();
    }

    @Test
    @DisplayName("Should return CatAspirantCustomerModel when aspirantCustomer is provided")
    void shouldReturnCatAspirantCustomerModelWhenCatAspirantCustomerIsProvided() {
        CatAspirantCustomerModel catAspirantCustomerModel = toModel();
        assertThat(catAspirantCustomerModel.getId(), is(catAspirantCustomer.getId()));
        assertThat(catAspirantCustomerModel.getFirstName(), is(catAspirantCustomer.getFirstName()));
        assertThat(catAspirantCustomerModel.getLastName(), is(catAspirantCustomer.getLastName()));
        assertThat(catAspirantCustomerModel.getBirthday(), is(catAspirantCustomer.getBirthday()));
        assertThat(catAspirantCustomerModel.getGender(), is(catAspirantCustomer.getGender()));
        assertThat(catAspirantCustomerModel.getCellphone(), is(catAspirantCustomer.getCellphone()));
        assertThat(catAspirantCustomerModel.getPhone(), is(catAspirantCustomer.getPhone()));
        assertThat(catAspirantCustomerModel.getAddress(), is(catAspirantCustomer.getAddress()));
        assertThat(catAspirantCustomerModel.getProfession(), is(catAspirantCustomer.getProfession()));
        assertThat(catAspirantCustomerModel.getIncomes(), is(catAspirantCustomer.getIncomes()));
    }

    @Test
    @DisplayName("Should return null when catAspirantCustomer is not given")
    void shouldReturnNullWhenCatAspirantCustomerIsNotGiven() {
        catAspirantCustomer = null;
        assertThat(this.toModel(), is(nullValue()));
    }

    @Test
    @DisplayName("Should add link to self when catAspirantCustomer is given")
    void shouldAddLinkToSelfWhenCatAspirantCustomerIsGiven() {
        CatAspirantCustomerModel catAspirantCustomerModel = toModel();
        assertThat(catAspirantCustomerModel.getLink("self").isPresent(), is(true));
    }

    private CatAspirantCustomerModel toModel() {
        return catAspirantCustomerAssembler.toModel(catAspirantCustomer);
    }

}
