import javax.imageio.ImageIO;
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
      int nb = 33;
      ArrayList<Hue> list = palette.getMoreFrequent(nb);
      System.out.println(list);
      Hue.sortByBrightness(list);
      int third = nb / 3;
      System.out.println("light:\n" + list.subList(0, third));
      System.out.println("medium:\n" + list.subList(third, third * 2));
      System.out.println("dark:\n" + list.subList(third * 2, list.size()));
    }
    catch (Exception e)
    {

    }
  }
}
