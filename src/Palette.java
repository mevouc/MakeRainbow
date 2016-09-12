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
  private BufferedImage img;

  public Palette(BufferedImage img)
  {
    this.img = img;
    palette = colors(img);
  }

  private Hashtable<Hue, Integer> colors(BufferedImage img)
  {
    Hashtable<Hue, Integer> colors = new Hashtable<Hue, Integer>();
    int width = img.getWidth();
    int height = img.getHeight();
    for (int x = 0; x < width; x++)
      for(int y = 0; y < height; y++)
      {
        Hue pixel = new Hue(img.getRGB(x, y)).approximate();
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

  public BufferedImage draw(ArrayList<Hue> hues, int nbX, int nbY)
  {
    int width = img.getWidth();
    int height = img.getHeight();
    BufferedImage drawing = new BufferedImage(width, height,
                                              BufferedImage.TYPE_INT_RGB);
    int gapX = width / nbX;
    int gapY = height / nbY;
    for (int x = 0; x < nbX * gapX; x += gapX)
      for (int y = 0; y < nbY * gapY; y += gapY)
      {
        int sumR = 0;
        int sumG = 0;
        int sumB = 0;
        for (int i = x; i < x + gapX; i++)
          for (int j = y; j < y + gapY; j++)
          {
            Color pixel = new Color(img.getRGB(i, j));
            sumR += pixel.getRed();
            sumG += pixel.getGreen();
            sumB += pixel.getBlue();
          }
        int nb = gapX * gapY;
        Hue aera = new Hue(sumR / nb, sumG / nb, sumB / nb);
        Hue newHue = aera.closestIn(hues);
        for (int i = x; i < x + gapX; i++)
          for (int j = y; j < y + gapY; j++)
            drawing.setRGB(i, j, newHue.getRGB());
      }
    return drawing;
  }
}
