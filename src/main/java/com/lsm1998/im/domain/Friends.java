package com.lsm1998.im.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Friends
{
    private Long groupId;
    private List<User> userList;
}
