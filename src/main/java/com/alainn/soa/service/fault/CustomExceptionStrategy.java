package com.alainn.soa.service.fault;


import javax.xml.namespace.QName;

import org.apache.cxf.interceptor.Fault;
import org.mule.api.ExceptionPayload;
import org.mule.api.MuleEvent;
import org.mule.exception.CatchMessagingExceptionStrategy;
import org.mule.message.DefaultExceptionPayload;

public class CustomExceptionStrategy extends CatchMessagingExceptionStrategy {
    @Override
    protected void nullifyExceptionPayloadIfRequired(MuleEvent event) {
        //do nothing
    }

    @Override
    protected MuleEvent afterRouting(Exception exception, MuleEvent event) {
        //Create the Soap Fault as desired
        Fault fault = new Fault(exception);
        QName errorCode = new QName("customer-already-exists");
        fault.setFaultCode(errorCode);
        fault.setMessage("This email has already been registered");

        ExceptionPayload exceptionPayload = new DefaultExceptionPayload(fault);
        event.getMessage().setExceptionPayload(exceptionPayload);


        return super.afterRouting(exception, event);    
    }
}