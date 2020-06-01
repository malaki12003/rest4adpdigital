package com.hamrasta.sms_tools.util;


import com.hamrasta.sms_tools.webservice.SendResult;
import org.apache.axis.client.Call;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;
import java.rmi.RemoteException;


public class SmsUtil {
    public static SendResult send(String phone, String content, String username,
                                  String password, String sourceNo) throws
            ServiceException, RemoteException {
        if (phone == null || phone.isEmpty()) {
            System.out.println("SMS - " + "wrong phone " + phone);
            throw new PhoneNumberException();
        }
        if (phone.startsWith("00"))
            phone = phone.substring(2);
        else if (phone.startsWith("0"))
            phone = "98" + phone.substring(1);
        final String cell = phone;
        System.out.println("SMS - sending sms to " + cell);
        return doSendTextMessage(cell, content, SmsType.TEXT, username, password, sourceNo);


    }

    private static SendResult doSendTextMessage(String to, String text, SmsType smsType, String username,
                                                String password, String sourceNo) throws
            ServiceException, RemoteException {
        String END_POINT_URL = "http://ws.adpdigital.com/services/MessagingService?wsdl";
        String URN = "urn:SOAPSmsQueue";
        String ENQUEUE_METHOD_CALL = "send";
        String RECIPIENT_NUMBER = to;

        org.apache.axis.client.Service service = new org.apache.axis.client.Service();
        Call call = (Call) service.createCall();
        call.setTargetEndpointAddress(END_POINT_URL);
        call.setOperationName(new QName(URN, ENQUEUE_METHOD_CALL));

        call.setReturnType(new javax.xml.namespace.QName("http://www.adpdigital.com/services/messaging", "SendResult"));
        call.setReturnClass(com.hamrasta.sms_tools.webservice.SendResult.class);
        call.setReturnQName(new javax.xml.namespace.QName("http://www.adpdigital.com/services/messaging", "sendMultipleReturn"));
//        QName q = new QName("http://www.w3.org/2001/XMLSchema", "sendResponse");

//        BeanSerializerFactory bsf = new BeanSerializerFactory(SendReturn.class, q);
//        BeanDeserializerFactory bdf = new BeanDeserializerFactory(SendReturn.class, q);
//        call.registerTypeMapping(SendReturn.class, q, bsf, bdf);
        call.setTimeout(10 * 1000);
        call.addParameter("username", XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter("password", XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter("sourceNo", XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter("destNo", XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter("sourcePort", XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter("destPort", XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter("clientId", XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter("messageType", XMLType.XSD_SHORT, ParameterMode.IN);
        call.addParameter("encoding", XMLType.XSD_SHORT, ParameterMode.IN);
        call.addParameter("longSupported", XMLType.XSD_BOOLEAN, ParameterMode.IN);
        call.addParameter("dueTime", XMLType.XSD_DATETIME, ParameterMode.IN);
        call.addParameter("content", XMLType.XSD_STRING, ParameterMode.IN);

        Object[] params = {username, password, sourceNo, RECIPIENT_NUMBER, "", "", "", smsType.code, (short) 2, true, null, text};
        double startTime = System.currentTimeMillis();
        SendResult result = (SendResult) call.invoke(params);
        System.out.println("SMS - " + to + " - " + result.getStatus());
        System.out.println("SMS - " + to + " request response-time=" + (System.currentTimeMillis() - startTime) / 1000 + " sec.s");
        return result;

    }

    public static class PhoneNumberException extends RuntimeException {

    }

}
