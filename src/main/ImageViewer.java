package main;


import java.awt.Desktop;
import java.net.URL;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class ImageViewer extends StackPane {
	
	private String imageUrl ,thumbImage ,facebookUrl , twitterUrl , googleUrl;
	
	
	public ImageViewer(String thumbImage , String imageUrl , String facebookUrl , String twitterUrl , String googleUrl) {
		this.thumbImage = thumbImage;
		this.imageUrl = imageUrl;
		this.facebookUrl = facebookUrl;
		this.twitterUrl = twitterUrl;
		this.googleUrl = googleUrl;
		
		setPrefSize(35, 19);
		ImageView imageViwe = new ImageView(new Image("file:\\" + getThumbUrl()));
		imageViwe.setFitWidth(260);
		imageViwe.setFitHeight(145);
		imageViwe.setOnMouseClicked(event -> {
			if(event.getButton().equals(MouseButton.PRIMARY)){
				if(event.getClickCount() >= 2){
					downloadPage();
				}else {
					for (Node node :  Main.imagesLayout.getChildren()) {
						node.setEffect(null);
					}
					
					Lighting effect1 = new Lighting();

					effect1.setBumpInput(null);
					effect1.setDiffuseConstant(0.82);
					effect1.setSpecularConstant(2);
					effect1.setSurfaceScale(5.34);
					effect1.setSpecularExponent(34.42);
					
					InnerShadow effect2 = new InnerShadow();
					effect2.setChoke(1);
					effect2.setHeight(15); 
					effect2.setRadius(7);
					effect2.setWidth(15);
					effect2.setColor(Color.web("#e74c3c"));
					
					effect2.setInput(effect1);
					
					this.setEffect(effect2);
				}
			}
		});
		
		getChildren().add(imageViwe);
	
	}
	
	private void downloadPage() {
		System.out.println(getImageUrl());
		String html = "<body><style>body{background-color: #E5E9EC;}</style><div id='displayed'> <img src=" + getImageUrl() + " max-width= 100% max-height= 100% > </div></body>";
		
		Button homeBtn = new Button("" , new ImageView(new Image("home.png")));
		homeBtn.setPrefSize(86, 60);
		homeBtn.setOnAction(e -> Main.mainLayout.setCenter(Main.centerLayoutC));
		homeBtn.setCursor(Cursor.HAND);
		
		WebView webView = new WebView();
		webView.setPrefSize(500, 280);
		webView.setMinSize(500, 280);
		webView.setMaxSize(-1, -1);
		
		
		WebEngine webEngine = webView.getEngine();
		webEngine.loadContent(html);
		
		StackPane cLayout = new StackPane();
		cLayout.getChildren().addAll(homeBtn , webView);
		StackPane.setAlignment(homeBtn, Pos.TOP_LEFT);
		StackPane.setAlignment(webView, Pos.CENTER);
		StackPane.setMargin(homeBtn, new Insets(10 , 0 , 0 , 10));
		
		
		Text shareText = new Text("Share this image on");
		shareText.setFill(Color.web("#696967"));
		shareText.setFont(Font.font("Roboto", FontWeight.BOLD ,20));
		
		ImageView twitterImage = new ImageView(new Image("twitter.png"));
		twitterImage.setFitWidth(96);
		twitterImage.setFitHeight(96);
		twitterImage.setCursor(Cursor.HAND);
		twitterImage.setOnMousePressed(event -> { try{Desktop.getDesktop().browse(new URL(getTwitterUrl()).toURI());}catch(Exception e){e.printStackTrace();}});
		
		ImageView googleImage = new ImageView(new Image("google.png"));
		googleImage.setFitWidth(96);
		googleImage.setFitHeight(96);
		googleImage.setCursor(Cursor.HAND);
		googleImage.setOnMousePressed(event -> { try{Desktop.getDesktop().browse(new URL(getGoogleUrl()).toURI());}catch(Exception e){e.printStackTrace();}});
		
		ImageView facebookImage = new ImageView(new Image("facebook.png"));
		facebookImage.setFitWidth(96);
		facebookImage.setFitHeight(96);
		facebookImage.setCursor(Cursor.HAND);
		facebookImage.setOnMousePressed(event -> { try{Desktop.getDesktop().browse(new URL(getFacebookUrl()).toURI());}catch(Exception e){e.printStackTrace();}});
		
		Text downloadText = new Text("Download this image");
		downloadText.setFill(Color.web("#696967"));
		downloadText.setFont(Font.font("Roboto", FontWeight.BOLD ,20));
		
		Button DownloadBtn = new Button(" Download" , new ImageView(new Image("dload.png")));
		DownloadBtn.setFont(Font.font("Roboto Bold" , FontWeight.BOLD , 18));
		DownloadBtn.setTextFill(Color.WHITE);
		DownloadBtn.setPrefSize(165 , 63);
		DownloadBtn.setOnAction(e -> System.out.println(getImageUrl()));
		DownloadBtn.setCursor(Cursor.HAND);
		
		VBox rightLayout = new VBox();
		rightLayout.setPrefSize(216, 526);
		rightLayout.setAlignment(Pos.TOP_CENTER);
		rightLayout.setSpacing(10);
		rightLayout.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0); -fx-background-radius: 5 0 5 0; -fx-border-color: #919291; -fx-border-style: solid inside; -fx-border-width: 0; -fx-background-color: #E5E9EC;");
		rightLayout.getChildren().addAll(shareText , twitterImage , googleImage , facebookImage , downloadText , DownloadBtn);
		VBox.setMargin(shareText, new Insets(20 , 0 , 0 , 0));
		VBox.setMargin(downloadText, new Insets(10 , 0 , 10 ,0));
		
		BorderPane centerLayout = new BorderPane();
		centerLayout.setId("centerLayout");
		centerLayout.setCenter(cLayout);
		centerLayout.setRight(rightLayout);
		
		
		Main.mainLayout.setCenter(centerLayout);
	}
	
	public String getImageUrl() {
		return this.imageUrl;
	}
	
	public String getThumbUrl() {
		return this.thumbImage;
	}

	public String getThumbImage() {
		return thumbImage;
	}

	public String getFacebookUrl() {
		return facebookUrl;
	}

	public String getTwitterUrl() {
		return twitterUrl;
	}

	public String getGoogleUrl() {
		return googleUrl;
	}
	
}
