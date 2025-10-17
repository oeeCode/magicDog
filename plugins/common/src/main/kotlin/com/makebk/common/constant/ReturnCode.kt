package com.makebk.common.constant

object ReturnCode {
    /**
     * 操作成功
     */
    val SUCCESS = "200"

    val UNAUTHENTICATED_ERROR = "401"

    val AUTHORIZATION_ERROR = "403"

    /**
     * 9999:操作失败
     */
    val ERROR = "9999"

    /**
     * 9001:系统繁忙
     */
    val CONNECTION_ERROR = "9001"

    /**
     * 9002:未知错误
     */
    val UNKNOWN_ERROR = "9002"

    /**
     * 9003:数据库异常
     */
    val DATA_ACCESS_ERROR = "9003"

    /**
     * 1000:非法参数
     */
    val INVALID_PARAMTER = "1000"

    /************************************ bussiness return code ********************************************/

    /************************************ bussiness return code  */
    /**
     * 文件上传失败
     */
    val FILE_UPLOAD_ERROR = "9010"

    /**
     * 操作失败，文件不存在
     */
    val FILE_DOES_NOT_EXIST = "9011"

    /**
     * 操作失败，请检查OSS配置信息
     */
    val OSS_OP_ERROR = "9012"

    /**
     * 操作失败，不支持的文件类型!
     */
    val UNSUPPORTED_FILE_TYPE = "9013"

    /**
     * 操作失败，手机号码不存在！
     */
    val NUMBER_DOES_NOT_EXIST = "9101"

    /**
     * 登陆失败，账号或密码错误！
     */
    val ACCOUNT_NUMBER_OR_PASSWORD = "9102"

    /**
     * 操作失败，消息发送过于频繁！
     */
    val ACCOUNT_NUMBER_REPEAT = "9103"

    /**
     * 验证码已过期请重新发送
     */
    val PLEASE_SEND_IT_AGAIN = "9104"

    /**
     * 验证码不正确
     */
    val INCORRECT_VERIFICATION_CODE = "9105"

    /**
     * 注册失败，手机号码已存在！
     */
    val MOBILE_NUMBER_ALREADY_EXISTS = "9106"

    /**
     * 数据不存在
     */
    val DATA_DOES_NOT_EXIST = "9107"
}
