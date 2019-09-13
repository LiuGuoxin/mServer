package gui;

import java.awt.Canvas;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.PreparedStatement;

import client.LoginCrypto;
import client.MapleCharacter;
import client.inventory.Equip;
import client.inventory.ItemFlag;
import client.inventory.MapleInventoryType;
import constants.GameConstants;
import constants.ServerConstants;
import database.DatabaseConnection;
import gui.entity.DropData;
import gui.entity.DropDataTableModel;
import handling.RecvPacketOpcode;
import handling.SendPacketOpcode;
import handling.channel.ChannelServer;
import handling.login.handler.AutoRegister;
import handling.world.World;
import scripting.PortalScriptManager;
import scripting.ReactorScriptManager;
import server.CashItemFactory;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.MapleShopFactory;
import server.ShutdownServer;
import server.Start;
import server.Timer;
import server.life.MapleMonsterInformationProvider;
import server.quest.MapleQuest;
import tools.MaplePacketCreator;

public class KinMS extends JFrame
{
    private static KinMS instance;
    private JPopupMenu popupMenu;
    private static ScheduledFuture<?> ts;
    private int minutesLeft;
    private static Thread t;
    private Canvas canvas1;
    private JTextPane chatLog;
    private Checkbox checkbox1;
    private JButton jButton1;
    private JButton jButton10;
    private JButton jButton11;
    private JButton jButton12;
    private JButton jButton13;
    private JButton jButton14;
    private JButton jButton15;
    private JButton jButton16;
    private JButton jButton17;
    private JButton jButton18;
    private JButton jButton19;
    private JButton jButton2;
    private JButton jButton20;
    private JButton jButton21;
    private JButton jButton22;
    private JButton jButton23;
    private JButton jButton24;
    private JButton jButton25;
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton5;
    private JButton jButton6;
    private JButton jButton7;
    private JButton jButton8;
    private JButton jButton9;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JPanel jPanel1;
    private JPanel jPanel10;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel7;
    private JPanel jPanel8;
    private JPanel jPanel9;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JTabbedPane jTabbedPane2;
    private JTable jTable1;
    private JTextField jTextField1;
    private JTextField jTextField10;
    private JTextField jTextField11;
    private JTextField jTextField12;
    private JTextField jTextField13;
    private JTextField jTextField14;
    private JTextField jTextField15;
    private JTextField jTextField16;
    private JTextField jTextField17;
    private JTextField jTextField18;
    private JTextField jTextField19;
    private JTextField jTextField2;
    private JTextField jTextField20;
    private JTextField jTextField21;
    private JTextField jTextField22;
    private JTextField jTextField23;
    private JTextField jTextField24;
    private JTextField jTextField25;
    private JTextField jTextField26;
    private JTextField jTextField27;
    private JTextField jTextField28;
    private JTextField jTextField29;
    private JTextField jTextField3;
    private JTextField jTextField30;
    private JTextField jTextField31;
    private JTextField jTextField32;
    private JTextField jTextField33;
    private JTextField jTextField34;
    private JTextField jTextField35;
    private JTextField jTextField36;
    private JTextField jTextField37;
    private JTextField jTextField4;
    private JTextField jTextField5;
    private JTextField jTextField6;
    private JTextField jTextField7;
    private JTextField jTextField8;
    private JTextField jTextField9;
    private JTextPane jTextPane1;
    
    public static final KinMS getInstance() {
        return KinMS.instance;
    }
    
