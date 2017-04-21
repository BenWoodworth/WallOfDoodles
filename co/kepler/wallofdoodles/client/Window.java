/*     */ package co.kepler.wallofdoodles.client;
/*     */ 
/*     */ import co.kepler.wallofdoodles.User;
/*     */ import co.kepler.wallofdoodles.Wall;
/*     */ import co.kepler.wallofdoodles.packets.WallAction;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.DefaultListModel;
/*     */ import javax.swing.InputMap;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JColorChooser;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollBar;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JToggleButton;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ 
/*     */ public class Window extends JFrame
/*     */ {
/*     */   private static final long serialVersionUID = -3410993633048867764L;
/*     */   private JPanel contentPane;
/*     */   private JScrollPane scrollPane;
/*     */   private WallComponent wall;
/*     */   private JPanel sidePanel;
/*     */   private JPanel settingsPanel;
/*     */   private JToggleButton toggleText;
/*     */   private JToggleButton toggleDraw;
/*     */   private JToggleButton toggleLine;
/*     */   private JButton buttonColor;
/*     */   private JScrollPane scrollPaneOnline;
/*     */   private JList<User> listOnline;
/*     */   private DefaultListModel<User> listOnlineModel;
/*     */   
/*     */   public Window()
/*     */   {
/*  50 */     setEnabled(false);
/*  51 */     ((InputMap)UIManager.getDefaults().get("ScrollPane.ancestorInputMap")).clear();
/*     */     
/*  53 */     setDefaultCloseOperation(3);
/*  54 */     setBounds(100, 100, 611, 541);
/*  55 */     this.contentPane = new JPanel();
/*  56 */     this.contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
/*  57 */     this.contentPane.setLayout(new BorderLayout(0, 0));
/*  58 */     setContentPane(this.contentPane);
/*     */     
/*  60 */     this.scrollPane = new JScrollPane();
/*  61 */     this.scrollPane.setViewportBorder(null);
/*  62 */     this.scrollPane.getVerticalScrollBar().setUnitIncrement(0);
/*  63 */     this.contentPane.add(this.scrollPane, "Center");
/*     */     
/*  65 */     this.sidePanel = new JPanel();
/*  66 */     this.sidePanel.setLayout(new BorderLayout());
/*  67 */     this.contentPane.add(this.sidePanel, "East");
/*     */     
/*  69 */     this.settingsPanel = new JPanel();
/*  70 */     this.settingsPanel.setLayout(new BoxLayout(this.settingsPanel, 1));
/*  71 */     this.sidePanel.add(this.settingsPanel, "North");
/*     */     
/*  73 */     this.settingsPanel.add(new JLabel(" Doodle Mode"));
/*     */     
/*  75 */     this.toggleText = new JToggleButton("Text");
/*  76 */     this.toggleText.setSelected(true);
/*  77 */     this.toggleText.setFocusable(false);
/*  78 */     this.settingsPanel.add(this.toggleText);
/*     */     
/*  80 */     this.toggleDraw = new JToggleButton("Draw");
/*  81 */     this.toggleDraw.setFocusable(false);
/*  82 */     this.settingsPanel.add(this.toggleDraw);
/*     */     
/*  84 */     this.toggleLine = new JToggleButton("Line");
/*  85 */     this.toggleLine.setFocusable(false);
/*  86 */     this.settingsPanel.add(this.toggleLine);
/*     */     
/*  88 */     Component verticalStrut = Box.createVerticalStrut(4);
/*  89 */     this.settingsPanel.add(verticalStrut);
/*  90 */     this.settingsPanel.add(new JLabel(" Settings"));
/*     */     
/*  92 */     ActionListener toggleListener = new ToggleListener(new JToggleButton[] { this.toggleText, this.toggleDraw, this.toggleLine });
/*  93 */     this.toggleText.addActionListener(toggleListener);
/*  94 */     this.toggleDraw.addActionListener(toggleListener);
/*  95 */     this.toggleLine.addActionListener(toggleListener);
/*     */     
/*  97 */     this.buttonColor = new JButton("Select Color...");
/*  98 */     this.buttonColor.setFocusable(false);
/*  99 */     this.buttonColor.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 101 */         Color c = JColorChooser.showDialog(null, 
/* 102 */           "Select a Color", new Color(Window.this.wall.getCurColor()));
/* 103 */         if (c != null) Window.this.wall.setCurColor(c.getRGB());
/*     */       }
/* 105 */     });
/* 106 */     this.settingsPanel.add(this.buttonColor);
/*     */     
/* 108 */     Dimension buttonSize = new Dimension(130, 30);
/* 109 */     Component[] arrayOfComponent; int j = (arrayOfComponent = new Component[] { this.toggleText, this.toggleDraw, this.toggleLine, this.buttonColor }).length; for (int i = 0; i < j; i++) { Component c = arrayOfComponent[i];
/* 110 */       c.setMaximumSize(buttonSize);
/* 111 */       c.setMinimumSize(buttonSize);
/* 112 */       c.setPreferredSize(buttonSize);
/*     */     }
/*     */     
/* 115 */     this.settingsPanel.add(Box.createVerticalStrut(4));
/* 116 */     this.settingsPanel.add(new JLabel(" Users Online"));
/*     */     
/* 118 */     this.scrollPaneOnline = new JScrollPane();
/* 119 */     this.scrollPaneOnline.setSize((int)buttonSize.getWidth(), 0);
/* 120 */     this.scrollPaneOnline.setPreferredSize(this.scrollPaneOnline.getSize());
/* 121 */     this.sidePanel.add(this.scrollPaneOnline, "Center");
/*     */     
/* 123 */     this.listOnline = new JList();
/* 124 */     this.listOnline.addListSelectionListener(new ListSelectionListener() {
/*     */       public void valueChanged(ListSelectionEvent arg0) {
/* 126 */         Window.this.listOnline.clearSelection();
/*     */       }
/* 128 */     });
/* 129 */     this.listOnline.setFocusable(false);
/* 130 */     this.listOnlineModel = new DefaultListModel();
/* 131 */     this.listOnline.setModel(this.listOnlineModel);
/* 132 */     this.scrollPaneOnline.setViewportView(this.listOnline);
/*     */     
/* 134 */     setLocationRelativeTo(null);
/*     */   }
/*     */   
/*     */   public void setRoom(Wall w, User[] users, String roomName) {
/* 138 */     setTitle("Room: " + roomName);
/* 139 */     this.wall = new WallComponent(w);
/* 140 */     this.scrollPane.setViewportView(this.wall);
/* 141 */     this.listOnlineModel.clear();
/* 142 */     User[] arrayOfUser; int j = (arrayOfUser = users).length; for (int i = 0; i < j; i++) { User u = arrayOfUser[i];this.listOnlineModel.addElement(u); }
/* 143 */     setEnabled(true);
/* 144 */     this.wall.requestFocus();
/*     */   }
/*     */   
/*     */   public void addUser(User u) {
/* 148 */     this.listOnlineModel.addElement(u);
/*     */   }
/*     */   
/*     */   public void removeUser(User u) {
/* 152 */     this.listOnlineModel.removeElement(u);
/*     */   }
/*     */   
/*     */   public void sendWallAction(WallAction a, Graphics2D g) {
/* 156 */     if (this.wall != null) {
/* 157 */       this.wall.getWall().drawAction(a, g);
/* 158 */       this.wall.repaint();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setTitle(String title) {
/* 163 */     String t = "Wall of Doodles";
/* 164 */     if ((title != null) && (!title.equals(""))) {
/* 165 */       t = t + " - " + title;
/*     */     }
/* 167 */     super.setTitle(t);
/*     */   }
/*     */   
/*     */   private class ToggleListener implements ActionListener { JToggleButton[] buttons;
/*     */     
/* 172 */     public ToggleListener(JToggleButton... buttons) { this.buttons = buttons; }
/*     */     
/* 174 */     public void actionPerformed(ActionEvent e) { JToggleButton[] arrayOfJToggleButton; int j = (arrayOfJToggleButton = this.buttons).length; for (int i = 0; i < j; i++) { JToggleButton b = arrayOfJToggleButton[i];b.setSelected(b == e.getSource()); }
/* 175 */       if (e.getSource() == Window.this.toggleText) Window.this.wall.setDrawMode(0);
/* 176 */       if (e.getSource() == Window.this.toggleDraw) Window.this.wall.setDrawMode(1);
/* 177 */       if (e.getSource() == Window.this.toggleLine) Window.this.wall.setDrawMode(2);
/*     */     }
/*     */   }
/*     */   
/*     */   public WallComponent getWallComponent() {
/* 182 */     return this.wall;
/*     */   }
/*     */ }


/* Location:              C:\Users\Ben\Downloads\Wall of Doodles.jar!\co\kepler\wallofdoodles\client\Window.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */