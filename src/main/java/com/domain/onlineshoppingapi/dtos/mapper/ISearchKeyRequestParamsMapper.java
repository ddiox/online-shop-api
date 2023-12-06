package com.domain.onlineshoppingapi.dtos.mapper;

import org.mapstruct.Mapper;
import com.domain.onlineshoppingapi.dtos.param.SearchKeyParams;
import com.domain.onlineshoppingapi.dtos.request.SearchKeyRequest;

@Mapper(componentModel = "spring")
public interface ISearchKeyRequestParamsMapper {

    public SearchKeyRequest searchKeyParamsToSearchKeyRequest(SearchKeyParams searchKeyParams);
    
    public SearchKeyParams searchKeyRequestToSearchKeyParams(SearchKeyRequest searchKeyRequest);
} 
