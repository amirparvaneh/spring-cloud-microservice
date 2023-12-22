package com.cloud.customer.mapper;

import com.cloud.customer.dto.CustomerDto;
import com.cloud.customer.dto.CustomerResponseDto;
import com.cloud.customer.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer customerDtoToCustomer(CustomerDto customerDto);

    CustomerResponseDto customerToCustomerResponse(Customer customer);
}
