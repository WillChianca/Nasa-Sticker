import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class StickersMakers {
    
    public void maker(InputStream input_stream, String arquive_name) throws Exception
    {
        // read the image
        BufferedImage old_image = ImageIO.read(input_stream);

        // make a new image with transparency and new size
        int width = old_image.getWidth();
        int heigth = old_image.getHeight();
        int new_heigth = heigth + 200;
        BufferedImage new_image = new BufferedImage(width, new_heigth, BufferedImage.TRANSLUCENT);

        // copy the real image to a new image that was created
        Graphics2D graphics = (Graphics2D) new_image.getGraphics();
        graphics.drawImage(old_image, 0, 0, null);

        // set the font of the phrase
        var pont = new Font("Impact", Font.BOLD, 50);
        graphics.setColor(Color.YELLOW);
        graphics.setFont(pont);
        
        // Get datas to change the phrase location
        var font_hw = graphics.getFontMetrics();
        Rectangle2D rex = font_hw.getStringBounds(arquive_name, graphics);
        int width_text = (int)rex.getWidth();

        // Put the first and second title name and add to a array
        String[] name = arquive_name
            .replace(":", "")
            .replace("by", "")
            .split(" ");
        int place_heigth = new_heigth - ((new_heigth - heigth)/3);


        // write a phrase into the new image
        for (int i = 0; i < 4; i++)
        {
            // draw the phrase
            graphics.drawString("ME IN THE " + name[0] + " " + name[1], ((width - width_text) / 2) + 50, place_heigth - 25);
           
            BufferedImage img = ImageIO.read(new File("./app-imdb/images/eu.png"));
            graphics.drawImage(img, width - 200, place_heigth - 460, null);

            // the layout of the contour
            FontRenderContext fontRenderContext = graphics.getFontRenderContext();
            var textLayout = new TextLayout("ME IN THE " + name[0] + " " + name[1], pont, fontRenderContext);

            // The position of the contour
            Shape outLine = textLayout.getOutline(null);
            AffineTransform transform = graphics.getTransform();
            transform.translate(((width - width_text) / 2) + 50, place_heigth - 25);
            graphics.setTransform(transform);

            // Defining the tickness of the contour
            var outLineStoke = new BasicStroke(width * 0.004f);
            graphics.setStroke(outLineStoke);

            // Defining color
            graphics.setColor(Color.BLACK);
            graphics.draw(outLine);
            graphics.setClip(outLine);


        }
        // put the new image into a arquive
        File dir = new File("./app-imdb/output/");
        dir.mkdir();

        ImageIO.write(new_image, "png", new File("./app-imdb/output/" + arquive_name
            .replace(" ", "_")
            .replace(":", "")
            .toLowerCase() + ".png"));
    }

}