import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class Hue extends Color
{
  public Hue(int rgb)
  {
    super(rgb);
  }

  public Hue(int r, int g, int b)
  {
    super(r, g, b);
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

  public Hue approximate()
  {
    return new Hue(approximate(getRed()), approximate(getGreen()),
                   approximate(getBlue()));
  }

  public int approximate(int chan)
  {
    chan = (chan + 8) / 17 * 17;
    chan = chan < 0 ? 0 : chan;
    chan = chan > 255 ? 255 : chan;
    return chan;
  }

  static public ArrayList<Hue> sortByBrightness(ArrayList<Hue> list)
  {
    Collections.sort(list, (h1, h2) -> h2.getBrightness() - h1.getBrightness());
    return list;
  }

  // TODO use L*a*b format
  public double distanceTo(Color that)
  {
    int diffR = that.getRed() - this.getRed();
    int diffG = that.getGreen() - this.getGreen();
    int diffB = that.getBlue() - this.getBlue();
    return Math.sqrt(diffR * diffR + diffG * diffG + diffB * diffB);
  }

  public Hue closestIn(ArrayList<Hue> list)
  {
    int closest = -1;
    double minDiff = Double.MAX_VALUE;
    for (int i = 0; i < list.size(); i++)
    {
      double currentDiff = distanceTo(list.get(i));
      if (currentDiff < minDiff)
      {
        minDiff = currentDiff;
        closest = i;
      }
    }
    return list.get(closest);
  }
}
