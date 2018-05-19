package redesii.ifb.com.simplesmtp;

import java.net.*;
import java.io.*;
import java.sql.Time;

public class SMTP {
    private String from;
    private String to;
    private String host;
    private String message;
    private String subject = "Teste";
    public String erro = "";
    //para comunicacao com o servidor
    InetAddress mailHost;
    InetAddress localhost;
    BufferedReader in;
    PrintWriter out;

    private final static int SMTP_PORT = 26;

    public SMTP(String from, String to, String host, String message) throws UnknownHostException{
        this.from    = from;
        this.to      = to;
        this.host    = host;
        this.message = message;

        mailHost = InetAddress.getByName(this.host);
        localhost = InetAddress.getLocalHost();
    }

    public String send(){
        try {
            Socket smtpPipe;
            InputStream inn;
            OutputStream outt;
            String response;

            smtpPipe = new Socket(mailHost, SMTP_PORT);

            if (smtpPipe == null) {
                return "Não foi possivel criar conexão com o servidor";
            }

            if(!smtpPipe.isConnected()){
                return "Socket nao esta conectado.";
            }

            inn = smtpPipe.getInputStream();
            outt = smtpPipe.getOutputStream();

            if (inn == null || outt == null) {
                return "Falha ao abrir socket.";
            }

            in = new BufferedReader(new InputStreamReader(inn));
            out = new PrintWriter(new OutputStreamWriter(outt), true);

            String initialID = in.readLine();

            out.println("HELO " + from);
            response = in.readLine();

            out.println("MAIL From: " + from);
            response = in.readLine();

            out.println("RCPT TO: " + to);
            in.readLine();


            //out.println("jihugyj");
            // gambiarra para funcionar o comando DATA

            String commando = "DATA\r\n" +
                    "hueahuea\r\n" +
                    "\r\n.\r\n";
            out.println(commando );


            //in.readLine();
            // :)
            //out.println("");

            //
            // out.println("From:" + from);

            //out.println("\r\n.\r\n");
            //out.println("\n\n"+"."+"\n\n");



           out.println("QUIT");

            return response;

        }catch (IOException e){
            return e.getMessage();
        }
    }
}
