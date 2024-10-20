package com.shivaya.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@AllArgsConstructor
@Getter
public class NameFilterTypeData implements IFilterTypeData{
    public String value;
    public  String matchWay;
}
