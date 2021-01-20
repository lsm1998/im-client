package com.lsm1998.im;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class SimpleTest
{
    int[] arr = {0, 1, 0, 3, 0};

    @Test
    public void test1()
    {
        int count = 0;
        for (int i = 0; i < arr.length - count; i++)
        {
            if (arr[i] == 0)
            {
                swap(i, arr.length - count - 1);
                count++;
            }
        }
        System.out.println(Arrays.toString(this.arr));
    }

    private void swap(int i, int j)
    {
        if (this.arr[j] == 0)
        {
            swap(i,j - 1);
        }
        int temp = this.arr[i];
        this.arr[i] = this.arr[j];
        this.arr[j] = temp;
    }
}
