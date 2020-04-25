package com.mydogspies.xflytools.tests;

public class ExtPlaneDataHandler implements Runnable {

    private String data;

    public ExtPlaneDataHandler(String data) {
        this.data = data;
    }


    @Override
    public void run() {

        System.out.println(data);
        System.out.println("tick");

    }

}
