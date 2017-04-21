/*     */ package co.kepler.wallofdoodles;
/*     */ 
/*     */ import co.kepler.wallofdoodles.packets.PacketRequestStrings;
/*     */ import co.kepler.wallofdoodles.packets.PacketStringResponse;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.event.DocumentListener;
/*     */ import javax.swing.text.Document;
/*     */ 
/*     */ public class InputDialog
/*     */ {
/*  22 */   public static final Color ERR_COLOR = new Color(16751001);
/*     */   
/*     */   public static final int STR_ALPHABETIC = 0;
/*     */   public static final int STR_NUMERIC = 1;
/*     */   public static final int STR_NUMERIC_INT = 2;
/*     */   public static final int STR_ALPHANUMERIC = 3;
/*     */   public static final int STR_ALPHANUMERIC_SPACES = 4;
/*     */   public static final int STR_ALPHANUMERIC_PUNCT = 5;
/*     */   public static final int STR_ALL = 6;
/*     */   
/*     */   public static PacketStringResponse showDialog(Component c, PacketRequestStrings p)
/*     */   {
/*  34 */     return showDialog(c, p.getTitle(), p.getInput(), p.getDefaults(), 
/*  35 */       p.getMinLength(), p.getMaxLength(), p.getStrType());
/*     */   }
/*     */   
/*     */   public static String showDialog(Component c, String title, String input, String def, int minLen, int maxLen, int strType) {
/*  39 */     PacketStringResponse r = showDialog(c, title, new String[] { input }, new String[] { def }, 
/*  40 */       new int[] { minLen }, new int[] { maxLen }, new int[] { strType });
/*  41 */     if (r.hitOkay()) return r.getStrings()[0];
/*  42 */     return null;
/*     */   }
/*     */   
/*     */   public static PacketStringResponse showDialog(Component c, String title, String[] input, String[] defaults, int[] minLength, int[] maxLength, int[] strType)
/*     */   {
/*  47 */     DocListener dl = new DocListener(null);
/*  48 */     LblText[] inputs = new LblText[input.length];
/*  49 */     JPanel panel = new JPanel() {
/*     */       private static final long serialVersionUID = -5929203616554270629L;
/*     */       
/*     */       public void addNotify() {
/*  53 */         super.addNotify();
/*  54 */         JOptionPane pane = (JOptionPane)getParent().getParent().getParent().getParent();
/*  55 */         InputDialog.this.setButton((JButton)((JPanel)pane.getComponent(1)).getComponent(0));
/*     */       }
/*  57 */     };
/*  58 */     BoxLayout bl = new BoxLayout(panel, 1);
/*  59 */     panel.setLayout(bl);
/*     */     
/*     */ 
/*  62 */     for (int i = 0; i < inputs.length; i++) {
/*  63 */       LblText cur = new LblText(input[i], defaults[i], minLength[i], maxLength[i], strType[i]);
/*  64 */       inputs[i] = cur;
/*  65 */       cur.getTextField().getDocument().addDocumentListener(dl);
/*  66 */       panel.add(cur);
/*  67 */       if (i != inputs.length - 1) panel.add(javax.swing.Box.createVerticalStrut(8));
/*     */     }
/*  69 */     dl.setTexts(inputs);
/*     */     
/*  71 */     int button = JOptionPane.showConfirmDialog(c, panel, title, 
/*  72 */       2, -1);
/*     */     
/*  74 */     String[] result = new String[inputs.length];
/*  75 */     for (int i = 0; i < inputs.length; i++)
/*  76 */       result[i] = inputs[i].getText();
/*  77 */     if ((c != null) && ((c instanceof JFrame))) ((JFrame)c).toFront();
/*  78 */     return new PacketStringResponse(button == 0, result);
/*     */   }
/*     */   
/*     */   private static class DocListener implements DocumentListener {
/*     */     private InputDialog.LblText[] texts;
/*     */     private JButton button;
/*     */     
/*  85 */     public void setTexts(InputDialog.LblText[] texts) { this.texts = texts; }
/*     */     
/*     */     public void setButton(JButton button) {
/*  88 */       this.button = button;
/*  89 */       update(); }
/*     */     
/*  91 */     public void changedUpdate(DocumentEvent e) { update(); }
/*  92 */     public void insertUpdate(DocumentEvent e) { update(); }
/*  93 */     public void removeUpdate(DocumentEvent e) { update(); }
/*     */     
/*  95 */     public void update() { boolean btnEnabled = true;
/*  96 */       for (int i = 0; (i < this.texts.length) && (btnEnabled); i++)
/*  97 */         btnEnabled = this.texts[i].isValidText();
/*  98 */       this.button.setEnabled(btnEnabled);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class LblText extends JPanel { private static final long serialVersionUID = -672602420565227761L;
/*     */     private JTextField text;
/*     */     private JLabel label;
/*     */     int min;
/*     */     int max;
/*     */     int strType;
/*     */     
/* 109 */     public LblText(String lbl, String def, int minLen, int maxLen, int strType) { this.label = new JLabel(lbl);
/* 110 */       this.text = new JTextField(def);
/* 111 */       this.min = minLen;
/* 112 */       this.max = maxLen;
/* 113 */       this.strType = strType;
/*     */       
/* 115 */       setLayout(new BorderLayout());
/* 116 */       add(this.label, "North");
/* 117 */       add(this.text, "South");
/*     */       
/* 119 */       this.text.getDocument().addDocumentListener(new DocumentListener()
/*     */       {
/* 121 */         public void changedUpdate(DocumentEvent e) { InputDialog.LblText.this.updateColor(); }
/*     */         
/* 123 */         public void insertUpdate(DocumentEvent e) { InputDialog.LblText.this.updateColor(); }
/*     */         
/* 125 */         public void removeUpdate(DocumentEvent e) { InputDialog.LblText.this.updateColor();
/*     */         }
/* 127 */       });
/* 128 */       updateColor();
/*     */     }
/*     */     
/*     */     public JTextField getTextField() {
/* 132 */       return this.text;
/*     */     }
/*     */     
/* 135 */     public String getText() { return this.text.getText(); }
/*     */     
/*     */     public void updateColor()
/*     */     {
/* 139 */       this.text.setBackground(isValidText() ? Color.WHITE : InputDialog.ERR_COLOR);
/*     */     }
/*     */     
/*     */     public boolean isValidText() {
/* 143 */       if (!fitsStrType()) return false;
/* 144 */       if (this.strType == 1) {
/* 145 */         double d = Double.parseDouble(this.text.getText());
/* 146 */         return (this.min <= d) || (d <= this.max); }
/* 147 */       if (this.strType == 2) {
/* 148 */         int i = Integer.parseInt(this.text.getText());
/* 149 */         return (this.min <= i) || (i <= this.max);
/*     */       }
/* 151 */       return ((this.min < 0) || (this.text.getText().length() >= this.min)) && (
/* 152 */         (this.max < 0) || (this.text.getText().length() <= this.max));
/*     */     }
/*     */     
/*     */     private boolean fitsStrType() {
/* 156 */       if (this.strType == 6) return true;
/* 157 */       if (this.strType == 1) {
/*     */         try {
/* 159 */           Double.parseDouble(this.text.getText());
/*     */         } catch (NumberFormatException localNumberFormatException) {}
/* 161 */         return false;
/*     */       }
/* 163 */       if (this.strType == 2) {
/*     */         try {
/* 165 */           Integer.parseInt(this.text.getText());
/*     */         } catch (NumberFormatException localNumberFormatException1) {}
/* 167 */         return false;
/*     */       }
/*     */       char[] arrayOfChar;
/* 170 */       int j = (arrayOfChar = this.text.getText().toCharArray()).length; for (int i = 0; i < j; i++) { char c = arrayOfChar[i];
/* 171 */         switch (this.strType) {
/*     */         case 0: 
/* 173 */           if (!Character.isAlphabetic(c))
/* 174 */             return false;
/*     */           break;
/*     */         case 5: 
/* 177 */           if ((c < ' ') || (c > '~')) return false;
/*     */         case 4: 
/* 179 */           if (c == ' ') {}
/*     */           break;
/* 181 */         case 3:  if (((c < '0') || (c > '9')) && (!Character.isAlphabetic(c)))
/* 182 */             return false;
/*     */           break;
/*     */         }
/*     */       }
/* 186 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Ben\Downloads\Wall of Doodles.jar!\co\kepler\wallofdoodles\InputDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */