package com.vijayrc.supportguy;

import com.jcraft.jsch.*;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.junit.Test;

import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Vector;

public class RemoteTest {

    @Test
    public void shouldConnect() throws Exception {
        InetAddress address = InetAddress.getByName("nasnmasmo");
        System.out.println(address.getHostAddress());
        System.out.println(address.isReachable(4000));
        SocketAddress socketAddress = new InetSocketAddress(address, 22);
        Socket socket = new Socket();
        socket.connect(socketAddress, 22);
        System.out.println(socket.isConnected());
    }

    @Test
    public void shouldConnectViaFTPToListAndDownloadSelectedFiles() throws Exception {
        FTPClient client = new FTPClient();
        client.connect("nasnmasdev");
        client.login("vichakra", "####");

        for (FTPFile ftpFile : client.listFiles("/was7blue2/logs/")) {
            String name = ftpFile.getName();
            if (!name.contains("router")) continue;
            FileOutputStream fos = new FileOutputStream(name);
            client.retrieveFile("/was7blue2/logs/" + name, fos);
            System.out.println("downloaded " + name);
            fos.close();
        }
        client.disconnect();
    }

    @Test
    public void shouldUseJSch() {
        JSch jsch = new JSch();
        Session session = null;
        try {
            session = jsch.getSession("vichakra", "nasnmasdev", 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword("######");
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.get("public/csrs-timesheets", "C:/work/csr.txt");
            Vector vector = sftpChannel.ls("public/tests");
            for (Object o : vector) {
                System.out.println(o);
                ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry) o;
                System.out.println(lsEntry.getFilename() + "|" + lsEntry.getAttrs().getMtimeString() + "|" + lsEntry.getAttrs().isDir());

            }
            sftpChannel.exit();
            session.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }

    }
}
