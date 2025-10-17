package com.makebk.common.constant

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class DefaultMethodEnums {
    ALL("all"),
    PAGE("page"),
    ADD("add"),
    FINDALL("findAll"),
    FINDID("findId"),
    FIND(""),
    LIST("list"),
    UPDATE("update"),
    DELETE("delete"),
    SUBMIT("submit"),
    REMOVE("remove"),
    ;

    private var methodName: String;

    constructor(methodName: String) {
        this.methodName = methodName;
    }

    companion object {
        fun getEnum(methodName: String?): DefaultMethodEnums? {
            return entries.firstOrNull { enm ->
                enm.methodName == methodName
            }
        }
    }
}