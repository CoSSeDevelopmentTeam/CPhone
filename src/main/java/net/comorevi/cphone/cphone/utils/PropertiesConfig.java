package net.comorevi.cphone.cphone.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class PropertiesConfig {

    private Map<String, Object> data;
    private File file;
    private String encode = "UTF-8";

    private String comment = "";

    public PropertiesConfig() {
        this("");
    }

    public PropertiesConfig(String path) {
        this(path, new HashMap<>());
    }

    public PropertiesConfig(String path, Map<String, Object> data) {
        this(new File(path), data);
    }

    public PropertiesConfig(File file, Map<String, Object> data) {
        this.file = file;
        this.data = data;
    }

    /*
    static {
        data = new Properties();
    }
    */

    public void set(String key, Object value) {
        data.put(key, value.toString());
    }

    public void setAll(Map<String, Object> data) {
        this.data.clear();
        for(Map.Entry map : data.entrySet()) {
            this.data.put(String.valueOf(map.getKey()), String.valueOf(map.getValue()));
        }
    }

    public void remove(String key) {
        data.remove(key);
    }

    public void remove(String key, Object value) {
        data.remove(key, value);
    }

    public Object get(String key) {
        return data.get(key);
    }

    public int getInt(String key) {
        return data.get(key) == null ? null : Integer.parseInt(String.valueOf(data.get(key)));
    }

    public double getDouble(String key) {
        return data.get(key) == null ? null : Double.valueOf(String.valueOf(data.get(key)));
    }

    public float getFloat(String key) {
        return data.get(key) == null ? null : Float.valueOf(String.valueOf(data.get(key)));
    }

    public String getString(String key) {
        return data.get(key) == null ? null : ConfigFormatter.format(String.valueOf(data.get(key)));
    }

    public Map<String, Object> getAllAsMap() {
        return data;
    }

    public void clear() {
        data.clear();
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean save() {
        try {
            if (!file.exists()) {
                PrintWriter pw = new PrintWriter(file);
                pw.print("");
                pw.close();
            }

            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), encode);
            List<String> keys = new ArrayList<>();

            this.data.keySet().stream().forEach(key -> keys.add(key));
            keys.sort(String.CASE_INSENSITIVE_ORDER);

            if(comment != null && !comment.equals("")) osw.write("# " + comment + "\n");
            osw.write(new SimpleDateFormat("# yyyy/MM/dd EEE HH:mm:ss z\n").format(Calendar.getInstance().getTime()));

            for(String key : keys) {
                osw.write(key + "=" + String.valueOf(this.data.get(key)) + "\n");
            }

            osw.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean load(String path) {
        return load(new File(path));
    }

    public boolean load(File file) {
        try {
            return load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean load(InputStream in) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, encode));
            Map<String, Object> map = new HashMap<>();

            while(true) {
                String str = br.readLine();

                if (str == null) break;
                if (str.startsWith("#")) continue;

                String[] splited = str.split("=");

                if (splited.length == 1) {
                    map.put(splited[0], null);
                    continue;
                }

                if(splited.length < 2) continue;

                String value = "";
                for(int i = 1;i < splited.length;i++) {
                    value += splited[i];
                }

                try {
                    map.put(splited[0], Integer.parseInt(value));

                } catch (NumberFormatException ex) {
                    try {
                        map.put(splited[0], Double.parseDouble(value));

                    } catch (NumberFormatException ex1) {
                        try {
                            map.put(splited[0], Float.parseFloat(value));

                        } catch (NumberFormatException ex2) {
                            map.put(splited[0], (Object) value);
                        }
                    }
                }

                map.put(splited[0], value);
            }

            this.data = map;
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString() {
        return data.toString();
    }

}