package redesii.ifb.com.simplesmtp;

import java.net.*;
import java.io.*;

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
            DataOutputStream os;
            DataInputStream is;

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

            smtpPipe.setKeepAlive(false);
            smtpPipe.setSoLinger(true, 10);
            smtpPipe.setReuseAddress(true);
            smtpPipe.setTcpNoDelay(true);

            inn = smtpPipe.getInputStream();
            //outt = smtpPipe.getOutputStream();

            os = new DataOutputStream(smtpPipe.getOutputStream());
            in = new BufferedReader(new InputStreamReader(inn));

            os.writeBytes("HELO " + from + "\n");
            in.readLine();
            os.writeBytes("MAIL From: " + from + "\n");
            in.readLine();
            os.writeBytes("RCPT TO: " + to + "\n");
            in.readLine();
            os.writeBytes("DATA\r\n");
            os.writeBytes("SUBJECT: " + subject + "\r\n");
            os.writeBytes("\r\n"+message+"\r\n");
            os.writeBytes("\r\n.\r\n");
            try{
                Thread.sleep(1000);
            }catch (Exception e){
                return e.getMessage();
            }

            os.writeBytes("QUIT\n");

            return "Mensagem enviada com sucesso!";
        }catch (IOException e){
            return e.getMessage();
        }
    }
}

//https://stackoverflow.com/questions/17440795/send-a-string-instead-of-byte-through-socket-in-java