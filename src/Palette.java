import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;

public class Palette
{
  private Hashtable<Hue, Integer> palette;

  public Palette(BufferedImage image)
  {
    palette = colors(image);
  }

  private Hashtable<Hue, Integer> colors(BufferedImage image)
  {
    Hashtable<Hue, Integer> colors = new Hashtable<Hue, Integer>();
    int width = image.getWidth();
    int height = image.getHeight();
    for (int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
      {
        Hue pixel = new Hue(image.getRGB(x, y));
        Integer n = colors.get(pixel);
        if (n == null)
          colors.put(pixel, 1);
        else
          colors.replace(pixel, n + 1);
      }
    return colors;
  }

  public ArrayList<Hue> getMoreFrequent(int nb)
  {
    ArrayList<Map.Entry<Hue, Integer>> l =
      new ArrayList<Map.Entry<Hue, Integer>>(palette.entrySet());
    if (l.isEmpty())
      return null;
    Collections.sort(l, (kv1, kv2) -> (kv2.getValue() - kv1.getValue()));
    ArrayList<Hue> hues = new ArrayList<Hue>(l.size());
    for (Map.Entry<Hue, Integer> entry : l)
      hues.add(entry.getKey());
    return new ArrayList<Hue>(hues.subList(0, min(hues.size(), nb)));
  }

  private int min(int a, int b)
  {
    return a < b ? a : b;
  }
}
