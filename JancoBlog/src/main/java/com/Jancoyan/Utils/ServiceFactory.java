package com.Jancoyan.Utils;

public class ServiceFactory {

    public static Object getService(Object service){

        return new TransactionInvocationHandler(service).getProxy();

    }

}

