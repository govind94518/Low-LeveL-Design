package com.shivaya.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Map;
@Builder
@Data
@AllArgsConstructor
@Getter
public class BooleanFilterTypeData implements IFilterTypeData{
    Map<String,Object>left;
    Map<String,Object>right;
}
