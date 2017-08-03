package main;

/*
 * 
 * this App created by Ahmed Hatem 
 * date : 1 / 8 / 2017
 * 
 * you can delete this comment 
 * 
 * */


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.ini4j.Ini;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	
	public static int APP_WIDTH  = 935;
	public static int APP_HEIGHT = 650;
	public static String APP_TITLE = "HD Image Downloader";
	
	private final static String imagesFolder = System.getProperty("java.io.tmpdir") + "HD_Image_Downloader\\";
	private final static String imagesFile   = System.getProperty("java.io.tmpdir") + "HD_Image_Downloader\\Images.ini";
	
	private double xOffset = 0;
	private double yOffset = 0;
	    
	ImageView appIcon = new ImageView(new Image("appIcon.png"));
	
	public Stage mainStage;
	public static BorderPane mainLayout;
	public static ScrollPane centerLayoutC;
	public static FlowPane imagesLayout;
	
	@Override
	public void start(Stage mainStage) {
		this.mainStage = mainStage;
		this.mainStage.setTitle(APP_TITLE);
		this.mainStage.initStyle(StageStyle.UNDECORATED);
		
		appIcon.setFitWidth(65);
		appIcon.setFitHeight(59);
		
		Text title = new Text(APP_TITLE);
		title.setFill(Color.web("#696967"));
		title.setFont(Font.font("Segoe UI", FontWeight.BOLD ,24));
		
		Pane topSpace = new Pane();
		topSpace.setPrefSize(473, 92);
		
		Text minimize = new Text("_");
		minimize.setFill(Color.web("#696967"));
		minimize.setFont(Font.font("Segoe UI", FontWeight.BOLD ,46));
		minimize.setCursor(Cursor.HAND);
		minimize.setOnMousePressed(e -> mainStage.setIconified(true));
		
		Text exit = new Text("X  ");
		exit.setFill(Color.web("#696967"));
		exit.setFont(Font.font("Segoe UI", FontWeight.BOLD ,46));
		exit.setCursor(Cursor.HAND);
		exit.setOnMousePressed(e -> mainStage.close());
		
		HBox topLayout = new HBox();
		topLayout.setPrefSize(927, 92);
		topLayout.setAlignment(Pos.CENTER_LEFT);
		topLayout.setSpacing(10);
		HBox.setMargin(minimize, new Insets(0, 0, 20, 0));
		topLayout.getChildren().addAll(appIcon , title , topSpace , minimize , exit);
				
		AnchorPane rightLayout = new AnchorPane();
		rightLayout.setPrefSize(30, 550);
		
		AnchorPane leftLayout = new AnchorPane();
		leftLayout.setPrefSize(30, 550);
		
		AnchorPane bottomLayout = new AnchorPane();
		bottomLayout.setPrefSize(927, 24);
		
		imagesLayout = new FlowPane();
		imagesLayout.setPrefSize(859, 517);
		imagesLayout.setVgap(10);
		imagesLayout.setHgap(15);
		imagesLayout.setAlignment(Pos.TOP_CENTER);	
		imagesLayout.setPadding(new Insets(10 , 0 , 10 , 0));
		
		//try {downloadImagesFile();} catch(Exception e) {System.err.println("Can't Download Images File"); e.printStackTrace();}
		try {downloadImages();} catch (Exception e) {System.err.println("Can't Download Images"); e.printStackTrace();}
		try {addImages();} catch(Exception e) {System.err.println("Can't add Images:( "); e.printStackTrace();}
		
		ScrollPane centerLayout = new ScrollPane();
		centerLayout.setId("centerLayout");
		centerLayout.setHbarPolicy(ScrollBarPolicy.NEVER);
		centerLayout.setContent(imagesLayout);
		centerLayoutC = centerLayout;
		
		mainLayout = new BorderPane();
		mainLayout.setId("mainLayout");
		BorderPane.setMargin(topLayout, new Insets(0, 0, 0, 10));
		mainLayout.setCenter(centerLayout);
		mainLayout.setTop(topLayout);
		mainLayout.setRight(rightLayout);
		mainLayout.setLeft(leftLayout);
		mainLayout.setBottom(bottomLayout);
		
		
		topSpace.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
		topSpace.setOnMouseDragged(event -> {
			this.mainStage.setX(event.getScreenX() - xOffset);
			this.mainStage.setY(event.getScreenY() - yOffset);
        });
		
		Scene mainScene = new Scene(mainLayout , APP_WIDTH , APP_HEIGHT);
		mainScene.getStylesheets().add("Style.css");
		this.mainStage.setScene(mainScene);
		this.mainStage.show();
		
	}
	
	public void downloadImagesFile() throws Exception {
		
		File ImagesFolder = new File(imagesFolder);
		if(!ImagesFolder.exists())
			ImagesFolder.mkdir();
		
		new File(imagesFolder + "Images.ini").delete();
		
		System.out.println("Downloading Image File");
		
		URL url = new URL("https://raw.githubusercontent.com/ahatem/DgAppMaker/master/Images.ini");
		URLConnection connection = url.openConnection();
		InputStream is = connection.getInputStream();
		OutputStream outputStream = new FileOutputStream(new File(imagesFolder + "Images.ini"));
		int read = 0;
		byte[] bytes = new byte[1024];
		while ((read = is.read(bytes)) != -1) {
			outputStream.write(bytes, 0, read);
		}
		outputStream.close();
	}
	
	
	private void addImages() throws Exception {

		Ini iniFile = new Ini(new File(imagesFile));
		int size = Integer.valueOf(iniFile.get("Images", "Images"));

		for (int i = 1; i <= size ; i++) {
			
			String imageName = iniFile.get("Image" + i, "filename");
			String thumbImage = imagesFolder + imageName.substring(1, imageName.length() - 1);
			String imaegePath = iniFile.get("Image" + i, "download");
			
			String facebookUrl = iniFile.get("Image" + i, "sof");
			String twiterUrl   = iniFile.get("Image" + i, "sot");
			String googleUrl   = iniFile.get("Image" + i, "sog"); 
			
			ImageViewer iv = new ImageViewer(thumbImage
											,imaegePath.substring(1, imaegePath.length() - 1)
											,facebookUrl.substring(1, facebookUrl.length() - 1)
											,twiterUrl.substring(1, twiterUrl.length() - 1)
											,googleUrl.substring(1, googleUrl.length() - 1));
			imagesLayout.getChildren().add(iv);
			//FlowPane.setMargin(iv, new Insets(10 ,10 , 10 , 20));
		}
	}
	
	private void downloadImages() throws Exception {
		
		Ini iniFile = new Ini(new File(imagesFile));
		int size = Integer.valueOf(iniFile.get("Images", "Images"));

		for (int i = 1; i <= size ; i++) {
			
			String thumbImage = iniFile.get("Image" + i, "thumb");
			String imageName = iniFile.get("Image" + i, "filename");
			
			if (!new File(imagesFolder + imageName.substring(1, imageName.length() - 1)).isFile()) {
				System.out.println("Downloading Image...");
				URL url = new URL(thumbImage.substring(1, thumbImage.length() - 1));
				URLConnection connection = url.openConnection();
				connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
				InputStream is = connection.getInputStream();
				OutputStream outputStream = new FileOutputStream(new File(imagesFolder + imageName.substring(1, imageName.length() - 1)));
				int read = 0;
				byte[] bytes = new byte[1024];
				while ((read = is.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
				outputStream.close();
			}
		
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);

	}

}
