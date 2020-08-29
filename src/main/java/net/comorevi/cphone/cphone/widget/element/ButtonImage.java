package net.comorevi.cphone.cphone.widget.element;

public class ButtonImage {

    private ImageType type;
    private String data;

    public ButtonImage(ImageType type, String data) {
        this.type = type;
        this.data = data;
    }

    public ImageType getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public void setType(ImageType type) {
        this.type = type;
    }

    public void setData(String data) {
        this.data = data;
    }

    public enum ImageType {
        URL("url"),
        PATH("path");

        private String typeString;

        ImageType(String type) {
            this.typeString = type;
        }

        public String getTypeString() {
            return typeString;
        }

        public static ImageType getTypeByString(String value) {
            for (ImageType imageType: ImageType.values()) {
                if (imageType.getTypeString().equals(value)) return imageType;
            }
            throw new IllegalArgumentException("No such enum object: " + value);
        }
    }
}


