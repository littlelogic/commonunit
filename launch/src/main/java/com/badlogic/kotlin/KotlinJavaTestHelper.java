package com.badlogic.kotlin;

import com.badlogic.utils.ALog;

import java.sql.Array;

public class KotlinJavaTestHelper {

    {

        int test_A = KotlinActivity.getNORMAL_VIEW();
        test_A = KotlinActivity.Companion.getTest_a();
        test_A = KotlinActivity.test_aa;

        KotlinTestHelper.Mycompanion.scheduleSomething();
        ALog.i(()-> "test-KotlinActivity.test_aa-> "+ KotlinActivity.test_aa);

    }


    private void test_a001(String[] args ){
        KotlinActivity hKotlinActivity = new KotlinActivity();
        hKotlinActivity.setSelfNameNumber(33);
        ALog.i(()-> "KotlinJavaTestHelper-test_a001" +
                "-KotlinActivity.test_aa-> " + KotlinActivity.test_aa +
                "-args[0]-> " + args[0] +
                "-hKotlinActivity.getSelfNameNumber()-> " + hKotlinActivity.getSelfNameNumber() +
                "");

    }
}
