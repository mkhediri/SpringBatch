package com.batchtraining.mk.springbatch.model;


import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@XmlRootElement(name = "student")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentXml {

	private Long id;

	private String firstName;

	private String lastName;

	private String email;

}
