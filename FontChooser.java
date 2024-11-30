import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
class FontChooser extends JDialog
{
private final String FONT_DATA="font.dat";
private JComboBox<String> fontFamilyBox;
private JComboBox<String> fontStyleBox;
private JSpinner fontSizeSpinner;
private JLabel previewLabel;
private Font selectedFont;
private String fontName;
private int fontStyle;
private int fontSize;
FontChooser(JFrame parent)
{
super(parent,"Font Chooser",true);
setFont();
setLayout(new BorderLayout());
fontFamilyBox=new JComboBox<>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
fontStyleBox=new JComboBox<>(new String[]{"Plain","Bold","Italic"});
fontSizeSpinner=new JSpinner(new SpinnerNumberModel(12,1,100,1));
fontFamilyBox.setSelectedItem(this.fontName);
fontStyleBox.setSelectedIndex(this.fontStyle);
fontSizeSpinner.setValue(this.fontSize);
JPanel topPanel=new JPanel(new GridLayout(3,2,5,5));
topPanel.add(new JLabel(" Font Family : "));
topPanel.add(fontFamilyBox);
topPanel.add(new JLabel(" Font Style : "));
topPanel.add(fontStyleBox);
topPanel.add(new JLabel(" Font Size : "));
topPanel.add(fontSizeSpinner);
previewLabel=new JLabel("Sample text");
previewLabel.setHorizontalAlignment(SwingConstants.CENTER);
previewLabel.setBorder(BorderFactory.createTitledBorder("Preview"));
add(previewLabel,BorderLayout.CENTER);

JButton okButton=new JButton("Ok");
JButton cancelButton=new JButton("Cancel");
JPanel buttonPanel=new JPanel();
buttonPanel.add(okButton);
buttonPanel.add(cancelButton);
add(topPanel,BorderLayout.NORTH);
add(buttonPanel,BorderLayout.SOUTH);

ActionListener updatePreview=e->updatePreview();


fontFamilyBox.addActionListener(updatePreview);
fontStyleBox.addActionListener(updatePreview);
fontSizeSpinner.addChangeListener(ev->updatePreview());

okButton.addActionListener(ev->{
selectedFont=previewLabel.getFont();
saveFontInFile();
dispose();
});
cancelButton.addActionListener(ev->{
dispose();
});
setSize(400,300);
setLocationRelativeTo(parent);
updatePreview();
}
private void updatePreview()
{
String fontFamily=(String)fontFamilyBox.getSelectedItem();
int fontStyle=fontStyleBox.getSelectedIndex();
int fontSize=(Integer) fontSizeSpinner.getValue();
Font font=new Font(fontFamily,fontStyle,fontSize);
this.fontName=fontFamily;
this.fontSize=fontSize;
this.fontStyle=fontStyle;
previewLabel.setFont(font);
}
public Font getSelectedFont()
{
return selectedFont;
}
public void setFont()
{
File file=new File(FONT_DATA);
if(!file.exists())
{
this.fontName="Arial";
this.fontStyle=0;
this.fontSize=40;
}
else
{
try
{
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
this.fontName=randomAccessFile.readLine();
this.fontStyle=Integer.parseInt(randomAccessFile.readLine());
this.fontSize=Integer.parseInt(randomAccessFile.readLine());
randomAccessFile.close();
}catch(IOException ioException)
{}
}
this.selectedFont=new Font(fontName,fontStyle,fontSize);
}
private void saveFontInFile()
{
System.out.println("Saving into file");
try
{
File file=new File(FONT_DATA);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
randomAccessFile.setLength(0);
randomAccessFile.writeBytes(this.fontName+"\n");
randomAccessFile.writeBytes(String.valueOf(this.fontStyle)+"\n");
randomAccessFile.writeBytes(String.valueOf(this.fontSize)+"\n");
randomAccessFile.close();
}catch(IOException ioException)
{}
}
}
