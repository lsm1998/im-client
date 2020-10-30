package com.lsm1998.im.domain;

import lombok.Data;

@Data
public class User
{
    private Long id;

    private String nickname;

    private String aesKey;

    private String headImg;

    private String token;
}
