package com.avalblock.sms_tools.util;

public enum SmsType
{
    TEXT((short)1),
    FLASH((short)2),
    BINARY((short)6),
    ;
    short code;

    SmsType(short code) {
        this.code = code;
    }
}
