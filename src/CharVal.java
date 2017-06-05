import com.intellij.util.ui.UIUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;


public class CharVal {
    public HashMap <Character, Float> chV;
    private int size = 16;
    private int as;

    public CharVal()
    {
        chV = new HashMap<Character, Float>(256);
    }

    public float getCharVal(char ch)
    {
        if (chV.containsKey(ch)==false) {
            BufferedImage image = UIUtil.createImage(size, size, BufferedImage.TYPE_INT_ARGB);
            Graphics graph = image.getGraphics();
            int[] d = new int[1024];
            as = 0;
            graph.drawString(Character.toString(ch), 0, size);
            image.getData().getPixels(0, 0, size, size, d);
            for (int i = 0; i < d.length; i+=4) {
                as += d[i] & 0xff;
            }
            chV.put(ch, Math.min(as / 9000.0f, 1.0f));
        }

        return chV.get(ch);
    }
}
