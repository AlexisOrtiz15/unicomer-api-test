package com.unicomer.repository.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 * Aspirant Customer domain object.
 */

@Getter
@Setter
@Entity
@Table(name = "cat_aspirant_customer")
@Builder(toBuilder = true)
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
public class CatAspirantCustomer implements Serializable {

	private static final long serialVersionUID = 8858410678310702698L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "birthday")
	private LocalDate birthday;

	@Column(name = "gender")
	private String gender;

	@Column(name = "cellphone")
	private String cellphone;

	@Column(name = "phone")
	private String phone;

	@Column(name = "address")
	private String address;

	@Column(name = "profession")
	private String profession;

	@Column(name = "incomes")
	private Double incomes;
}
