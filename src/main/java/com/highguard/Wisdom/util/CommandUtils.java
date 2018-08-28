package com.highguard.Wisdom.util;


import com.highguard.Wisdom.mgmt.hibernate.beans.Lattice;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;

//指令开关帮助类
public class CommandUtils {

    public static String OPENLOCKFLAG_OPEN = "01";
    public static String OPENLOCKFLAG_CLOSE = "10";
    public static String OPENLOCKFLAG_TOGGLE = "00";
    public static String OPENLOCKFLAG_KEEP = "11";

    public static class DeviceStatus {
        public Lattice lattice;
        public int lock;
        public int light;
        public int toy;
    }
    /**
     * 根据控制器生成开锁指令
     * @param lattice
     * @return
     */
    public static String makeOpenlock(Lattice lattice){
        String command = "SPTP-1234-";
        String[] tokens = new String[8];
        for (int i = 0; i < tokens.length; i++){
            tokens[i] = OPENLOCKFLAG_KEEP;
        }
        int index = Integer.valueOf(lattice.getQrcode());
        tokens[index-1] = OPENLOCKFLAG_OPEN;

        command += lattice.getLockid()+"-";

        StringBuilder sp = new StringBuilder();
        for (String token : tokens) {
            sp.append(token);
        }

        String hex = Integer.toHexString(Integer.parseInt(sp.toString(),2));

        hex = formatString(hex, "0000");

        command +="FFFF-"+hex;

        return command.toUpperCase();
    }

    public static String makeQueryStatus(Lattice lattice){
        String command = "SPTP-1234-";
        command += lattice.getLockid()+"-FFFF-FFFF";
        return command;
    }


    public static DeviceStatus readStatus(Lattice lattice, String[] tokens){
        DeviceStatus status = new DeviceStatus();

        if(!Objects.equals(tokens[0], "READ") && ! Objects.equals(tokens[0], "OK SPTP")){
            return null;
        }

        if( !lattice.getLockid().equals(tokens[1]) ){
            return null;
        }

        String command = null;
        if( "READ".equals(tokens[0]) ){
            command = tokens[2];
        }
        else if( "OK SPTP".equals(tokens[0]) ){
            command = tokens[2];
        }

        if(StringUtils.isEmpty(command) ){
            return null;
        }

        String lookToken = formatString(Integer.toBinaryString(Integer.decode("0x"+command.substring(2,4))));
        String lightToken = formatString(Integer.toBinaryString(Integer.decode("0x"+command.substring(4,6))));
        String toyToken = formatString(Integer.toBinaryString((Integer.decode("0x"+command.substring(8)))));


        int index = Integer.valueOf(lattice.getQrcode());

        if(Objects.equals(lookToken.substring(index - 1, index), "1")){
            status.lock = 1;
        }
        else{
            status.lock = 0;
        }

        if(Objects.equals(lightToken.substring(index - 1, index), "1")){
            status.light = 1;
        }
        else{
            status.light = 0;
        }

        if(Objects.equals(toyToken.substring(index - 1, index), "1")){
            status.toy = 1;
        }
        else{
            status.toy = 0;
        }

        status.lattice = lattice;
        return status;
    }


    private static String formatString(String value, String format){
        value = format.substring(0, format.length()-value.length())+value;
        return value;
    }

    private static String formatString(String value){
        return formatString(value, "00000000");
    }
}
