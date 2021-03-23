package imageprocessing;
import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
public class imageprocessing {
Frame f;
public static void main(String[] args) {
imageprocessing im=new imageprocessing();
}
imageprocessing()
{
f=new Frame();
f.setSize(2048,1536);
f.setVisible(true);
f.addWindowListener(new WindowAdapter() {
@Override
public void windowClosing(WindowEvent e) {
System.exit(0);
}
});
mycanvas c=new mycanvas();
c.setSize(2048,1536);
f.add(c);
}
}
class mycanvas extends Canvas
{
int [][][] srcArr;
int [][][] destArr;
@Override
public void paint(Graphics g) {
BufferedImage in;
try {
in = ImageIO.read(new FileInputStream("/Users/chi/Desktop/IMG/website/IMG_7571.JPG"));
g.drawImage(in, 0, 0, this);
int w=in.getWidth();
int h= in.getHeight();
srcArr=new int[w][h][3];
destArr=new int[w][h][3];
int rgb;
//---- read data ----
BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
int x,y;
for(y=0;y<h;y++)
for(x=0;x<w;x++)
{
rgb=in.getRGB(x, y);
srcArr[x][y][0]=(rgb>>16) & 0xff;
srcArr[x][y][1]=(rgb>>8) & 0xff;
srcArr[x][y][2]=(rgb) & 0xff;
}
//----- image processing ----
for(y=1;y<h-1;y++)
for(x=1;x<w-1;x++)
{
int [][]M= {{-2,1,1},{1,1,1},{-2,1,1}};
for(int k=0;k<3;k++)
{
destArr[x][y][k]=0;
for(int i=-1;i<=1;i++)
for(int j=-1;j<=1;j++)
destArr[x][y][k]+=(srcArr[x+i][y+j][k]*M[1+i][1+j]);
if(destArr[x][y][k]>255) destArr[x][y][k]=255;
if(destArr[x][y][k]<=0) destArr[x][y][k]=0;
}
}
//---- output data ----
for(y=0;y<h;y++)
for(x=0;x<w;x++)
{
rgb=(destArr[x][y][0]<<16) | (destArr[x][y][1]<<8) | destArr[x][y][2];
image.setRGB(x, y,rgb);
}
g.drawImage(image,500, 0, this);
} catch (IOException e) {
e.printStackTrace();
}
}
}