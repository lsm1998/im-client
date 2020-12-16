package com.lsm1998.im;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class SocketTest
{
    @Test
    public void test() throws Exception
    {
        char[] arr = {'a', 'a', 'b', 'a'};
        System.out.println(Arrays.toString(demo(arr)));
    }

    char[] demo(char[] arr)
    {
        if (arr == null || arr.length == 1)
        {
            return arr;
        }
        char lastChar = arr[0];
        for (int i = 1; i < arr.length; i++)
        {
            if (arr[i] == lastChar)
            {
                swapEle(arr, i, arr[i]);
            }
            if (arr[i] == lastChar)
            {
                return null;
            }
            lastChar = arr[i];
        }
        return arr;
    }

    private void swapEle(char[] arr, int index, char c)
    {
        if (index == arr.length - 1) return;
        for (int i = index + 1; i < arr.length; i++)
        {
            if (arr[i] != c)
            {
                char temp = arr[index];
                arr[index] = arr[i];
                arr[i] = temp;
                break;
            }
        }
    }
}
