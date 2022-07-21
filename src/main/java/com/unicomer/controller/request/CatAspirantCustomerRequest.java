package com.unicomer.controller.request;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request object that will be use to create Aspirant Customer through the API.
 */
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CatAspirantCustomerRequest {

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotNull
	private LocalDate birthday;

	@NotBlank
	private String gender;

	@NotBlank
	private String cellphone;

	@NotBlank
	private String phone;

	@NotBlank
	private String address;

	@NotBlank
	private String profession;

	@NotNull
	private Double incomes;

}
