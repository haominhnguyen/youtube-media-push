import com.jcraft.jsch.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by haotr on 22/12/2019.
 */
public class Main {
    public static void main(String[] args) {
        String user = "Administrator";
        String password = "mypassword";
        String host = "ec2-18-219-186-22.us-east-2.compute.amazonaws.com";
        int port = 3389;
        String remoteFile = "C:\\Users\\Administrator\\Downloads\\y4.txt";

        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Crating SFTP Channel.");
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            System.out.println("SFTP Channel created.");

            InputStream inputStream = sftpChannel.get(remoteFile);

            Scanner scanner = new Scanner(new InputStreamReader(inputStream));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }
}
