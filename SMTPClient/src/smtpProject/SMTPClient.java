/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smtpProject;

/**
 *
 * @author gustavo
 * Sends emails using SMTP protocol.
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SMTPClient {

  public static void main(String args[]) throws IOException, UnknownHostException {
    String msgContent = "my message here";
    String from = "gustavo";
    String to = "receiver";
    String mailHost = "localhost";
    
    SMTP mail = new SMTP(mailHost);
    if (mail != null) {
      if (mail.send(from, to, msgContent)) {
        System.out.println("Mail sent.");
      } else {
        System.out.println("Connect to SMTP server failed!");
      }
    }
    System.out.println("Done.");
  }

  static class SMTP {
      
    private final static int SMTP_PORT = 25;
    InetAddress mailHost;
    InetAddress localhost;
    BufferedReader in;
    PrintWriter out;

    public SMTP(String host) throws UnknownHostException {
        mailHost = InetAddress.getByName(host);
        localhost = InetAddress.getLocalHost();
        System.out.println("mailhost = " + mailHost);
        System.out.println("localhost= " + localhost);
        System.out.println("SMTP constructor done\n");
    }

    public boolean send(String from, String to, String msgContent) throws IOException {
        
        Socket smtpPipe;
        InputStream inn;
        OutputStream outt;

        smtpPipe = new Socket(mailHost, SMTP_PORT);

        if (smtpPipe == null) {
          return false;
        }

        inn = smtpPipe.getInputStream();
        outt = smtpPipe.getOutputStream();

        in = new BufferedReader(new InputStreamReader(inn));
        out = new PrintWriter(new OutputStreamWriter(outt), true);

        if (inn == null || outt == null) {
          System.out.println("Failed to open streams to socket.");
          return false;
        }
        String initialID = in.readLine();

        System.out.println(initialID);
        System.out.println("HELO " + localhost.getHostName());
        out.println("HELO " + localhost.getHostName());

        String welcome = in.readLine();
        System.out.println(welcome);
        System.out.println("MAIL From:<" + from + ">");
        out.println("MAIL From: " + from);

        String senderOK = in.readLine();
        System.out.println(senderOK);
        System.out.println("RCPT TO:<" + to + ">");
        out.println("RCPT TO: " + to);

        String recipientOK = in.readLine();
        System.out.println(recipientOK);
        System.out.println("DATA");

        // gambiarra para funcionar o comando DATA
        out.println("DATA");
        in.readLine();
        // :)

        out.println("DATA " + msgContent);
        out.println(".");
        System.out.println(in.readLine());

        System.out.println("QUIT");
        out.println("QUIT");
        return true;
    }
  }
}