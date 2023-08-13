package org.geekster;

public class Sub {
    public static String sub;
    public Sub() {
        sub = this.sub;
    }

    public static String getsub() {
        return sub;
    }

    public static void setsub(String sub) {
        Sub.sub = sub;
    }
//
//    try{
//    MimeMessage message = new MimeMessage(MailHandler);
//
//
//            message.setFrom(MailMetaData.myUserMail);
//
//
//            message.setRecipient(Message.RecipientType.TO,new InternetAddress(MailMetaData.));
//
//
//            message.setSubject("Weekly test Mail");
//
//
//
//
//            message.setText(Sub.getsub());
//
//
//            Transport.send(message);
//            System.out.println("Mail successfully sent");
//        }
//        catch (MessagingException mex) {
//                mex.printStackTrace();
//        }
}
