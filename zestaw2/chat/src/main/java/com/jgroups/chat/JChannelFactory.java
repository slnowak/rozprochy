package com.jgroups.chat;

import org.jgroups.JChannel;
import org.jgroups.Receiver;
import org.jgroups.protocols.*;
import org.jgroups.protocols.pbcast.*;
import org.jgroups.stack.Protocol;
import org.jgroups.stack.ProtocolStack;

import java.net.InetAddress;
import java.util.Optional;

/**
 * Created by novy on 25.03.15.
 */
public class JChannelFactory {

    private static final String DEFAULT_HOSTNAME = "224.0.0.1";

    public static JChannel channelFor(String channelName, Receiver receiver) throws Exception {
        return channelFor(channelName, null, receiver);
    }

    public static JChannel channelFor(String channelName, String hostName, Receiver receiver) throws Exception {
        final JChannel channel = new JChannel(false);
        final ProtocolStack defaultProtocolStack = defaultProtocolStackForAddress(hostName);

        channel.setName(channelName);
        channel.setProtocolStack(defaultProtocolStack);
        defaultProtocolStack.init();
        channel.setReceiver(receiver);

        return channel;
    }

    private static ProtocolStack defaultProtocolStackForAddress(String hostName) throws Exception {

//        final String inetAddressName = Optional.ofNullable(hostName).orElse(DEFAULT_HOSTNAME);
        Protocol udp = new UDP();
        if (hostName != null) {
            udp = udp.setValue("mcast_group_addr", InetAddress.getByName(hostName));
        }

        return new ProtocolStack()
                .addProtocol(udp
//                        new UDP()
//                                .setValue(
//                                        "mcast_group_addr",
//                                        InetAddress.getByName(inetAddressName)
//                                )
                )
                .addProtocol(new PING())
                .addProtocol(new MERGE2())
                .addProtocol(new FD_SOCK())
                .addProtocol(
                        new FD_ALL()
                                .setValue("timeout", 12000)
                                .setValue("interval", 3000)
                )
                .addProtocol(new VERIFY_SUSPECT())
                .addProtocol(new BARRIER())
                .addProtocol(new NAKACK())
                .addProtocol(new UNICAST2())
                .addProtocol(new STABLE())
                .addProtocol(new GMS())
                .addProtocol(new UFC())
                .addProtocol(new MFC())
                .addProtocol(new FRAG2())
                .addProtocol(new STATE_TRANSFER())
                .addProtocol(new FLUSH());
    }
}
