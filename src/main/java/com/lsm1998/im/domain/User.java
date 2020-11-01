package com.lsm1998.im.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User
{
    private Long id;

    private String nickname;

    private String username;

    private transient String password;

    private transient String aesKey;

    private String headImg;

    private String token;

    private Character sex;

    private String signature;

    private String birthDay;

    public Character getSex()
    {
        return this.sex == null ? '男' : this.sex;
    }
}
