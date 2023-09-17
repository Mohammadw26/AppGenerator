import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

public class AndroidProjectGeneratorGUI extends Application {

    private TextField appNameTextField;
    private Button generateButton;
    private ColorPicker buttonColorPicker;
    private ProgressBar progressBar;
    private File selectedLogo;
    private File selectedGoogleServicesFile; // Store the selected google-services.json file

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Android Project Generator");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Label appNameLabel = new Label("Enter App Name:");
        appNameTextField = new TextField();

        Label buttonColorLabel = new Label("Button Color:");
        buttonColorPicker = new ColorPicker();

        generateButton = new Button("Generate Project");
        progressBar = new ProgressBar(0);
        Button logoPickerButton = new Button("Upload logo");
        Button googleServicesButton = new Button("Upload google-services.json");

        grid.add(appNameLabel, 0, 0);
        grid.add(appNameTextField, 1, 0);
        grid.add(buttonColorLabel, 0, 1);
        grid.add(buttonColorPicker, 1, 1);
        grid.add(logoPickerButton, 0, 3, 2, 1);
        grid.add(googleServicesButton, 0, 4, 2, 1);
        grid.add(generateButton, 0, 5, 2, 1);
        grid.add(progressBar, 0, 6, 2, 1);

        logoPickerButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Upload logo");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif");
            fileChooser.getExtensionFilters().add(extFilter);
            selectedLogo = fileChooser.showOpenDialog(primaryStage);
        });

        googleServicesButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Upload google-services.json");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON Files", "*.json");
            fileChooser.getExtensionFilters().add(extFilter);
            selectedGoogleServicesFile = fileChooser.showOpenDialog(primaryStage);
        });

        generateButton.setOnAction(e -> {
            String appName = appNameTextField.getText();
            Color buttonColor = buttonColorPicker.getValue();

            if (appName.isEmpty()) {
                showAlert("Error", "Please provide an app name.");
            } else if (selectedGoogleServicesFile == null) {
                showAlert("Error", "Please upload google-services.json.");
            } else {
                generateAndroidProject(appName, buttonColor);
            }
        });

        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void generateAndroidProject(String appName, Color buttonColor) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                String templateProjectPath = "src/main/templates/projectTemplateMatchMaking";

                // Check if the template project directory exists
                File templateProjectDirectory = new File(templateProjectPath);
                if (!templateProjectDirectory.exists()) {
                    System.err.println("Error: Template resource directory does not exist.");
                    return null;
                }


                String newProjectDir = "C:\\Users\\WATTADM\\AndroidStudioProjects";

                File newProjectDirectory = new File(newProjectDir, appName);
                newProjectDirectory.mkdirs();

                try {
                    FileUtils.copyDirectory(templateProjectDirectory, newProjectDirectory);


                    modifyAppName(newProjectDirectory, appName);
                    modifyLogo(newProjectDir,selectedLogo);
                    modifyGoogleServiceJson(newProjectDirectory, selectedGoogleServicesFile);

                    customizeColors(newProjectDirectory, buttonColor);

                    System.out.println("Android project generated successfully at: " + newProjectDirectory.getAbsolutePath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    throw ex;
                }
                return null;
            }
        };

        progressBar.progressProperty().bind(task.progressProperty());

        task.setOnSucceeded(e -> {
            progressBar.progressProperty().unbind();
            progressBar.setProgress(1.0);
            showAlert("Success", "Android project generated successfully.");
        });

        task.setOnFailed(e -> {
            showAlert("Error", "Failed to generate Android project. Check template directory.");
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void modifyGoogleServiceJson(File newProjectDirectory, File selectedGoogleServicesFile) {
        if (selectedGoogleServicesFile != null) {
            File googleServicesDir = new File(newProjectDirectory, "app");

            // Specify the destination path for the google-services.json file
            File destGoogleServicesFile = new File(googleServicesDir, "google-services.json");

            try {
                // Copy the selected google-services.json file to the destination directory with the new name
                FileUtils.copyFile(selectedGoogleServicesFile, destGoogleServicesFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void modifyLogo(String newProjectDir, File selectedLogo) {
        if (selectedLogo != null) {
            File appDir = new File(newProjectDir, appNameTextField.getText());
            File resDir = new File(appDir, "app/src/main/res");

            // Specify the destination directory for the logo (drawable directory)
            File drawableDir = new File(resDir, "drawable");

            // Ensure the drawable directory exists, and create it if it doesn't
            if (!drawableDir.exists()) {
                drawableDir.mkdirs();
            }

            // Specify the destination path for the logo file (appLogo.png)
            File destLogoFile = new File(drawableDir, "app_logo.png");

            try {
                // Copy the selected logo file to the destination directory with the new name
                FileUtils.copyFile(selectedLogo, destLogoFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void modifyAppName(File projectDirectory, String appName) {
        // Specify the path to the strings.xml file
        String stringsXmlPath = Paths.get(projectDirectory.getAbsolutePath(), "app", "src", "main", "res", "values", "strings.xml").toString();

        try {
            // Read the content of strings.xml into a Stream
            Stream<String> lines = Files.lines(Paths.get(stringsXmlPath));

            // Modify the app name string and collect the lines into a list
            String modifiedContent = lines.map(line -> {
                if (line.contains("<string name=\"app_name\">")) {
                    // Replace the old app name with the new app name
                    return "        <string name=\"app_name\">" + appName + "</string>";
                } else {
                    return line;
                }
            }).collect(Collectors.joining("\n"));

            // Write the modified content back to strings.xml
            Files.write(Paths.get(stringsXmlPath), modifiedContent.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void customizeColors(File projectDirectory, Color buttonColor) {
        // Specify the path to the colors.xml file
        String colorsXmlPath = Paths.get(projectDirectory.getAbsolutePath(), "app", "src", "main", "res", "values", "colors.xml").toString();

        try {
            // Convert JavaFX Color to Android color format (ARGB)
            String androidColor = String.format("#%02X%02X%02X",
                    (int) (buttonColor.getRed() * 255),
                    (int) (buttonColor.getGreen() * 255),
                    (int) (buttonColor.getBlue() * 255));

            System.out.println(androidColor);
            // Read the content of colors.xml into a Stream
            Stream<String> lines = Files.lines(Paths.get(colorsXmlPath));

            // Modify the color value and collect the lines into a list
            String modifiedContent = lines.map(line -> {
                if (line.contains("<color name=\"theme_color\">")) {
                    // Replace the old color value with the new Android color value
                    return "        <color name=\"theme_color\">" + androidColor + "</color>";
                } else {
                    return line;
                }
            }).collect(Collectors.joining("\n"));

            // Write the modified content back to colors.xml
            Files.write(Paths.get(colorsXmlPath), modifiedContent.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
