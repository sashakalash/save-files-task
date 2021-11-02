import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    private static final String PATH_TO_SAVE_PROGRESS = "/Users/admin/Games/savegames/";
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
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path + "zip_output.zip"))) {
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

}