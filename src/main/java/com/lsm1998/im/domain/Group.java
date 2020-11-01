package com.lsm1998.im.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Group
{
    private Long id;
    private String groupName;
}
