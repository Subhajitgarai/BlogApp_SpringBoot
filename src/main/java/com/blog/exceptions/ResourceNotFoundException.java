package com.blog.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//Creating the ResourceNotFoundException Class manually
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    String resourceName;
    String feildName;
    Object feildValue;
    public ResourceNotFoundException(String resourceName, String feildName, Object feildValue) {
        super(String.format("%s not found with %s : %s",resourceName,feildName,feildValue));
        this.resourceName = resourceName;
        this.feildName = feildName;
        this.feildValue = feildValue;
    }
}
