package com.clamav.backend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class AuthenticationResponse implements Serializable {

    private static final long serialVersionUID = -897933201988899999L;

    private final String token;

    private final String avatar;

    private final String userName;

    private final String createTime;


}