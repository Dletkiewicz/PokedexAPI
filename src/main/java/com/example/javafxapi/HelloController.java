package com.example.javafxapi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloController {

    @FXML
    public TextField tfName, tfID;

    @FXML
    private TableView<DBTable> table;
    @FXML
    private TableColumn<DBTable, String> columnID;
    @FXML
    private TableColumn<DBTable, String> columnName;

    @FXML
    ObservableList<DBTable> oblist = FXCollections.observableArrayList();

    public void getButton() throws SQLException {

        table.getItems().clear();

        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM pokemons");

            while (rs.next()) {
                oblist.add(new DBTable(rs.getString("id"), rs.getString("name")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
        }

        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        table.setItems(oblist);

    }

    public void checkPokemon()

    public void addNewPokemon(ActionEvent event) {

        if (tfID.getText().isEmpty() || tfName.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("You need to fill up all text fields!");
            alert.showAndWait();
        } else {
            try {
                int id = Integer.parseInt(tfID.getText());
                String name = tfName.getText();
                System.out.println(id + " " + name);

                Connection con = DBConnection.getConnection();
                String insertStr = "INSERT INTO pokemons(id,name) VALUES(?,?);";
                PreparedStatement myInsert = con.prepareStatement(insertStr);
                myInsert.setString(1, String.valueOf(id));
                myInsert.setString(2, name);
                myInsert.executeUpdate();
                getButton();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("SUCCESS");
                alert.setHeaderText("Succesfully added new Pokemon to Database!");
                alert.showAndWait();

                System.out.println("Added new pokemon to db");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    }
}