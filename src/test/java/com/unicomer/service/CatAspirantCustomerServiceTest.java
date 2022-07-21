package com.unicomer.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.util.UriComponentsBuilder;

import com.unicomer.controller.request.CatAspirantCustomerRequest;
import com.unicomer.controller.response.CatAspirantCustomerModel;
import com.unicomer.repository.CatAspirantCustomerRepository;
import com.unicomer.repository.domain.CatAspirantCustomer;
import com.unicomer.service.assembler.CatAspirantCustomerAssembler;
import com.unicomer.service.implementation.CatAspirantCustomerServiceImpl;
import com.unicomer.service.mapper.CatAspirantCustomerMapper;


/**
 * Unit test the methods expose on the business layer
 * for the  Aspirant Customer.
 */
public class CatAspirantCustomerServiceTest {

    private CatAspirantCustomerService catAspirantCustomerService;
    private CatAspirantCustomerAssembler catAspirantCustomerAssembler;
    private CatAspirantCustomerRepository catAspirantCustomerRepository;
    private PagedResourcesAssembler<CatAspirantCustomer> pagedResourcesAssembler;

    @BeforeEach
    void initialize() {
    	catAspirantCustomerAssembler = new CatAspirantCustomerAssembler(Mappers.getMapper(CatAspirantCustomerMapper.class));
        pagedResourcesAssembler = new PagedResourcesAssembler<CatAspirantCustomer>(null,
            UriComponentsBuilder.fromUriString("http://localhost").build());

        catAspirantCustomerRepository = Mockito.mock(CatAspirantCustomerRepository.class);
        catAspirantCustomerService = new CatAspirantCustomerServiceImpl(catAspirantCustomerAssembler,
        		catAspirantCustomerRepository, pagedResourcesAssembler);
    }

    @Nested
    @DisplayName("GetCatAspirantCustomer")
    class GetCatAspirantCustomer {

        private CatAspirantCustomer catAspirantCustomer;
        private CatAspirantCustomerModel catAspirantCustomerModel;

        @BeforeEach
        void initialize() {
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
        	catAspirantCustomerModel = catAspirantCustomerAssembler.toModel(catAspirantCustomer);
        }

        @Test
        @DisplayName("Should return catAspirantCustomer when aspirant custome does exist")
        void shouldReturnCatAspirantCustomerWhenAspirantCustomerDoesExist() {
            assertThat(getCatAspirantCustomerModel(), is(catAspirantCustomerModel));
        }

        private CatAspirantCustomerModel getCatAspirantCustomerModel() {
            doReturn(Optional.of(catAspirantCustomer))
                .when(catAspirantCustomerRepository).findById(Mockito.anyLong());

            return catAspirantCustomerService.getCatAspirantCustomer(20L);
        }

    }

    @Nested
    @DisplayName("GetCatAspirantCustomes")
    class GetCatAspirantCustomers {

        private Pageable pageable;

        @BeforeEach
        void initialize() {
            pageable = PageRequest.of(0, 1);
        }

        @Test
        @DisplayName("Should return a page when repository is paged")
        void shouldReturnAPageWhenRepositoryIsPaged() {
            assertThat(getCatAspirantCustomes(), is(not(nullValue())));
        }

        private PagedModel<CatAspirantCustomerModel> getCatAspirantCustomes() {
            doReturn(new PageImpl<CatAspirantCustomer>(Collections.emptyList()))
                .when(catAspirantCustomerRepository).findAll(any(Pageable.class));

            return catAspirantCustomerService.getCatAspirantCustomer(pageable);
        }
    }

    @Nested
    @DisplayName("CreateCatAspirantCustomer")
    class CreateCatAspirantCustomer {

        private CatAspirantCustomerRequest catAspirantCustomerRequest;

        @BeforeEach
        void initialize() {
        	catAspirantCustomerRequest = CatAspirantCustomerRequest.
        													builder().
        													firstName("Juana").
                           								   	lastName("Martinez").
               											    birthday(LocalDate.now()).
               											    gender("Femenino").
               											    cellphone("75689783").
               											    phone("22367366").
               											    address("La libertada").
               											    profession("Abogado").
               											    incomes(7705778.798).
        													build();
        }

        @Test
        @DisplayName("Should create record when aspirantCustomer is given")
        void shouldCreateRecordWhenCatAspirantCustomerIsGiven() {
            this.createCatAspirantCustomer();
            verify(catAspirantCustomerRepository, times(1))
                .save(any(CatAspirantCustomer.class));
        }

        private void createCatAspirantCustomer() {
        	catAspirantCustomerService.createCatAspirantCustomer(catAspirantCustomerRequest);
        }
    }
}
