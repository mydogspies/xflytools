package com.mydogspies.xflytools.tests;

public class ExtPlaneTest {

    public static void main(String[] args) {

        Thread extPlane = new ExtPlaneTCP();
        extPlane.start();

    }
}
