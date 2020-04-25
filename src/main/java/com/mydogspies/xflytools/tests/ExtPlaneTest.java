package com.mydogspies.xflytools.tests;

public class ExtPlaneTest {

    public static void main(String[] args) {

        Thread extPlaneSend = new ExtPlaneTCPSend();
        extPlaneSend.start();

        Thread extPlaneRec = new ExtPlaneTCPReceive();
        extPlaneRec.start();

    }
}
