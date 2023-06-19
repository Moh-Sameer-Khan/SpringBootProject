package org.geekster;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailHandler {
    //    for using this class make an object in main class then we can access all method or property
    public void sendMail() {
        //        System.out.println(); /// go inside System -- props--properties--hashtable then --
        Properties sysProperties = System.getProperties(); // get properties give an object -- Hash Table --> (This give us a hash-map / hash table)

//        defining meta data here........
        sysProperties.put("mail.smtp.host", MailMetaData.hostServer);
        sysProperties.put("mail.smtp.port", MailMetaData.port);

        sysProperties.put(MailMetaData.sslProperty, "true");
        sysProperties.put(MailMetaData.authPerm, "true");


        //      about this object read singleton pattern
        Authenticator mailAuthenticator = new CustomizeMailAuthentication();  // autehnticator is parent class so its contain the child class
        //        creates a session using sender email and password
        Session mailSession = Session.getInstance(sysProperties, mailAuthenticator);

//        MIME message build
        MimeMessage mailMessage = new MimeMessage(mailSession);

        try {
//        from my end (Sender)
            mailMessage.setFrom(MailMetaData.myUserMail);
            mailMessage.setSubject("This is my Weekly Test Project By Java Code Testing.....");
            mailMessage.setText("Hey! This is Sameer who is trying to send mail using java ..Thanks!");

//        set the receiver
            Address receiverEmail = new InternetAddress(MailMetaData.receiverMail);
            mailMessage.setRecipient(Message.RecipientType.TO, receiverEmail);

            Transport.send(mailMessage);
        }catch (Exception mailException) {
            System.out.println(mailException);
        }
    }
}
