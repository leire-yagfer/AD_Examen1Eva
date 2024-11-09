package com.example.demoradiobutton;

import javafx.beans.binding.ObjectBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RadioButtonController {

	@FXML
	private RadioButton rdb01;
	@FXML
	private RadioButton rdb02;
	@FXML
	private ToggleGroup rdbgroup;
	@FXML
	private TextField txf01; //el texto de la izqda pone lo mismo que en el de la derecha
	@FXML
	private TextField txf02; //pone lo que albergan las variables win y osx




	private String win = "Windows";
	private String osx = "OS X";

	@FXML
	void initialize() {


		this.rdb01.setUserData(win);
		this.rdb02.setUserData(osx);
		//en vez de hacerlo de la siguiente manera, directamente en el FXML en la opcion de Selected (encima del ToggleGroup), lo marco y se pone por defecto
		this.rdb01.setSelected(true); //el botón que aparece seleccionado según se inicia
		this.txf01.setText(rdb01.getText()); //como txf01 tiene el texto que pone en el RBseleccionado, cojo el texto del RB y lo seteo en el txf01
		this.txf02.setText(win);


	}

	// Using Event Handler
	@FXML
	void rdbOnAction(ActionEvent event) {

		//miro que botón ha sido seleccionado
		if(rdb01.isSelected()) //windows
		{
			this.txf01.setText(rdb01.getText());
			this.txf02.setText(win);
		}
		else //apple
		{
			this.txf01.setText(rdb02.getText());
			this.txf02.setText(osx);
		}

	}





}
