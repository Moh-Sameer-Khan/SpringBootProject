package org.geekster;

public class MailMetaData {
    //    this class for bcz we need some hard code like SMTP..
//    we need 4 things host,port,ssl enable, authentication
    public static final String hostServer = "smtp.gmail.com"; // server mail // search google..
    public static final String port = "465"; //  // smtp gmail com port we need
    public static final String sslProperty = "mail.smtp.ssl.enable"; // ssl for 465 is TLS so use
    public static final String authPerm = "mail.smtp.auth";  // authorization permission need
    //    these four things we need in our mail process up

//    for sender need password and email
    public static final String myUserMail = "mskhanm1819@gmail.com";
//    bcz we can not make gmail password bcz we are using third party application mean java so we creates app password(tempararily password)
    public static final String userPassword = "uhhxwgwmfzxcesnr";  //  app password

//    for receiver need email
    public static final String receiverMail = "shahhusainh532@gmail.com";
}
