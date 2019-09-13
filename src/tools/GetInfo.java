package tools;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Formatter;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class GetInfo
{
    public static void main(final String[] args) {
        Config();
        getConfig();
        all();
    }
    
    public static void getIpconfig() {
        final Map<String, String> map = System.getenv();
        System.out.println(map);
        System.out.println(map.get("USERNAME"));
        System.out.println(map.get("COMPUTERNAME"));
        System.out.println(map.get("USERDOMAIN"));
        System.out.println(map.get("USER"));
    }
    
    public static void all() {
        final Properties �O���n = System.getProperties();
        System.out.println("Java���\�Эh���汾��" + �O���n.getProperty("java.version"));
        System.out.println("Java���\�Эh�������̣�" + �O���n.getProperty("java.vendor"));
        System.out.println("Java�����̵�URL��" + �O���n.getProperty("java.vendor.url"));
        System.out.println("Java�İ��b·����" + �O���n.getProperty("java.home"));
        System.out.println("Java��̓�M�CҎ���汾��" + �O���n.getProperty("java.vm.specification.version"));
        System.out.println("Java��̓�M�CҎ�������̣�" + �O���n.getProperty("java.vm.specification.vendor"));
        System.out.println("Java��̓�M�CҎ�����Q��" + �O���n.getProperty("java.vm.specification.name"));
        System.out.println("Java��̓�M�C���F�汾��" + �O���n.getProperty("java.vm.version"));
        System.out.println("Java��̓�M�C���F�����̣�" + �O���n.getProperty("java.vm.vendor"));
        System.out.println("Java��̓�M�C���F���Q��" + �O���n.getProperty("java.vm.name"));
        System.out.println("Java�\�Еr�h��Ҏ���汾��" + �O���n.getProperty("java.specification.version"));
        System.out.println("Java�\�Еr�h��Ҏ�����Q��" + �O���n.getProperty("java.specification.name"));
        System.out.println("Java���ʽ�汾̖��" + �O���n.getProperty("java.class.version"));
        System.out.println("Java���·����" + �O���n.getProperty("java.class.path"));
        System.out.println("���d��r������·���б�" + �O���n.getProperty("java.library.path"));
        System.out.println("Ĭ�J���R�r�ļ�·����" + �O���n.getProperty("java.io.tmpdir"));
        System.out.println("һ��������UչĿ䛵�·����" + �O���n.getProperty("java.ext.dirs"));
        System.out.println("����ϵ�y�Ę��ܣ�" + �O���n.getProperty("os.arch"));
        System.out.println("����ϵ�y�İ汾��" + �O���n.getProperty("os.version"));
        System.out.println("�ļ��ָ�����" + �O���n.getProperty("file.separator"));
        System.out.println("·���ָ�����" + �O���n.getProperty("path.separator"));
        System.out.println("�зָ�����" + �O���n.getProperty("line.separator"));
        System.out.println("�Ñ����~�����Q��" + �O���n.getProperty("user.name"));
        System.out.println("�Ñ�����Ŀ䛣�" + �O���n.getProperty("user.home"));
        System.out.println("�Ñ��Į�ǰ����Ŀ䛣�" + �O���n.getProperty("user.dir"));
    }
    
    public static void Config() {
        try {
            final InetAddress addr = InetAddress.getLocalHost();
            final String ip = addr.getHostAddress().toString();
            final String hostName = addr.getHostName().toString();
            System.out.println("���CIP��" + ip + "\n���C���Q:" + hostName);
            final Properties �O���n = System.getProperties();
            System.out.println("����ϵ�y�����Q��" + �O���n.getProperty("os.name"));
            System.out.println("����ϵ�y�İ汾��" + �O���n.getProperty("os.version"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void getConfig() {
        try {
            final InetAddress address = InetAddress.getLocalHost();
            final NetworkInterface ni = NetworkInterface.getByInetAddress(address);
            byte[] mac = ni.getHardwareAddress();
            if (mac == null) {
                mac = ni.getInetAddresses().nextElement().getAddress();
            }
            final String sIP = address.getHostAddress();
            String sMAC = "";
            final Formatter formatter = new Formatter();
            for (int i = 0; i < mac.length; ++i) {
                sMAC = formatter.format(Locale.getDefault(), "%02X%s", mac[i], (i < mac.length - 1) ? "-" : "").toString();
            }
            System.out.println("IP��" + sIP);
            System.out.println("MAC��" + sMAC);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
