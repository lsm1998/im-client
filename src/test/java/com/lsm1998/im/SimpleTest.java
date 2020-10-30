package com.lsm1998.im;

import com.lsm1998.im.utils.ImageUtil;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class SimpleTest
{
    @Test
    public void test1()
    {
        Image image = ImageUtil.getImageByUrl("http://git.strongberry.cn/uploads/-/system/user/avatar/1/avatar.png?width=40");
        System.out.println(image);
    }
}
