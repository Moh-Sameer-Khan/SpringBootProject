package org.geekster;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class CustomizeMailAuthentication extends Authenticator {
    //    for this class go in authentication after click there is abstract...so extends that class
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(MailMetaData.myUserMail, MailMetaData.userPassword);
    }
}
