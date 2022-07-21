package com.unicomer.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.UriComponentsBuilder;
import com.unicomer.controller.response.CatAspirantCustomerModel;
import com.unicomer.repository.domain.CatAspirantCustomer;
import com.unicomer.service.CatAspirantCustomerService;
import com.unicomer.service.assembler.CatAspirantCustomerAssembler;
import com.unicomer.service.mapper.CatAspirantCustomerMapper;


/**
 * Test the GET Method expose on CatAspirantCustomerController that gets
 * all the records from the repository using a pageable result.
 */
@WebMvcTest(CatAspirantCustomerController.class)
public class GetAllCatAspirantCustomerTest extends AbstractTestController {

    @MockBean 
    private CatAspirantCustomerService catAspirantCustomerService;

    private CatAspirantCustomerAssembler catAspirantCustomerAssembler;
    
    private PagedResourcesAssembler<CatAspirantCustomer> pagedResourcesAssembler;

    private PagedModel<CatAspirantCustomerModel> catAspirantCustomerModelPage;

    private int pageSize = 5;

    @BeforeEach
    public void initialize() {
        pageSize = 5;

        catAspirantCustomerAssembler = new CatAspirantCustomerAssembler(Mappers.getMapper(CatAspirantCustomerMapper.class));
        pagedResourcesAssembler = new PagedResourcesAssembler<CatAspirantCustomer>(null,
            UriComponentsBuilder.fromUriString("http://localhost").build());

        createCatAspirantCustomer();
    }

    @Test
    public void catAspirantCustomerGetAll() throws Exception {
        doRequest()
            .andDo(
                document("{method-name}",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    responseFields(documentResponseFields()),
                    links(
                        halLinks(),
                        linkWithRel("self").description("Canonical link for this resource"),
                        linkWithRel("first").description("First page of result"),
                        linkWithRel("last").description("Last page of result"),
                        linkWithRel("next").description("Next page of result"),
                        linkWithRel("prev").description("Previous page of result")))
            );
    }

    @Test
    @DisplayName("Should return data when CatAspirantCustomer exists")
    void shouldReturnDataWhenCatAspirantCustomerExists() throws Exception {
        doRequest()
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.data", hasSize(pageSize)));
    }

    @Test
    @DisplayName("Should not return data when CatAspirantCustomer is empty")
    void shouldNotReturnDataWhenCatAspirantCustomerIsEmpty() throws Exception {
    	catAspirantCustomerModelPage = pagedResourcesAssembler.toModel(
            new PageImpl<CatAspirantCustomer>(Collections.emptyList()), catAspirantCustomerAssembler);

        doRequest()
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded").doesNotExist());
    }

    @Test
    @DisplayName("Should return page when CatAspirantCustomer is empty")
    void shouldReturnPageWhenCatAspirantCustomerExists() throws Exception {
    	catAspirantCustomerModelPage = pagedResourcesAssembler.toModel(
            new PageImpl<CatAspirantCustomer>(Collections.emptyList()), catAspirantCustomerAssembler);

        doRequest()
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.page").exists());
    }

    @Test
    @DisplayName("Should return link to self when CatAspirantCustomer exists")
    void shouldReturnLinksWhenCatAspirantCustomerExists() throws Exception {
        doRequest()
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._links.self").exists());
    }

    private ResultActions doRequest() throws Exception {
        doReturn(catAspirantCustomerModelPage)
            .when(catAspirantCustomerService).getCatAspirantCustomer(any(Pageable.class));

        return getMockMvc()
            .perform(
                get("/api-aspirant-customer")
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaTypes.HAL_JSON_VALUE)
            );

    }

    private void createCatAspirantCustomer() {
        List<CatAspirantCustomer> catAspirantCustomers = IntStream.range(0, pageSize)
            .mapToObj(i -> CatAspirantCustomer.builder().
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
            								   build())
            .collect(Collectors.toList());

        PageImpl<CatAspirantCustomer> catAspirantCustomerPage =
            new PageImpl<>(catAspirantCustomers, PageRequest.of(1, pageSize), pageSize * 3);

        catAspirantCustomerModelPage = pagedResourcesAssembler.toModel(catAspirantCustomerPage, catAspirantCustomerAssembler);

    }

    private List<FieldDescriptor> documentResponseFields() {
        return Arrays.asList(
            subsectionWithPath("_embedded.data")
                .type(JsonFieldType.ARRAY)
                .description("An array of <<resources_cat_aspirant_customer, Aspirant Customer resources>>"),
            subsectionWithPath("_links").type(JsonFieldType.OBJECT).description("<<resources_cat_aspirant_customers_links, Links>>"),
            subsectionWithPath("page").type(JsonFieldType.OBJECT).description("<<resource_common_page, Pagination information>>")
        );
    }
}
