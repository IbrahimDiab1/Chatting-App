package com.liqaa.server.controllers.FXMLcontrollers;

import com.liqaa.server.Main;
import com.liqaa.server.controllers.services.implementations.MessageServiceImpl;
import com.liqaa.server.controllers.services.implementations.UserServicesImpl;
import com.liqaa.server.util.FilePaths;
import com.liqaa.server.util.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StatisticsController {

    @FXML
    private Label Countries;
    @FXML
    private StackedBarChart<String, Number> DailyEntries;
    @FXML
    private CategoryAxis DailyEntries_X;
    @FXML
    private NumberAxis DailyEntries_Y;
    @FXML
    private Text FemaleText;
    @FXML
    private Text MaleText;
    @FXML
    private Label OfflineUser;
    @FXML
    private Label OnlineUser;
    @FXML
    private StackedBarChart<String, Number> TopCountriesChart;
    @FXML
    private CategoryAxis TopCountriesChart_X;
    @FXML
    private NumberAxis TopCountriesChart_Y;
    @FXML
    private Label TotalUser;

    private ScheduledExecutorService backgroundExecutor;

    public void initialize() {
        // Initialize placeholder data
        initializePlaceholderChartData();

        // Set chart axis labels
        TopCountriesChart_Y.setLabel("Number of Users");
        DailyEntries_Y.setLabel("Number of Messages");

        DailyEntries.setLegendVisible(false);

        // Start background updates
        startBackgroundUpdates();
    }

    @FXML
    public void logout(MouseEvent event) {
        System.out.println("Logging out...");
        try {
            SceneManager.getInstance().switchScene(new Scene(FXMLLoader.load(Main.class.getResource(FilePaths.LOGIN_SCREEN_FXML))));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void switchToAnnouncements(MouseEvent event) {
        System.out.println("Switching to Announcements...");
        switchScene(FilePaths.ANNOUNCEMENTS_SCREEN_FXML);
    }

    @FXML
    public void switchToManager(MouseEvent event) {
        onDestroy(); // Shut down background threads
        Platform.runLater(() -> {try {

            SceneManager.getInstance().switchScene(new Scene(FXMLLoader.load(Main.class.getResource(FilePaths.MANAGER_SCREEN_FXML))));
        } catch (IOException e) {
            e.printStackTrace();
        }});
    }

    private void switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlPath));
            Scene scene = new Scene(loader.load());
            Platform.runLater(() -> SceneManager.getInstance().switchScene(scene));
        } catch (IOException e) {
            System.err.println("Error loading scene: " + fxmlPath);
            e.printStackTrace();
        }
    }

    private void initializePlaceholderChartData() {
        Platform.runLater(() -> {
            XYChart.Series<String, Number> placeholderSeries = new XYChart.Series<>();
            placeholderSeries.getData().add(new XYChart.Data<>("Loading", 0));
            TopCountriesChart.getData().add(placeholderSeries);
            DailyEntries.getData().add(placeholderSeries);
        });
    }

    private void updateAllData() {
        // Fetch data in the background
        int totalUsers = UserServicesImpl.getInstance().getNumberAllUsers();
        int onlineUsers = UserServicesImpl.getInstance().getNumberAllOnlineUsers();
        int offlineUsers = UserServicesImpl.getInstance().getNumberAllOfflineUsers();
        int numberOfCountries = UserServicesImpl.getInstance().getNumberAllCountryOfUsers();

        double malePercentage = totalUsers == 0 ? 0 : (UserServicesImpl.getInstance().getNumberAllMaleUsers() / (double) totalUsers) * 100;
        double femalePercentage = totalUsers == 0 ? 0 : (UserServicesImpl.getInstance().getNumberAllFemaleUsers() / (double) totalUsers) * 100;

        Map<String, Integer> topCountriesData = UserServicesImpl.getInstance().getTopCountriesOfUsers();
        Map<String, Integer> messagesPerDay = MessageServiceImpl.getInstance().getMessagesPerDay();

        // Update the UI on the JavaFX Application Thread
        Platform.runLater(() -> {
            // Update user statistics
            TotalUser.setText(String.valueOf(totalUsers));
            OnlineUser.setText(String.valueOf(onlineUsers));
            OfflineUser.setText(String.valueOf(offlineUsers));
            Countries.setText(String.valueOf(numberOfCountries));
            MaleText.setText(String.valueOf( (int)malePercentage+"%"));
            FemaleText.setText(String.valueOf( (int)femalePercentage+"%"));

            // Update Top Countries Chart
            updateChart(TopCountriesChart, topCountriesData, "Users");

            // Update Daily Entries Chart
            updateDailyEntriesChart(messagesPerDay);

            // Apply bar styles
            setBarColorToBlue();
        });
    }

    private void updateChart(StackedBarChart<String, Number> chart, Map<String, Integer> data, String seriesName) {
        chart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(seriesName);
        data.forEach((key, value) -> series.getData().add(new XYChart.Data<>(key, value)));
        chart.getData().add(series);
    }

    private void updateDailyEntriesChart(Map<String, Integer> actualData) {
        Map<String, Integer> messagesPerDay = initializeDaysOfWeek();
        actualData.forEach(messagesPerDay::put);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        messagesPerDay.forEach((day, count) -> series.getData().add(new XYChart.Data<>(day, count)));

        DailyEntries.getData().clear();
        DailyEntries.getData().add(series);
    }

    private Map<String, Integer> initializeDaysOfWeek() {
        Map<String, Integer> daysOfWeek = new LinkedHashMap<>();
        daysOfWeek.put("Monday", 0);
        daysOfWeek.put("Tuesday", 0);
        daysOfWeek.put("Wednesday", 0);
        daysOfWeek.put("Thursday", 0);
        daysOfWeek.put("Friday", 0);
        daysOfWeek.put("Saturday", 0);
        daysOfWeek.put("Sunday", 0);
        return daysOfWeek;
    }

    private void startBackgroundUpdates() {
        backgroundExecutor = Executors.newSingleThreadScheduledExecutor();
        backgroundExecutor.scheduleAtFixedRate(this::updateAllData, 0, 10, TimeUnit.SECONDS); // Update every 10 seconds
    }

    public void shutdown() {
        if (backgroundExecutor != null && !backgroundExecutor.isShutdown()) {
            backgroundExecutor.shutdown();
        }
    }

    public void onDestroy() {
        shutdown();
    }

    /**************************** Chart Styles *******************************/
    private void setBarColorToBlue() {
        for (XYChart.Series<String, Number> series : TopCountriesChart.getData()) {
            for (XYChart.Data<String, Number> data : series.getData()) {
                if (data.getNode() != null) {
                    data.getNode().setStyle("-fx-bar-fill: #4A90E2;");
                }
            }
        }

        for (XYChart.Series<String, Number> series : DailyEntries.getData()) {
            for (XYChart.Data<String, Number> data : series.getData()) {
                if (data.getNode() != null) {
                    data.getNode().setStyle("-fx-bar-fill: #4A90E2;");
                }
            }
        }
    }
}