    public KinMS() {
        this.minutesLeft = 0;
        final ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("gui/Icon.png"));
        this.setIconImage(icon.getImage());
        this.setTitle("Gui����̨ Vr.079");
        this.initComponents();
    }
    
    private void initComponents() {
        this.jScrollPane2 = new JScrollPane();
        this.jTextPane1 = new JTextPane();
        this.jLabel3 = new JLabel();
        this.jPanel9 = new JPanel();
        this.canvas1 = new Canvas();
        this.jScrollPane1 = new JScrollPane();
        this.chatLog = new JTextPane();
        this.jTabbedPane2 = new JTabbedPane();
        this.jPanel5 = new JPanel();
        this.jButton10 = new JButton();
        this.jTextField22 = new JTextField();
        this.jButton16 = new JButton();
        this.jPanel7 = new JPanel();
        this.jButton7 = new JButton();
        this.jButton8 = new JButton();
        this.jLabel2 = new JLabel();
        this.jPanel6 = new JPanel();
        this.jButton9 = new JButton();
        this.jButton1 = new JButton();
        this.jButton5 = new JButton();
        this.jButton4 = new JButton();
        this.jButton3 = new JButton();
        this.jButton2 = new JButton();
        this.jLabel1 = new JLabel();
        this.jButton6 = new JButton();
        this.jButton12 = new JButton();
        this.jButton19 = new JButton();
        this.jPanel8 = new JPanel();
        this.jButton11 = new JButton();
        this.jTextField1 = new JTextField();
        this.jTextField23 = new JTextField();
        this.jButton17 = new JButton();
        this.jPanel1 = new JPanel();
        this.jTextField2 = new JTextField();
        this.jButton13 = new JButton();
        this.jTextField3 = new JTextField();
        this.jTextField4 = new JTextField();
        this.jButton14 = new JButton();
        this.jTextField5 = new JTextField();
        this.jTextField6 = new JTextField();
        this.jTextField7 = new JTextField();
        this.jTextField8 = new JTextField();
        this.jTextField9 = new JTextField();
        this.jTextField10 = new JTextField();
        this.jTextField11 = new JTextField();
        this.jTextField12 = new JTextField();
        this.jTextField13 = new JTextField();
        this.jTextField14 = new JTextField();
        this.jTextField15 = new JTextField();
        this.jTextField16 = new JTextField();
        this.jTextField17 = new JTextField();
        this.jTextField18 = new JTextField();
        this.jTextField19 = new JTextField();
        this.jPanel2 = new JPanel();
        this.jTextField20 = new JTextField();
        this.jTextField21 = new JTextField();
        this.jButton15 = new JButton();
        this.jPanel4 = new JPanel();
        this.jTextField27 = new JTextField();
        this.jTextField28 = new JTextField();
        this.jTextField29 = new JTextField();
        this.jTextField30 = new JTextField();
        this.jTextField31 = new JTextField();
        this.jButton20 = new JButton();
        this.jButton21 = new JButton();
        this.jLabel4 = new JLabel();
        this.jTextField32 = new JTextField();
        this.jTextField33 = new JTextField();
        this.jSeparator1 = new JSeparator();
        this.jButton22 = new JButton();
        this.jButton23 = new JButton();
        this.jPanel10 = new JPanel();
        this.jTextField34 = new JTextField();
        this.jTextField35 = new JTextField();
        this.jButton24 = new JButton();
        this.jScrollPane3 = new JScrollPane();
        this.jTable1 = new JTable();
        this.jPanel3 = new JPanel();
        this.jTextField24 = new JTextField();
        this.jTextField25 = new JTextField();
        this.jButton18 = new JButton();
        this.jTextField26 = new JTextField();
        this.checkbox1 = new Checkbox();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.jTextField36 = new JTextField();
        this.jTextField37 = new JTextField();
        this.jButton25 = new JButton();
        this.jSeparator2 = new JSeparator();
        this.jScrollPane2.setViewportView(this.jTextPane1);
        this.jLabel3.setText("jLabel3");
        final GroupLayout jPanel9Layout = new GroupLayout(this.jPanel9);
        this.jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 100, 32767));
        jPanel9Layout.setVerticalGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 100, 32767));
        this.setDefaultCloseOperation(3);
        this.jScrollPane1.setViewportView(this.chatLog);
        this.jButton10.setText("���������");
        this.jButton10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton10ActionPerformed(evt);
            }
        });
        this.jTextField22.setText("�رշ���������ʱ��");
        this.jTextField22.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jTextField22ActionPerformed(evt);
            }
        });
        this.jButton16.setText("�رշ�����");
        this.jButton16.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton16ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel5Layout = new GroupLayout(this.jPanel5);
        this.jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addContainerGap().addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jButton10).addGroup(jPanel5Layout.createSequentialGroup().addComponent(this.jTextField22, -2, -1, -2).addGap(18, 18, 18).addComponent(this.jButton16))).addContainerGap(440, 32767)));
        jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addContainerGap().addComponent(this.jButton10).addGap(18, 18, 18).addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField22, -2, -1, -2).addComponent(this.jButton16)).addContainerGap(207, 32767)));
        this.jTabbedPane2.addTab("����������", this.jPanel5);
        this.jButton7.setText("��������");
        this.jButton7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton7ActionPerformed(evt);
            }
        });
        this.jButton8.setText("�����Ӷ");
        this.jButton8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton8ActionPerformed(evt);
            }
        });
        this.jLabel2.setText("����ϵ�У�");
        final GroupLayout jPanel7Layout = new GroupLayout(this.jPanel7);
        this.jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel7Layout.createSequentialGroup().addContainerGap().addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addGroup(jPanel7Layout.createSequentialGroup().addComponent(this.jButton7).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButton8))).addContainerGap(497, 32767)));
        jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel7Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButton7).addComponent(this.jButton8)).addContainerGap(227, 32767)));
        this.jTabbedPane2.addTab("��������", this.jPanel7);
        this.jButton9.setText("��������");
        this.jButton9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton9ActionPerformed(evt);
            }
        });
        this.jButton1.setText("���ظ���");
        this.jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton1ActionPerformed(evt);
            }
        });
        this.jButton5.setText("���ر���");
        this.jButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton5ActionPerformed(evt);
            }
        });
        this.jButton4.setText("�����̵�");
        this.jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton4ActionPerformed(evt);
            }
        });
        this.jButton3.setText("���ش�����");
        this.jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton3ActionPerformed(evt);
            }
        });
        this.jButton2.setText("���ط�Ӧ��");
        this.jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton2ActionPerformed(evt);
            }
        });
        this.jLabel1.setText("����ϵ�У�");
        this.jButton6.setText("���ذ�ͷ");
        this.jButton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton6ActionPerformed(evt);
            }
        });
        this.jButton12.setText("�����̳�");
        this.jButton12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton12ActionPerformed(evt);
            }
        });
        this.jButton19.setText("���Sql�B��");
        this.jButton19.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton19ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel6Layout = new GroupLayout(this.jPanel6);
        this.jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel6Layout.createSequentialGroup().addContainerGap().addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addGroup(jPanel6Layout.createSequentialGroup().addComponent(this.jButton6).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButton12)).addGroup(jPanel6Layout.createSequentialGroup().addComponent(this.jButton1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButton5).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButton2).addGap(12, 12, 12).addComponent(this.jButton3)).addGroup(jPanel6Layout.createSequentialGroup().addComponent(this.jButton9).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButton4)).addComponent(this.jButton19)).addContainerGap(293, 32767)));
        jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel6Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButton3).addComponent(this.jButton2)).addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButton1).addComponent(this.jButton5))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButton9).addComponent(this.jButton4)).addGap(10, 10, 10).addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButton6).addComponent(this.jButton12)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jButton19).addContainerGap(134, 32767)));
        this.jTabbedPane2.addTab("����ϵ��", this.jPanel6);
        this.jButton11.setText("�⿨���");
        this.jButton11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton11ActionPerformed(evt);
            }
        });
        this.jTextField1.setText("�����������");
        this.jTextField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jTextField1ActionPerformed(evt);
            }
        });
        this.jTextField23.setText("�����˺�");
        this.jTextField23.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jTextField23ActionPerformed(evt);
            }
        });
        this.jButton17.setText("�⿨�˺�");
        this.jButton17.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton17ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel8Layout = new GroupLayout(this.jPanel8);
        this.jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel8Layout.createSequentialGroup().addContainerGap().addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel8Layout.createSequentialGroup().addComponent(this.jTextField1, -2, 124, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButton11)).addGroup(jPanel8Layout.createSequentialGroup().addComponent(this.jTextField23, -2, 124, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButton17))).addContainerGap(454, 32767)));
        jPanel8Layout.setVerticalGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel8Layout.createSequentialGroup().addContainerGap().addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField1, -2, -1, -2).addComponent(this.jButton11)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField23, -2, -1, -2).addComponent(this.jButton17)).addContainerGap(219, 32767)));
        this.jTabbedPane2.addTab("���Ŵ���", this.jPanel8);
        this.jTextField2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jTextField2ActionPerformed(evt);
            }
        });
        this.jButton13.setText("���淢��");
        this.jButton13.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton13ActionPerformed(evt);
            }
        });
        this.jTextField3.setText("�������");
        this.jTextField4.setText("��ƷID");
        this.jButton14.setText("������Ʒ");
        this.jButton14.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton14ActionPerformed(evt);
            }
        });
        this.jTextField5.setText("����");
        this.jTextField6.setText("����");
        this.jTextField7.setText("����");
        this.jTextField8.setText("����");
        this.jTextField9.setText("����");
        this.jTextField10.setText("HP����");
        this.jTextField11.setText("MP����");
        this.jTextField12.setText("�Ӿ����");
        this.jTextField13.setText("������");
        this.jTextField14.setText("������Ʒʱ��");
        this.jTextField15.setText("���Խ���");
        this.jTextField16.setText("������");
        this.jTextField17.setText("ħ����");
        this.jTextField18.setText("�������");
        this.jTextField19.setText("ħ������");
        final GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jTextField2, -1, 556, 32767).addGap(18, 18, 18).addComponent(this.jButton13)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jTextField3, -2, 92, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextField4, -2, 77, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextField5, -2, 52, -2)).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jTextField9, -2, 58, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextField13)).addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jTextField8).addComponent(this.jTextField7)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jTextField11, -2, 79, -2).addComponent(this.jTextField12, -2, 79, -2))).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.jTextField6, -2, 58, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextField10, -2, 79, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jTextField16).addComponent(this.jTextField15).addComponent(this.jTextField14).addComponent(this.jTextField17)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jButton14, -1, -1, 32767).addComponent(this.jTextField18).addComponent(this.jTextField19)))).addContainerGap()));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField2, -2, -1, -2).addComponent(this.jButton13)).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField3, -2, -1, -2).addComponent(this.jTextField4, -2, -1, -2).addComponent(this.jTextField5, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField6, -2, -1, -2).addComponent(this.jTextField10, -2, -1, -2).addComponent(this.jTextField14, -2, -1, -2).addComponent(this.jTextField18, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField7, -2, -1, -2).addComponent(this.jTextField11, -2, -1, -2).addComponent(this.jTextField15, -2, -1, -2).addComponent(this.jTextField19, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField8, -2, -1, -2).addComponent(this.jTextField12, -2, -1, -2).addComponent(this.jTextField16, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField9, -2, -1, -2).addComponent(this.jTextField13, -2, -1, -2).addComponent(this.jTextField17, -2, -1, -2).addComponent(this.jButton14)).addContainerGap(95, 32767)));
        this.jTabbedPane2.addTab("ָ��/����", this.jPanel1);
        this.jTextField20.setText("��������");
        this.jTextField20.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jTextField20ActionPerformed(evt);
            }
        });
        this.jTextField21.setText("1���/2����/3���/4����");
        this.jButton15.setText("����ȫ�����/���þ�/���/����");
        this.jButton15.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton15ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addComponent(this.jTextField20, -2, 88, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextField21, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButton15).addContainerGap(214, 32767)));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField20, -2, -1, -2).addComponent(this.jTextField21, -2, -1, -2).addComponent(this.jButton15)).addContainerGap(248, 32767)));
        this.jTabbedPane2.addTab("����ϵ��", this.jPanel2);
        this.jTextField27.setHorizontalAlignment(0);
        this.jTextField27.setText("����ID");
        this.jTextField28.setHorizontalAlignment(0);
        this.jTextField28.setText("��ƷID");
        this.jTextField29.setHorizontalAlignment(0);
        this.jTextField29.setText("��С����");
        this.jTextField30.setHorizontalAlignment(0);
        this.jTextField30.setText("������");
        this.jTextField31.setHorizontalAlignment(0);
        this.jTextField31.setText("����");
        this.jButton20.setText("�������");
        this.jButton20.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton20ActionPerformed(evt);
            }
        });
        this.jButton21.setText("ȫ�����");
        this.jButton21.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton21ActionPerformed(evt);
            }
        });
        this.jLabel4.setForeground(new Color(255, 0, 0));
        this.jLabel4.setText("��ȫ��������ӡ�ɾ������ʱ������д����ID");
        this.jTextField32.setHorizontalAlignment(0);
        this.jTextField32.setText("����ID");
        this.jTextField33.setHorizontalAlignment(0);
        this.jTextField33.setText("��ƷID");
        this.jButton22.setText("����ɾ��");
        this.jButton22.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton22ActionPerformed(evt);
            }
        });
        this.jButton23.setText("ȫ��ɾ��");
        this.jButton23.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton23ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel4Layout = new GroupLayout(this.jPanel4);
        this.jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jSeparator1).addGroup(jPanel4Layout.createSequentialGroup().addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel4).addGroup(jPanel4Layout.createSequentialGroup().addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup().addComponent(this.jTextField32, -2, 80, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextField33, -2, 80, -2)).addGroup(jPanel4Layout.createSequentialGroup().addComponent(this.jTextField27, -2, 80, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextField28, -2, 80, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(jPanel4Layout.createSequentialGroup().addComponent(this.jTextField29, -2, 70, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextField30, -2, 70, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextField31, -2, 50, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 115, 32767).addComponent(this.jButton20, -2, 80, -2)).addGroup(GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup().addGap(0, 0, 32767).addComponent(this.jButton22, -2, 80, -2))))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jButton21, GroupLayout.Alignment.TRAILING, -2, 80, -2).addComponent(this.jButton23, GroupLayout.Alignment.TRAILING, -2, 80, -2)))).addContainerGap()));
        jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel4).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField27, -2, -1, -2).addComponent(this.jTextField28, -2, -1, -2).addComponent(this.jTextField29, -2, -1, -2).addComponent(this.jTextField30, -2, -1, -2).addComponent(this.jTextField31, -1, -1, -2).addComponent(this.jButton20).addComponent(this.jButton21)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jSeparator1, -2, 14, -2).addGap(1, 1, 1).addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField32, -2, -1, -2).addComponent(this.jTextField33, -2, -1, -2).addComponent(this.jButton22).addComponent(this.jButton23)).addContainerGap(179, 32767)));
        this.jTabbedPane2.addTab("�������", this.jPanel4);
        this.jTextField34.setHorizontalAlignment(0);
        this.jTextField34.setText("����ID");
        this.jTextField35.setHorizontalAlignment(0);
        this.jTextField35.setText("��ƷID");
        this.jButton24.setText("��ѯ");
        this.jButton24.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton24ActionPerformed(evt);
            }
        });
        this.jTable1.setModel(new DefaultTableModel(new Object[0][], new String[0]));
        this.jTable1.setRowHeight(25);
        this.jScrollPane3.setViewportView(this.jTable1);
        final GroupLayout jPanel10Layout = new GroupLayout(this.jPanel10);
        this.jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel10Layout.createSequentialGroup().addContainerGap().addGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane3, -1, 655, 32767).addGroup(jPanel10Layout.createSequentialGroup().addComponent(this.jTextField34, -2, 80, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jTextField35, -2, 80, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(this.jButton24, -2, 70, -2))).addContainerGap()));
        jPanel10Layout.setVerticalGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel10Layout.createSequentialGroup().addContainerGap().addGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField34, -2, -1, -2).addComponent(this.jTextField35, -2, -1, -2).addComponent(this.jButton24)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane3, -1, 232, 32767).addContainerGap()));
        this.jTextField34.getAccessibleContext().setAccessibleName("");
        this.jTabbedPane2.addTab("��ѯ�޸�", this.jPanel10);
        this.jTextField24.setText("�˺�");
        this.jTextField24.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jTextField24ActionPerformed(evt);
            }
        });
        this.jTextField25.setText("������");
        this.jTextField25.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jTextField25ActionPerformed(evt);
            }
        });
        this.jButton18.setText("�޸�����");
        this.jButton18.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton18ActionPerformed(evt);
            }
        });
        this.jTextField26.setText("��������");
        this.jTextField26.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jTextField26ActionPerformed(evt);
            }
        });
        this.checkbox1.setCursor(new Cursor(0));
        this.checkbox1.setName("123");
        this.checkbox1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                KinMS.this.checkbox1MouseClicked(evt);
            }
        });
        this.jLabel5.setForeground(new Color(255, 0, 0));
        this.jLabel5.setText("�޸�����");
        this.jLabel6.setForeground(new Color(255, 51, 0));
        this.jLabel6.setText("ע���˺�");
        this.jTextField36.setText("�˺�");
        this.jTextField37.setText("����");
        this.jButton25.setText("ע��");
        this.jButton25.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                KinMS.this.jButton25ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
        this.jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jTextField26, -2, 88, -2).addGroup(jPanel3Layout.createSequentialGroup().addComponent(this.jTextField24, -2, 88, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jTextField25, -2, 88, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jButton18).addComponent(this.checkbox1, -2, -1, -2))).addComponent(this.jLabel5).addComponent(this.jLabel6).addGroup(jPanel3Layout.createSequentialGroup().addComponent(this.jTextField36, -2, 88, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jTextField37, -2, 88, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jButton25, -2, 80, -2))).addContainerGap(388, 32767)).addComponent(this.jSeparator2));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel6).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField36, -2, -1, -2).addComponent(this.jTextField37, -2, -1, -2).addComponent(this.jButton25)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jSeparator2, -2, 10, -2).addGap(2, 2, 2).addComponent(this.jLabel5).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jTextField24, -2, -1, -2).addComponent(this.jTextField25, -2, -1, -2).addComponent(this.jButton18)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jTextField26, -2, -1, -2).addComponent(this.checkbox1, -2, -1, -2)).addContainerGap(129, 32767)));
        this.jTabbedPane2.addTab("�˺Ź���", this.jPanel3);
        final GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1).addGroup(layout.createSequentialGroup().addContainerGap(-1, 32767).addComponent(this.canvas1, -2, -1, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jTabbedPane2, -2, 680, -2).addGap(0, 0, 32767)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jTabbedPane2).addGap(5, 5, 5).addComponent(this.canvas1, -2, -1, -2).addGap(20, 20, 20).addComponent(this.jScrollPane1, -2, 93, -2).addContainerGap()));
        this.pack();
    }
    
    private void jButton1ActionPerformed(final ActionEvent evt) {
        for (final ChannelServer instance1 : ChannelServer.getAllInstances()) {
            if (instance1 != null) {
                instance1.reloadEvents();
            }
        }
        final String ��� = "[����ϵͳ] �������سɹ���";
        JOptionPane.showMessageDialog(null, "�������سɹ���");
        this.printChatLog(���);
    }
    
    private void jButton5ActionPerformed(final ActionEvent evt) {
        MapleMonsterInformationProvider.getInstance().clearDrops();
        final String ��� = "[����ϵͳ] �������سɹ���";
        JOptionPane.showMessageDialog(null, "�������سɹ���");
        this.printChatLog(���);
    }
    
    private void jButton6ActionPerformed(final ActionEvent evt) {
        SendPacketOpcode.reloadValues();
        RecvPacketOpcode.reloadValues();
        final String ��� = "[����ϵͳ] ��ͷ���سɹ���";
        JOptionPane.showMessageDialog(null, "��ͷ���سɹ���");
        this.printChatLog(���);
    }
    
    private void jButton3ActionPerformed(final ActionEvent evt) {
        PortalScriptManager.getInstance().clearScripts();
        final String ��� = "[����ϵͳ] ���������سɹ���";
        JOptionPane.showMessageDialog(null, "���������سɹ���");
        this.printChatLog(���);
    }
    
    private void jButton4ActionPerformed(final ActionEvent evt) {
        MapleShopFactory.getInstance().clear();
        final String ��� = "[����ϵͳ] �̵����سɹ���";
        JOptionPane.showMessageDialog(null, "�̵����سɹ���");
        this.printChatLog(���);
    }
    
    private void jButton2ActionPerformed(final ActionEvent evt) {
        ReactorScriptManager.getInstance().clearDrops();
        final String ��� = "[����ϵͳ] ��Ӧ�����سɹ���";
        JOptionPane.showMessageDialog(null, "��Ӧ�����سɹ���");
        this.printChatLog(���);
    }
    
    private void jButton9ActionPerformed(final ActionEvent evt) {
        MapleQuest.clearQuests();
        final String ��� = "[����ϵͳ] �������سɹ���";
        JOptionPane.showMessageDialog(null, "�������سɹ���");
        this.printChatLog(���);
    }
    
    private void jButton8ActionPerformed(final ActionEvent evt) {
        int p = 0;
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            ++p;
            cserv.closeAllMerchant();
        }
        final String ��� = "[�����Ӷ����ϵͳ] ��Ӷ���˱���" + p + "��Ƶ���ɹ���";
        JOptionPane.showMessageDialog(null, "��Ӷ���˱���" + p + "��Ƶ���ɹ���");
        this.printChatLog(���);
    }
    
    private void jButton7ActionPerformed(final ActionEvent evt) {
        int p = 0;
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                ++p;
                chr.saveToDB(true, true);
            }
        }
        final String ��� = "[��������ϵͳ] ����" + p + "���ɹ���";
        JOptionPane.showMessageDialog(null, ���);
        this.printChatLog(���);
    }
    
    private void jButton10ActionPerformed(final ActionEvent evt) {
        try {
            if (Start.Check) {
                Start.instance.startServer();
                final String ��� = "[������] �����������ɹ���";
                this.printChatLog(���);
            }
            else {
                JOptionPane.showMessageDialog(null, "[������] �޷��ظ����С�");
            }
        }
        catch (InterruptedException ex) {
            Logger.getLogger(KinMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void jTextField1ActionPerformed(final ActionEvent evt) {
    }
    
    private void jButton11ActionPerformed(final ActionEvent evt) {
        this.sendNotice(0);
    }
    
    private void jButton12ActionPerformed(final ActionEvent evt) {
        CashItemFactory.getInstance().clearCashShop();
        final String ��� = "[����ϵͳ] �̳����سɹ���";
        JOptionPane.showMessageDialog(null, "�̳����سɹ���");
        this.printChatLog(���);
    }
    
    private void jTextField2ActionPerformed(final ActionEvent evt) {
    }
    
    private void jButton13ActionPerformed(final ActionEvent evt) {
        this.sendNoticeGG();
    }
    
    private void jButton14ActionPerformed(final ActionEvent evt) {
        this.ˢ��Ʒ();
    }
    
    private void jTextField20ActionPerformed(final ActionEvent evt) {
    }
    
    private void jButton15ActionPerformed(final ActionEvent evt) {
        this.��ȫ�����();
    }
    
    private void jButton16ActionPerformed(final ActionEvent evt) {
        this.����������();
    }
    
    private void jTextField22ActionPerformed(final ActionEvent evt) {
    }
    
    private void jTextField23ActionPerformed(final ActionEvent evt) {
    }
    
    private void jButton17ActionPerformed(final ActionEvent evt) {
        this.FixAcLogged();
    }
    
    private void jTextField24ActionPerformed(final ActionEvent evt) {
    }
    
    private void jTextField25ActionPerformed(final ActionEvent evt) {
    }
    
    private void jButton18ActionPerformed(final ActionEvent evt) {
        this.ChangePassWord();
    }
    
    private void jButton19ActionPerformed(final ActionEvent evt) {
        DatabaseConnection.closeTimeout();
    }
    
    private void jTextField26ActionPerformed(final ActionEvent evt) {
    }
    
    private void checkbox1MouseClicked(final MouseEvent evt) {
        final boolean status = this.checkbox1.getState();
        if (!(ServerConstants.Super_password = status)) {
            ServerConstants.superpw = "";
        }
        else {
            ServerConstants.superpw = this.jTextField26.getText();
        }
    }
    
    private void jButton20ActionPerformed(final ActionEvent evt) {
        this.�������();
    }
    
    private void jButton21ActionPerformed(final ActionEvent evt) {
        this.ȫ�����();
    }
    
    private void jButton22ActionPerformed(final ActionEvent evt) {
        this.����ɾ��();
    }
    
    private void jButton23ActionPerformed(final ActionEvent evt) {
        this.ȫ��ɾ��();
    }
    
    private void jButton24ActionPerformed(final ActionEvent evt) {
        this.��ѯ����();
    }
    
    private void jButton25ActionPerformed(final ActionEvent evt) {
        this.ע��();
    }
    
    private void ע��() {
        final String acc = this.jTextField36.getText();
        final String password = this.jTextField37.getText();
        if (acc == null || password == null) {
            this.printChatLog("�˺Ż������쳣");
            return;
        }
        final boolean ACCexist = AutoRegister.getAccountExists(acc);
        if (ACCexist) {
            this.printChatLog("�ʺ��ѱ�ʹ��");
            return;
        }
        if (acc.length() >= 12) {
            this.printChatLog("���볤�ȹ���");
            return;
        }
        try {
            final Connection con = DatabaseConnection.getConnection();
        }
        catch (Exception ex) {
            System.out.println(ex);
            return;
        }
        Connection con = null;
        try (final PreparedStatement ps = (PreparedStatement)con.prepareStatement("INSERT INTO accounts (name, password,md5pass) VALUES (?, ?, ?)")) {
            ps.setString(1, acc);
            ps.setString(2, LoginCrypto.hexSha1(password));
            ps.setString(3, "");
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException ex2) {
            System.out.println(ex2);
            return;
        }
        this.jTextField36.setText("�˺�");
        this.jTextField37.setText("����");
        this.printChatLog("[ע�����] �˺�: " + acc + " ����: " + password);
    }
    
    private void ��ѯ����() {
        if (this.popupMenu == null) {
            this.popupMenu = new JPopupMenu();
            final JMenuItem jMenuItem = new JMenuItem("ɾ��");
            this.popupMenu.add(jMenuItem);
            this.jTable1.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(final MouseEvent e) {
                    if (e.getButton() == 3) {
                        final int focusedRowIndex = KinMS.this.jTable1.rowAtPoint(e.getPoint());
                        if (focusedRowIndex == -1) {
                            return;
                        }
                        KinMS.this.jTable1.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
                        KinMS.this.popupMenu.show(KinMS.this.jTable1, e.getX(), e.getY());
                    }
                }
            });
            jMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    ((DropDataTableModel)KinMS.this.jTable1.getModel()).removeRow(KinMS.this.jTable1.getSelectedRow());
                    KinMS.this.jPanel10.updateUI();
                }
            });
        }
        try {
            int dropperid = 0;
            int itemid = 0;
            final Connection con = DatabaseConnection.getConnection();
            java.sql.PreparedStatement ps = null;
            String sql = "SELECT * FROM drop_data where dropperid = ?";
            if (this.jTextField34.getText().equals("����ID") || this.jTextField34.getText().equals("")) {
                itemid = Integer.parseInt(this.jTextField35.getText());
                sql = "SELECT * FROM drop_data where itemid = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, itemid);
            }
            else {
                dropperid = Integer.parseInt(this.jTextField34.getText());
                if (this.jTextField35.getText().equals("")) {
                    this.jTextField35.setText("��ƷID");
                }
                if (!this.jTextField35.getText().equals("��ƷID")) {
                    itemid = Integer.parseInt(this.jTextField35.getText());
                    sql += " and itemid = ?";
                }
                ps = con.prepareStatement(sql);
                ps.setInt(1, dropperid);
                if (!this.jTextField35.getText().equals("��ƷID")) {
                    ps.setInt(2, itemid);
                }
            }
            final ResultSet rs = ps.executeQuery();
            final List<DropData> dropData = new ArrayList<DropData>();
            while (rs.next()) {
                final DropData drop = new DropData();
                drop.setId(rs.getInt("id"));
                drop.setDropperid(rs.getInt("dropperid"));
                drop.setItemid(rs.getInt("itemid"));
                drop.setMinimum_quantity(rs.getInt("minimum_quantity"));
                drop.setMaximum_quantity(rs.getInt("maximum_quantity"));
                drop.setChance(((rs.getInt("chance") >= 999999) ? 1000000 : rs.getInt("chance")) / 10000.0);
                dropData.add(drop);
            }
            final DropDataTableModel dataTableModel = new DropDataTableModel(dropData);
            this.jTable1.setModel(dataTableModel);
            if (dropData.isEmpty()) {
                this.printChatLog("��ѯ��������!");
            }
        }
        catch (NumberFormatException | SQLException ex3) {
            final Exception ex2 = null;
            final Exception ex = ex2;
            JOptionPane.showMessageDialog(null, "����!\r\n" + ex);
        }
    }
    
    private void ����ɾ��() {
        final int dropperid = Integer.parseInt(this.jTextField32.getText());
        final int itemid = Integer.parseInt(this.jTextField33.getText());
        try {
            final Connection con = DatabaseConnection.getConnection();
            final java.sql.PreparedStatement ps = con.prepareStatement("DELETE FROM drop_data where dropperid = ? and itemid = ?");
            ps.setInt(1, dropperid);
            ps.setInt(2, itemid);
            final int execute = ps.executeUpdate();
            ps.close();
            if (execute > 0) {
                this.printChatLog("ɾ�����" + dropperid + "��" + itemid + "�ɹ�!");
            }
            else {
                this.printChatLog("����ID������ƷID������,ɾ��ʧ��.");
            }
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "����!\r\n" + ex);
        }
    }
    
    private void ȫ��ɾ��() {
        final int itemid = Integer.parseInt(this.jTextField33.getText());
        try {
            final Connection con = DatabaseConnection.getConnection();
            final java.sql.PreparedStatement ps2 = con.prepareStatement("SELECT distinct dropperid FROM drop_data");
            final ResultSet rs = ps2.executeQuery();
            final java.sql.PreparedStatement ps3 = con.prepareStatement("DELETE FROM drop_data where dropperid = ? and itemid = ?");
            while (rs.next()) {
                ps3.setInt(1, rs.getInt("dropperid"));
                ps3.setInt(2, itemid);
                ps3.addBatch();
            }
            ps3.executeBatch();
            ps3.close();
            ps2.close();
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "����!\r\n" + ex);
        }
        this.printChatLog("ɾ�����л����" + itemid + "����Ʒ�ɹ�!");
    }
    
    private void �������() {
        final int dropperid = Integer.parseInt(this.jTextField27.getText());
        final int itemid = Integer.parseInt(this.jTextField28.getText());
        final int minimumQuantity = Integer.parseInt(this.jTextField29.getText());
        final int maximumQuantity = Integer.parseInt(this.jTextField30.getText());
        final double chance = Double.parseDouble(this.jTextField31.getText());
        try {
            final Connection con = DatabaseConnection.getConnection();
            final java.sql.PreparedStatement ps = con.prepareStatement("INSERT INTO drop_data(dropperid,itemid,minimum_quantity,maximum_quantity,questid,chance) VALUES(?,?,?,?,?,?)");
            ps.setInt(1, dropperid);
            ps.setInt(2, itemid);
            ps.setInt(3, minimumQuantity);
            ps.setInt(4, maximumQuantity);
            ps.setInt(5, 0);
            ps.setInt(6, (int)(chance * 10000.0));
            ps.execute();
            ps.close();
        }
        catch (NumberFormatException | SQLException ex3) {
            final Exception ex2 = null;
            final Exception ex = ex2;
            JOptionPane.showMessageDialog(null, "����!\r\n" + ex);
            JOptionPane.showMessageDialog(null, "����!\r\n" + ex3);
        }
        this.printChatLog("������ӵ���ɹ�!");
    }
    
    private void ȫ�����() {
        final int itemid = Integer.parseInt(this.jTextField28.getText());
        final int minimumQuantity = Integer.parseInt(this.jTextField29.getText());
        final int maximumQuantity = Integer.parseInt(this.jTextField30.getText());
        final double chance = Double.parseDouble(this.jTextField31.getText());
        try {
            final Connection con = DatabaseConnection.getConnection();
            final java.sql.PreparedStatement ps2 = con.prepareStatement("SELECT distinct dropperid FROM drop_data");
            final ResultSet rs = ps2.executeQuery();
            final java.sql.PreparedStatement ps3 = con.prepareStatement("INSERT INTO drop_data(dropperid,itemid,minimum_quantity,maximum_quantity,questid,chance) VALUES(?,?,?,?,?,?)");
            while (rs.next()) {
                ps3.setInt(1, rs.getInt("dropperid"));
                ps3.setInt(2, itemid);
                ps3.setInt(3, minimumQuantity);
                ps3.setInt(4, maximumQuantity);
                ps3.setInt(5, 0);
                ps3.setInt(6, (int)(chance * 10000.0));
                ps3.addBatch();
            }
            ps3.executeBatch();
            ps3.close();
            ps2.close();
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "����!\r\n" + ex);
        }
        this.printChatLog("�����й�����ӵ���ɹ�!");
    }
    
    private void ChangePassWord() {
        final String account = this.jTextField24.getText();
        final String password = this.jTextField25.getText();
        if (password.length() > 12) {
            JOptionPane.showMessageDialog(null, "�������");
            return;
        }
        if (!AutoRegister.getAccountExists(account)) {
            JOptionPane.showMessageDialog(null, "�˺Ų�����");
            return;
        }
        try {
            final Connection con = DatabaseConnection.getConnection();
            final java.sql.PreparedStatement ps = con.prepareStatement("Update accounts set password = ? Where name = ?");
            ps.setString(1, LoginCrypto.hexSha1(password));
            ps.setString(2, account);
            ps.execute();
            ps.close();
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "����!\r\n" + ex);
        }
        this.printChatLog("�����˺�: " + account + "������Ϊ " + password);
    }
    
    private void ����������() {
        try {
            final String ��� = "�رշ���������ʱ��";
            this.minutesLeft = Integer.parseInt(this.jTextField22.getText());
            if (KinMS.ts == null && (KinMS.t == null || !KinMS.t.isAlive())) {
                KinMS.t = new Thread(ShutdownServer.getInstance());
                KinMS.ts = Timer.EventTimer.getInstance().register(new Runnable() {
                    @Override
                    public void run() {
                        if (KinMS.this.minutesLeft == 0) {
                            ShutdownServer.getInstance();
                            KinMS.t.start();
                            KinMS.ts.cancel(false);
                            return;
                        }
                        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(0, "���������� " + KinMS.this.minutesLeft + "���Ӻ�ر�. �뾡��رչ�Ӷ���˰�ȫ����.").getBytes());
                        System.out.println("���������� " + KinMS.this.minutesLeft + "���Ӻ�ر�.");
                        KinMS.this.minutesLeft--;
                    }
                }, 60000L);
            }
            this.jTextField22.setText("�رշ���������ʱ��");
            this.printChatLog(���);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "����!\r\n" + e);
        }
    }
    
    private void ��ȫ�����() {
        try {
            int ����;
            if ("��������".equals(this.jTextField20.getText())) {
                ���� = 0;
            }
            else {
                ���� = Integer.parseInt(this.jTextField20.getText());
            }
            int ����;
            if ("1���/2����/3���/4����".equals(this.jTextField21.getText())) {
                ���� = 0;
            }
            else {
                ���� = Integer.parseInt(this.jTextField21.getText());
            }
            if (���� <= 0 || ���� <= 0) {
                return;
            }
            String ��� = "";
            int ret = 0;
            if (���� == 1 || ���� == 2) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        mch.modifyCSPoints(����, ����);
                        String cash = null;
                        if (���� == 1) {
                            cash = "���";
                        }
                        else if (���� == 2) {
                            cash = "���þ�";
                        }
                        mch.startMapEffect("����Ա����" + ���� + cash + "�����ߵ�������ң����л����Ա�ɣ�", 5121009);
                        ++ret;
                    }
                }
            }
            else if (���� == 3) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        mch.gainMeso(����, true);
                        mch.startMapEffect("����Ա����" + ���� + "ð�ձҸ����ߵ�������ң����л����Ա�ɣ�", 5121009);
                        ++ret;
                    }
                }
            }
            else if (���� == 4) {
                for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        mch.gainExp(����, true, false, true);
                        mch.startMapEffect("����Ա����" + ���� + "��������ߵ�������ң����л����Ա�ɣ�", 5121009);
                        ++ret;
                    }
                }
            }
            String ����A = "";
            if (���� == 1) {
                ����A = "���";
            }
            else if (���� == 2) {
                ����A = "���þ�";
            }
            else if (���� == 3) {
                ����A = "���";
            }
            else if (���� == 4) {
                ����A = "����";
            }
            ��� = "һ������[" + ���� * ret + "]." + ����A + "!һ�����Ÿ���" + ret + "�ˣ�";
            this.jTextField20.setText("��������");
            this.jTextField21.setText("1���/2����/3���/4����");
            this.printChatLog(���);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "����!\r\n" + e);
        }
    }
    
    private void ˢ��Ʒ() {
        try {
            String ����;
            if ("�������".equals(this.jTextField3.getText())) {
                ���� = "";
            }
            else {
                ���� = this.jTextField3.getText();
            }
            int ��ƷID;
            if ("��ƷID".equals(this.jTextField4.getText())) {
                ��ƷID = 0;
            }
            else {
                ��ƷID = Integer.parseInt(this.jTextField4.getText());
            }
            int ����;
            if ("����".equals(this.jTextField5.getText())) {
                ���� = 0;
            }
            else {
                ���� = Integer.parseInt(this.jTextField5.getText());
            }
            int ����;
            if ("����".equals(this.jTextField6.getText())) {
                ���� = 0;
            }
            else {
                ���� = Integer.parseInt(this.jTextField6.getText());
            }
            int ����;
            if ("����".equals(this.jTextField7.getText())) {
                ���� = 0;
            }
            else {
                ���� = Integer.parseInt(this.jTextField7.getText());
            }
            int ����;
            if ("����".equals(this.jTextField8.getText())) {
                ���� = 0;
            }
            else {
                ���� = Integer.parseInt(this.jTextField8.getText());
            }
            int ����;
            if ("����".equals(this.jTextField9.getText())) {
                ���� = 0;
            }
            else {
                ���� = Integer.parseInt(this.jTextField9.getText());
            }
            int HP;
            if ("HP����".equals(this.jTextField10.getText())) {
                HP = 0;
            }
            else {
                HP = Integer.parseInt(this.jTextField10.getText());
            }
            int MP;
            if ("MP����".equals(this.jTextField11.getText())) {
                MP = 0;
            }
            else {
                MP = Integer.parseInt(this.jTextField11.getText());
            }
            int �ɼӾ����;
            if ("�Ӿ����".equals(this.jTextField12.getText())) {
                �ɼӾ���� = 0;
            }
            else {
                �ɼӾ���� = Integer.parseInt(this.jTextField12.getText());
            }
            String ����������;
            if ("������".equals(this.jTextField13.getText())) {
                ���������� = "";
            }
            else {
                ���������� = this.jTextField13.getText();
            }
            int ����ʱ��;
            if ("������Ʒʱ��".equals(this.jTextField14.getText())) {
                ����ʱ�� = 0;
            }
            else {
                ����ʱ�� = Integer.parseInt(this.jTextField14.getText());
            }
            final String �Ƿ���Խ��� = this.jTextField15.getText();
            int ������;
            if ("������".equals(this.jTextField16.getText())) {
                ������ = 0;
            }
            else {
                ������ = Integer.parseInt(this.jTextField16.getText());
            }
            int ħ����;
            if ("ħ����".equals(this.jTextField17.getText())) {
                ħ���� = 0;
            }
            else {
                ħ���� = Integer.parseInt(this.jTextField17.getText());
            }
            int �������;
            if ("�������".equals(this.jTextField18.getText())) {
                ������� = 0;
            }
            else {
                ������� = Integer.parseInt(this.jTextField18.getText());
            }
            int ħ������;
            if ("ħ������".equals(this.jTextField19.getText())) {
                ħ������ = 0;
            }
            else {
                ħ������ = Integer.parseInt(this.jTextField19.getText());
            }
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(��ƷID);
            String ���A = "";
            final String ��� = "������֣�" + ���� + " ��ƷID��" + ��ƷID + " ������" + ���� + " ����:" + ���� + " ����:" + ���� + " ����:" + ���� + " ����:" + ���� + " HP:" + HP + " MP:" + MP + " �ɼӾ����:" + �ɼӾ���� + " ����������:" + ���������� + " ����ʱ��:" + ����ʱ�� + " �Ƿ���Խ���:" + �Ƿ���Խ��� + " ������:" + ������ + " ħ����:" + ħ���� + " �������:" + ������� + " ħ������:" + ħ������ + "\r\n";
            for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    if (mch.getName().equals(����)) {
                        if (���� >= 0) {
                            if (!MapleInventoryManipulator.checkSpace(mch.getClient(), ��ƷID, ����, "")) {
                                return;
                            }
                            if ((type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(��ƷID) && !GameConstants.isBullet(��ƷID)) || (type.equals(MapleInventoryType.CASH) && ��ƷID >= 5000000 && ��ƷID <= 5000100)) {
                                final Equip item = (Equip)ii.getEquipById(��ƷID);
                                if (ii.isCash(��ƷID)) {
                                    item.setUniqueId(1);
                                }
                                if (���� > 0 && ���� <= 32767) {
                                    item.setStr((short)����);
                                }
                                if (���� > 0 && ���� <= 32767) {
                                    item.setDex((short)����);
                                }
                                if (���� > 0 && ���� <= 32767) {
                                    item.setInt((short)����);
                                }
                                if (���� > 0 && ���� <= 32767) {
                                    item.setLuk((short)����);
                                }
                                if (������ > 0 && ������ <= 32767) {
                                    item.setWatk((short)������);
                                }
                                if (ħ���� > 0 && ħ���� <= 32767) {
                                    item.setMatk((short)ħ����);
                                }
                                if (������� > 0 && ������� <= 32767) {
                                    item.setWdef((short)�������);
                                }
                                if (ħ������ > 0 && ħ������ <= 32767) {
                                    item.setMdef((short)ħ������);
                                }
                                if (HP > 0 && HP <= 30000) {
                                    item.setHp((short)HP);
                                }
                                if (MP > 0 && MP <= 30000) {
                                    item.setMp((short)MP);
                                }
                                if ("���Խ���".equals(�Ƿ���Խ���)) {
                                    byte flag = item.getFlag();
                                    if (item.getType() == MapleInventoryType.EQUIP.getType()) {
                                        flag |= (byte)ItemFlag.KARMA_EQ.getValue();
                                    }
                                    else {
                                        flag |= (byte)ItemFlag.KARMA_USE.getValue();
                                    }
                                    item.setFlag(flag);
                                }
                                if (����ʱ�� > 0) {
                                    item.setExpiration(System.currentTimeMillis() + ����ʱ�� * 24 * 60 * 60 * 1000);
                                }
                                if (�ɼӾ���� > 0) {
                                    item.setUpgradeSlots((byte)�ɼӾ����);
                                }
                                if (���������� != null) {
                                    item.setOwner(����������);
                                }
                                final String name = ii.getName(��ƷID);
                                if (��ƷID / 10000 == 114 && name != null && name.length() > 0) {
                                    final String msg = "���ѻ�óƺ� <" + name + ">";
                                    mch.getClient().getPlayer().dropMessage(5, msg);
                                    mch.getClient().getPlayer().dropMessage(5, msg);
                                }
                                MapleInventoryManipulator.addbyItem(mch.getClient(), item.copy());
                            }
                            else {
                                MapleInventoryManipulator.addById(mch.getClient(), ��ƷID, (short)����, "", null, ����ʱ��, (byte)0);
                            }
                        }
                        else {
                            MapleInventoryManipulator.removeById(mch.getClient(), GameConstants.getInventoryType(��ƷID), ��ƷID, -����, true, false);
                        }
                        mch.getClient().getSession().write((Object)MaplePacketCreator.getShowItemGain(��ƷID, (short)����, true));
                        ���A = "[ˢ��Ʒ]:" + ���;
                    }
                }
            }
            this.jTextField3.setText("�������");
            this.jTextField4.setText("��ƷID");
            this.jTextField5.setText("����");
            this.jTextField6.setText("����");
            this.jTextField7.setText("����");
            this.jTextField8.setText("����");
            this.jTextField9.setText("����");
            this.jTextField10.setText("HP����");
            this.jTextField11.setText("MP����");
            this.jTextField12.setText("�Ӿ����");
            this.jTextField13.setText("������");
            this.jTextField14.setText("������Ʒʱ��");
            this.jTextField15.setText("���Խ���");
            this.jTextField16.setText("������");
            this.jTextField17.setText("ħ����");
            this.jTextField18.setText("�������");
            this.jTextField19.setText("ħ������");
            this.printChatLog(���A);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "����!\r\n" + e);
        }
    }
    
    private void printChatLog(final String str) {
        this.chatLog.setText(this.chatLog.getText() + str + "\r\n");
    }
    
    private void sendNoticeGG() {
        try {
            final String str = this.jTextField2.getText();
            String ��� = "";
            for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    mch.startMapEffect(str, 5121009);
                    ��� = "[����]:" + str;
                }
            }
            this.jTextField2.setText("");
            this.printChatLog(���);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "����!\r\n" + e);
        }
    }
    
    private void FixAcLogged() {
        try {
            final com.mysql.jdbc.Connection dcon = (com.mysql.jdbc.Connection)DatabaseConnection.getConnection();
            try (final PreparedStatement ps = (PreparedStatement)dcon.prepareStatement("UPDATE accounts SET loggedin = 0 WHERE name = " + this.jTextField23.getText())) {
                ps.executeUpdate();
            }
            this.printChatLog("������˺�" + this.jTextField23.getText());
            this.jTextField23.setText("");
        }
        catch (SQLException ex) {}
    }
    
    private void sendNotice(final int type) {
        try {
            final String str = this.jTextField1.getText();
            final byte[] p = null;
            String ��� = "";
            if (type == 0) {
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        try {
                            ChannelServer.forceRemovePlayerByCharName(str);
                            if (chr.getName().equals(str) && chr.getMapId() != 0) {
                                chr.getClient().getSession().close();
                                chr.getClient().disconnect(true, false);
                                ��� = "[�⿨ϵͳ] �ɹ��Ͽ�" + str + "��ң�";
                            }
                            else {
                                ��� = "[�⿨ϵͳ] ����������������߸����û�����ߣ�";
                            }
                        }
                        catch (Exception ex) {}
                    }
                }
            }
            this.jTextField1.setText("");
            this.printChatLog(���);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "����!\r\n" + e);
        }
    }
    
    public static void main(final String[] args) {
        try {
            for (final UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(KinMS.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex2) {
            Logger.getLogger(KinMS.class.getName()).log(Level.SEVERE, null, ex2);
        }
        catch (IllegalAccessException ex3) {
            Logger.getLogger(KinMS.class.getName()).log(Level.SEVERE, null, ex3);
        }
        catch (UnsupportedLookAndFeelException ex4) {
            Logger.getLogger(KinMS.class.getName()).log(Level.SEVERE, null, ex4);
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new KinMS().setVisible(true);
            }
        });
    }
    
    static {
        KinMS.instance = new KinMS();
        KinMS.ts = null;
        KinMS.t = null;
    }
}
