package net.comorevi.cphone.cphone.widget.element;

import cn.nukkit.Player;
import net.comorevi.cphone.presenter.SharingData;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Button {

    private String text = "";
    private ButtonImage image = null;

    public Button() {
        this("");
    }

    public Button(String text) {
        this.text = text;
    }

    public Button(String text, ButtonImage image) {
        this.text = text;
        if (!image.getType().getTypeString().equals("") && !image.getData().equals("")) this.image = image;
    }

    public Button setImage(ButtonImage image) {
        if (!image.getType().getTypeString().equals("") && !image.getData().equals("")) this.image = image;

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

    public ButtonImage getImage() {
        return image;
    }

    public void onClick(Player player) {

    }

}
