package com.unicomer.controller;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.web.servlet.ResultActions;
import com.unicomer.controller.response.CatAspirantCustomerModel;
import com.unicomer.repository.domain.CatAspirantCustomer;
import com.unicomer.service.CatAspirantCustomerService;
import com.unicomer.service.assembler.CatAspirantCustomerAssembler;
import com.unicomer.service.mapper.CatAspirantCustomerMapper;

/**
 * Test the GET Method expose on CatAspirantCustomerController that gets
 * a specific funny name from the repository.
 */
@WebMvcTest(CatAspirantCustomerController.class)
public class GetCatAspirantCustomerTest extends AbstractTestController{

    @MockBean private CatAspirantCustomerService catAspirantCustomerService;

    private CatAspirantCustomerModel catAspirantCustomerModel;
    
    private CatAspirantCustomerAssembler catAspirantCustomerAssembler;

    @BeforeEach
    public void initialize() {
        catAspirantCustomerAssembler = new CatAspirantCustomerAssembler(Mappers.getMapper(CatAspirantCustomerMapper.class));

        catAspirantCustomerModel = catAspirantCustomerAssembler.toModel(CatAspirantCustomer.builder().
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
        																					build());
    }

    @Test
    public void catAspirantCustomerGet() throws Exception {
        doRequest()
            .andDo(
                document("{method-name}",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    pathParameters(documentPathParameters()),
                    responseFields(documentResponseFields()),
                    links(
                        halLinks(),
                        linkWithRel("self").description("Canonical link for this resource"))
                )
            );

    }

    @Test
    @DisplayName("Should return catAspirantCustomer when exists")
    void shouldReturnCatAspirantCustomerWhenExists() throws Exception {
        doRequest()
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(catAspirantCustomerModel.getId().intValue())));
    }

    private ResultActions doRequest() throws Exception {
        doReturn(catAspirantCustomerModel)
            .when(catAspirantCustomerService).getCatAspirantCustomer(anyLong());

        return getMockMvc()
            .perform(
                get("/api-aspirant-customer/{id}", catAspirantCustomerModel.getId())
            );
    }

    private List<ParameterDescriptor> documentPathParameters() {
        return Arrays.asList(
            parameterWithName("id").description("Unique identifier of the catAspirantCustomer given to do the lookup")
        );
    }

    private List<FieldDescriptor> documentResponseFields() {
        return Arrays.asList(
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("catAspirantCustomer unique identifier"),
            fieldWithPath("firstName").type(JsonFieldType.STRING).description("First name that represents the aspirant customer"),
            fieldWithPath("lastName").type(JsonFieldType.STRING).description("Last Name that represents the aspirant custome"),
            fieldWithPath("birthday").type(JsonFieldType.STRING).description("Date birthday the of aspirant custome"),
            fieldWithPath("gender").type(JsonFieldType.STRING).description("Gender the aspirant custome"),
            fieldWithPath("cellphone").type(JsonFieldType.STRING).description("Number cell phone the aspirant custome"),
            fieldWithPath("phone").type(JsonFieldType.STRING).description("Home phone the  aspirant custome"),
            fieldWithPath("address").type(JsonFieldType.STRING).description("Address home the aspirant customer"),
            fieldWithPath("profession").type(JsonFieldType.STRING).description("Profession of the aspirant customer"),
            fieldWithPath("incomes").type(JsonFieldType.NUMBER).description("incomes of the  aspirant customer"),
            subsectionWithPath("_links").description("<<resources_cat_aspirant_customer_links, Links>>")
        );
    }
}
