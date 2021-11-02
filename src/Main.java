import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    private static final String PATH_TO_SAVE_PROGRESS = "/Users/kalash/Games/savegames/";
    private static final String ZIP_OUTPUT_NAME = "zip_output.zip";
    private static ArrayList<String> pathStore = new ArrayList();

    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(45, 6, 7, 16.05);
        GameProgress gameProgress2 = new GameProgress(65, 7, 8, 66.05);
        GameProgress gameProgress3 = new GameProgress(15, 9, 17, 116.05);

        saveGame(PATH_TO_SAVE_PROGRESS, gameProgress1);
        saveGame(PATH_TO_SAVE_PROGRESS, gameProgress2);
        saveGame(PATH_TO_SAVE_PROGRESS, gameProgress3);

        zipFiles(PATH_TO_SAVE_PROGRESS, pathStore);

        removeUnpackedFiles(pathStore);

        openZip(PATH_TO_SAVE_PROGRESS + ZIP_OUTPUT_NAME, PATH_TO_SAVE_PROGRESS);

        showSavedFiles(PATH_TO_SAVE_PROGRESS);
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        String fileName = path + "save-" + new Date().getTime();
        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
            pathStore.add(fileName);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String path, List<String> filesPathList) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path + ZIP_OUTPUT_NAME))) {
            for (String filePath : filesPathList) {
                try (FileInputStream fis = new FileInputStream(filePath)) {
                    ZipEntry entry = new ZipEntry(filePath.replace(path, ""));
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            zout.closeEntry();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void removeUnpackedFiles(ArrayList<String> pathList) {
        for (String filePath : pathList) {
            File dir = new File(filePath);
            if (dir.delete()) {
                System.out.println("Каталог удален");
            }
        }
    }

    public static void openZip(String zipPath, String destination) {
        try (ZipInputStream zin = new ZipInputStream(new
                FileInputStream(zipPath))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(destination + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void showSavedFiles(String path) {
        File dir = new File(path);
        if (dir.isDirectory()) {
            for (File item : dir.listFiles()) {
                if (item.getName().contains("save")) {
                    System.out.println(openProgress(path + item.getName()).toString());
                }
            }
        }
    }

    public static GameProgress openProgress(String filePath) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }

}