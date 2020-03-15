package net.comorevi.cphone.cphone.widget.element;

import cn.nukkit.Player;
import net.comorevi.cphone.presenter.SharingData;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Button {

    private final String type = "button";
    private String text;
    private Map<String, Object> image = new HashMap<>();

    public Button() {
        this("");
    }

    public Button(String text) {
        this.text = text;
        image.put("data", "");
        image.put("type", "");
    }

    public Button(String text, String imageType, String imageData) {
        this.text = text == null ? "" : text;

        if (imageType == null || !imageType.equals("url") && !imageType.equals("path")) imageType = "url";

        this.image.put("type", imageType);
        this.image.put("data", imageData);
    }

    public Button setImage(String imageType, String imageData) {

        if (imageType == null || !imageType.equals("url") && !imageType.equals("path")) imageType = "url";

        this.image.put("type", imageType);
        this.image.put("data", imageData);

        return this;
    }

    public byte[] getImageByteArray(File file) {
        try {
            BufferedImage image = ImageIO.read(file);

            if (image.getHeight() != 128 || image.getWidth() != 128) {
                image = new BufferedImage(128, 128, image.getType());
                Graphics2D g = image.createGraphics();
                g.drawImage(image, 0, 0, 128, 128, null);
                g.dispose();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, file.getName().substring(file.getName().lastIndexOf(".") + 1), baos);

            return baos.toByteArray();
        } catch (IOException e) {
            SharingData.server.getLogger().logException(e);
        }

        return null;
    }

    public Button setText(String text) {
        this.text = text == null ? "" : text;
        return this;
    }

    public String getText() {
        return this.text;
    }

    public String getName() {
        return "Button";
    }

    public Map<String, Object> getImage() {
        return image;
    }

    public void onClick(Player player) {

    }

}
