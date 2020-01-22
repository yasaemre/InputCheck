import java.io.*;
import java.util.*;
import javafx.application.*;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CustomerInput extends Application {

    private Stage primaryStage;
    private Text statusText, resultText;
    private Button uploadButton;

    private final static Font RESULT_FONT = Font.font("Helvetica", 24);
    private final static Font INPUT_FONT = Font.font("Helvetica", 20);

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        VBox primaryBox = new VBox();
        primaryBox.setAlignment(Pos.CENTER);
        primaryBox.setSpacing(20);
        primaryBox.setStyle("-fx-background-color: white");

        VBox uploadBox = new VBox();
        uploadBox.setAlignment(Pos.CENTER);
        uploadBox.setSpacing(20);
        Text uploadLabel = new Text("Upload a comma-separated file with customer data.");
        uploadLabel.setFont(INPUT_FONT);
        uploadButton = new Button("Upload data");
        uploadButton.setOnAction(event -> {
			try {
				processDataUpload(event);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IllegalCharacterException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

        uploadBox.getChildren().add(uploadLabel);
        uploadBox.getChildren().add(uploadButton);
        primaryBox.getChildren().add(uploadBox);

        VBox resultsBox = new VBox();
        resultsBox.setAlignment(Pos.CENTER);
        resultsBox.setSpacing(20);
        statusText = new Text("");
        statusText.setVisible(false);
        statusText.setFont(RESULT_FONT);
        statusText.setFill(Color.RED);
        resultText = new Text("");
        resultText.setVisible(false);
        resultText.setFont(RESULT_FONT);
        resultsBox.getChildren().add(statusText);
        resultsBox.getChildren().add(resultText);
        primaryBox.getChildren().add(resultsBox);

        Scene scene = new Scene(primaryBox, 475, 200, Color.TRANSPARENT);
        primaryStage.setTitle("Customer Data Upload");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void processDataUpload(ActionEvent event) throws FileNotFoundException, IllegalCharacterException,IOException{
        statusText.setVisible(false);
        resultText.setVisible(false);
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(primaryStage);
        parseFile(file);

    }

    private void parseFile(File file) throws IllegalCharacterException, FileNotFoundException, IOException {
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		
		Scanner fileScan = new Scanner(new FileInputStream(file));
		String oneLine = "";
		String customerName = "";
		int orders = 0;
		try {
			fileScan.nextLine();

			while(fileScan.hasNext()) {
				oneLine = fileScan.nextLine();

				Scanner lineScan = new Scanner(oneLine);
				lineScan.useDelimiter(",");
				customerName = lineScan.next();
				String ordersStr = lineScan.next();
				orders = Integer.parseInt(ordersStr);

				Customer customer = new Customer(customerName, orders);
				customerList.add(customer);
				int sum = 0;
				
				if (!oneLine.matches(".*[@$#%].*")) {
					for (Customer customerObj : customerList) {
			    		statusText.setVisible(true);
			    		statusText.setText("Success");
			    		resultText.setVisible(true);
			    		sum += customerObj.getNumberOfOrders();
			    		resultText.setText("" + sum);
			    		System.out.println(customerObj);
			            uploadButton.setDisable(true);
			    	}
				} else if(oneLine.matches(".*[@$#%].*"))  {
					throw new IllegalCharacterException("Invalid character");
				} else if (!file.exists()) {
					throw new IOException("The file not here");
				}
				else {
					throw new IOException("IO Exception occured");
				}
		    	
			}
		} catch (NumberFormatException e) {
			statusText.setText(orders + " is non integer data");
			resultText.setText("");
		} catch(IllegalCharacterException e) {
		    statusText.setText(customerName + " has invalid character");
		    resultText.setText("");
		} catch(FileNotFoundException e) {
			statusText.setText(e.getMessage());
		} catch(IOException e) {
			statusText.setText("IO Exception occured");
		} finally {
	            fileScan.close();
	        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}