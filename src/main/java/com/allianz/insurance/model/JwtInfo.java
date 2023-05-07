package com.allianz.insurance.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtInfo {
    private String sub;
    private String email;
    private String iat;
}
