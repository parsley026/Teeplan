package com.example.teeplan.mailRelatedUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailAPI extends AsyncTask<Void,Void,Boolean>  {

    private Context mContext;
    private Session mSession;
    private String mEmail;
    private String mSubject;
    private String mMessage;
    private final String username = "teeplanreport@gmail.com";
    private final String password = "xger avrf ogpr sfsw";

    private ProgressDialog mProgressDialog;

    public JavaMailAPI(Context mContext, String mEmail, String mSubject, String mMessage) {
        this.mContext = mContext;
        this.mEmail = mEmail;
        this.mSubject = mSubject;
        this.mMessage = mMessage;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = ProgressDialog.show(mContext,"Sending report", "Please wait...",false,false);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        mProgressDialog.dismiss();

        if(result) {
            Toast.makeText(mContext,"Report Sent",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext,"Failed to send report",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        mSession = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            MimeMessage mm = new MimeMessage(mSession);

            mm.setFrom(new InternetAddress(username));
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(mEmail));
            mm.setSubject(mSubject);
            mm.setText(mMessage);
            Transport.send(mm);

            return true;

        } catch (MessagingException e) {
            return false;
        }
    }
}
