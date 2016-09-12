import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class Hue extends Color
{
  public Hue(int rgb)
  {
    super(rgb);
  }

  public String toString()
  {
    String rgb = Integer.toHexString(super.getRGB());
    return '#' + rgb.substring(2, rgb.length());
  }

  public int getBrightness()
  {
    return (getRed() + getBlue() + getGreen()) / 3;
  }

  static public ArrayList<Hue> sortByBrightness(ArrayList<Hue> list)
  {
    Collections.sort(list, (h1, h2) -> h2.getBrightness() - h1.getBrightness());
    return list;
  }
}
