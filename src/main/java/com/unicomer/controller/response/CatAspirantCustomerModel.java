package com.unicomer.controller.response;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 * Model object to represent Aspirant Customer information.
 */
@Getter
@Setter
@Builder(toBuilder = true)
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "data")
public class CatAspirantCustomerModel extends RepresentationModel<CatAspirantCustomerModel> {

	private Long id;

	private String firstName;

	private String lastName;

	private LocalDate birthday;

	private String gender;

	private String cellphone;

	private String phone;

	private String address;

	private String profession;

	private Double incomes;

}
