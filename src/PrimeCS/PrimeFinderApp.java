package PrimeCS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PrimeFinderApp extends Application {
	private static CheckBox isServer;
	private static Button startApp;
	
	private static TextArea finder = new TextArea();
	private static TextField inputField;
		
	private static Boolean dataRecieved;
	private static Lock dataLock = new ReentrantLock();
	
	
	private Parent createContent() {
		dataRecieved = false;
		
		isServer = new CheckBox("Is Server");
		isServer.setSelected(true);
		startApp = new Button("Start");
		startApp.setOnAction(e -> StartApp());
		
		finder.setPrefHeight(500);
		
		inputField = new TextField();
		inputField.setOnAction(event -> {
			if (startApp.isDisable())
			{
				dataLock.lock();
				dataRecieved = true;
				dataLock.unlock();
			}
		});

		HBox hBox = new HBox(50, isServer, startApp);
		VBox vBox = new VBox(15, hBox, finder, inputField);
		vBox.setPrefSize(600, 600);
		
		return vBox;
	}
	
	public static class SeverThread extends Thread {
		public void run()
		{
			try(ServerSocket serverSocket = new ServerSocket(3013)) {
				finder.appendText("Waiting for Client\n");
				Socket foundSocket = serverSocket.accept();
				finder.appendText("Client connected\n");
            
				BufferedReader input = new BufferedReader(
                    new InputStreamReader(foundSocket.getInputStream()));
				PrintWriter output = new PrintWriter(foundSocket.getOutputStream(), true);

				while(true) {
					finder.appendText("Waiting for number\n");
					String numbPut = input.readLine();
					int primeNumber = -1; 
					if(numbPut.equals("exit")) {
						break;
					}
					else
					{
						primeNumber = Integer.parseInt(numbPut);
					}

					finder.appendText("Sending Answer\n");
					output.println(PrimeTest(primeNumber));
				}
				finder.appendText("Server Ended\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static class ClientThread extends Thread {
		public void run()
		{
			try(Socket clientSocket = new Socket("localhost", 3013)) {
	            finder.appendText("Server found\n");
	            BufferedReader primes = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter stringToPrime = new PrintWriter(clientSocket.getOutputStream(), true);
	            
	            String primeT = "";
                String primeNum;
                
                finder.appendText("Input number below\n");
	            
	            do {
	            	dataLock.lock();
	            	if (dataRecieved)
	            	{
	            		primeT = inputField.getText();
	                    stringToPrime.println(primeT);
		                if(!primeT.equals("exit")) {
		                    primeNum = primes.readLine();
		                    if (primeNum.equals("yes"))
		                    {
			                    finder.appendText(primeNum + ", " + primeT + " is a prime number\n");
		                    }
		                    else
		                    {
			                    finder.appendText(primeNum + ", " + primeT + " is not a prime number\n");
		                    }
		                }
		                inputField.clear();
		                dataRecieved = false;
	            	}
	            	dataLock.unlock();
	            } while (!primeT.equals("exit"));
				finder.appendText("Client Ended\n");
	        } catch(IOException e) {
	        	finder.appendText("Client Error: " + e.getMessage());
	        }
		}
	}
	
	private void StartApp() {
		isServer.setDisable(true);
		startApp.setDisable(true);

		if (isServer.isSelected())
		{
			Thread severThread = new SeverThread();
			severThread.start();
		}
		else
		{
			Thread clientThread = new ClientThread();
			clientThread.start();
		}
	}
	
    static String PrimeTest (int num) {
        if (num > 0 && num < 4)
        {
            return "yes";
        }
        else if (num <= 0)
        {
            return "no";
        }

        boolean flag = false;
        for (int i = 2; i <= num / 2; ++i) {
            if (num % i == 0) {
                flag = true;
            }
        }

        if (!flag) {
            return "yes";
        } else {
            return "no";
        }
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(createContent()));
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);

	}

}
