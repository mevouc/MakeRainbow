import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class MakeRainbow
{
  static public void main(String[] args)
  {
    try
    {
      if (args.length != 1)
        return;
      Palette palette = new Palette(ImageIO.read(new File(args[0])));
      int nb = 16;
      ArrayList<Hue> list = palette.getMoreFrequent(nb);
      System.out.println(list);
      Hue.sortByBrightness(list);
      nb = list.size() < nb ? list.size() : nb;
      int third = nb / 3;
      System.out.println("light:\n" + list.subList(0, third));
      System.out.println("medium:\n" + list.subList(third, nb - third));
      System.out.println("dark:\n" + list.subList(nb - third, nb));
      BufferedImage drawing = palette.draw(list, 512, 512);
      ImageIO.write(drawing, "png", new File("palette.png"));
    }
    catch (Exception e)
    {
      System.err.println(e);
      e.printStackTrace();
    }
  }
}
