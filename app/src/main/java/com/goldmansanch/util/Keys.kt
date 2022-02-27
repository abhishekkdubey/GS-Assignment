package com.goldmansanch.util

/**
 * Created by Abhishek on 26,February,2022 For GolmanSanch
 *
 */
object Keys
{
    init
    {
        System.loadLibrary("golmansanch")
    }

    external fun apodKey(): String

    external fun baseURL(): String
}