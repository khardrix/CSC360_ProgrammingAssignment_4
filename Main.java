/*********************************************************************************************************************
 *********************************************************************************************************************
 *****  Class: CSC-360-001-2019-040    Semester: Summer 2019    Professor: Richard Fox    Student: Ryan Huffman  *****
 *****-----------------------------------------------------------------------------------------------------------*****
 *****                                       Programming Assignment #4                                           *****
 *****___________________________________________________________________________________________________________*****
 *****                 This is a program used to create random stories, similarly to Mad Libs.                   *****
 *****                    The generated stories can be generated, saved, loaded or cleared.                      *****
 *****___________________________________________________________________________________________________________*****
 *****          This program has a lot of GUI components. Below is a list of them and their purpose:             *****
 *****-----------------------------------------------------------------------------------------------------------*****
 *****                                               Buttons:                                                    *****
 *****            "Generate" - Generate a new story (if the TextArea is currently editable) and display          *****
 *****                         the generated story in the TextArea. Display "Story created" in the               *****
 *****                         "Status" Label. If the TextArea is currently not editable, do not "generate"      *****
 *****                         a new story, but display "Create failure: TextArea not editable in the            *****
 *****                         "Status" Label.                                                                   *****
 *****                "Save" - Save the story currently displayed in the TextArea to a file with the name        *****
 *****                         specified by the user and currently typed into the "Filename" TextField.          *****
 *****                         The status Label displays "Story saved" and the "Filename" TextField is           *****
 *****                         cleared in order to prevent accidentally loading or overwriting the user          *****
 *****                         did not intend to.                                                                *****
 *****                "Load" - If the TextArea is currently editable, load the story from the File specified     *****
 *****                         by the user in the "Filename" TextField, display "Story loaded" in the "Status"   *****
 *****                         Label and the "Filename" TextField is cleared in order to prevent the user from   *****
 *****                         accidentally loading or overwriting a file the user did not intend to.            *****
 *****                         If the TextArea is not currently editable, do not load any file and display       *****
 *****                         "Load failure: TextArea not editable" in the "Status" Label.                      *****
 *****               "Clear" - If the TextArea is currently editable, clear all of the text from the TextArea    *****
 *****                         and display "Story cleared" in the "Status" Label. If the TextArea is currently   *****
 *****                         not editable, do not clear the text in the TextArea and display                   *****
 *****                         "Clear failure: TextArea not editable" in the "Status" Label.                     *****
 *****                "Quit" - Use System.exit(0); to close the GUI and exit the program.                        *****
 *****-----------------------------------------------------------------------------------------------------------*****
 *****                                                 Labels:                                                   *****
 *****              "Status" - Used to display the currently program status                                      *****
 *****            "Filename" - Used to display "Enter filename: " about the "Filename" TextField                 *****
 *****          "Select Sex" - Used to display "Main character sex: " above the "Sex" RadioButtons               *****
 *****       "Story Aspects" - Used to display "Select story elements: " above the "Story Aspects" CheckBoxes    *****
 *****      "Text Area Edit" - Used to display "Text area: " above the "Edit" RadioButtons                       *****
 *****            There are also two additional blank Labels placed above the "Status" Label and under           *****
 *****                                         the "Filename" TextField.                                         *****
 *****-----------------------------------------------------------------------------------------------------------*****
 *****                                               CheckBoxes:                                                 *****
 *****      "Starting Location" - Used to add a starting location to the story                                   *****
 *****       "Intended to meet" - Used to add a character the main character intended to meet to the story       *****
 *****           "Actually met" - Used to add a character the main character met to the story                    *****
 *****           "Travelled by" - Used to add the main characters mode of travel to the story                    *****
 *****        "Action en route" - Used to add an event that happened en route to the main character's            *****
 *****                            destination to the story                                                       *****
 *****         "Moral of story" - Used to add the moral of the story to the story                                *****
 *****-----------------------------------------------------------------------------------------------------------*****
 *****                                               RadioButtons:                                               *****
 *****     "Male" - Used to select the main character's name from a String Array of male character names         *****
 *****   "Female" - Used to select the main character's name from a String Array of female character names       *****
 *****    "Other" - Used to select the main character's name from a String Array of sexless character names      *****
 *****     "Edit" - Used to make the TextArea editable and display that in the "Status" Label                    *****
 *****  "No Edit" - Used to make the TextArea not editable and display that in the "Status" Label                *****
 *****-----------------------------------------------------------------------------------------------------------*****
 *****                                                  Text:                                                    *****
 *****                            TextArea to display the generated or loaded story                              *****
 *****      "Filename" TextField for the user to type in the name of a File the user wants to save or load       *****
 *****___________________________________________________________________________________________________________*****
 *****                              The program will have the following methods:                                 *****
 *****             generateStory() - Generate the random story based upon the status of the GUI                  *****
 *****                               (If the TextArea is currently editable)                                     *****
 *****  loadStory(String filename) - Load the story whose filename was typed in by the user to the "Filename"    *****
 *****                               TextField (If the TextArea is currently editable)                           *****
 *****  saveStory(String filename) - Save the currently displayed in the TextArea story to the filename typed    *****
 *****                               in by the user to the "Filename" TextField                                  *****
 *****                clearStory() - Clear the text in the TextArea (If the TextArea is currently editable)      *****
 ***** insertLineBreak(String str) - Insert a line break after every 50 characters of the story                  *****
 *********************************************************************************************************************
 *********************************************************************************************************************/

// IMPORTS of needed tools and plug-ins
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Main extends Application {

    // CLASS VARIABLE(s) declaration(s)
    private RadioButton rdoBtnMale, rdoBtnFemale, rdoBtnOther;
    private CheckBox chkBoxStartLocation, chkBoxIntendedChar, chkBoxActualChar, chkBoxTravelMeans, chkBoxEnRouteEvent,
            chkBoxMoral;
    private TextField txtFldFileName;
    private Label lblStatus;
    private TextArea txtAreaGenStory;
    private String story = "";
    private Random generator;
    private PrintWriter printWriter;
    private Scanner scanner;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage){
        // Initialize the Random variable
        generator = new Random();

        // Create and Initialize the Labels to output the status of the program, provide instructions to select a sex
            // and to select aspects of the story to generate
        Label lblFileName = new Label("Enter filename: ");
        lblStatus = new Label("");
        Label lblSelectSex = new Label("Main character sex: ");
        Label lblStoryAspects = new Label("Select story elements: ");
        Label lblTextAreaEdit = new Label("Text area: ");
        Label lblBlank1 = new Label("");
        Label lblBlank2 = new Label("");

        // Initialize the RadioButtons to assign a sex
        rdoBtnMale = new RadioButton("Male");
        rdoBtnFemale = new RadioButton("Female");
        rdoBtnOther = new RadioButton("Other");

        // Create and Initialize the ToggleGroup for the RadioButtons dealing with assigning a sex,
            // then, assigning the corresponding RadioButtons to the ToggleGroup and Setting the "Male" RadioButton
            // to be selected as default
        ToggleGroup tGroupSex = new ToggleGroup();
        rdoBtnMale.setToggleGroup(tGroupSex);
        rdoBtnFemale.setToggleGroup(tGroupSex);
        rdoBtnOther.setToggleGroup(tGroupSex);
        rdoBtnMale.setSelected(true);

        // Create and Initialize a VBox to store the ToggleGroup of RadioButtons dealing with a person/thing's sex and
            // its Label, then add the appropriate RadioButtons and Set the Padding of the RadioButtons in the VBox
        VBox rdoBtnSexVBoxPane = new VBox();
        rdoBtnSexVBoxPane.getChildren().add(lblSelectSex);
        rdoBtnSexVBoxPane.getChildren().add(rdoBtnMale);
        rdoBtnSexVBoxPane.getChildren().add(rdoBtnFemale);
        rdoBtnSexVBoxPane.getChildren().add(rdoBtnOther);
        rdoBtnSexVBoxPane.setPadding(new Insets(10, 20, 10, 20));

        // Create and Initialize RadioButtons to Toggle TextArea as editable or not editable
        RadioButton rdoBtnEdit = new RadioButton("Editable");
        RadioButton rdoBtnNoEdit = new RadioButton("Not Editable");

        // Create and Initialize the ToggleGroup for RadioButtons dealing with whether the TextArea is Editable or not
            // and Setting the "Editable" RadioButton to be selected as default
        ToggleGroup tGroupEdit = new ToggleGroup();
        rdoBtnEdit.setToggleGroup(tGroupEdit);
        rdoBtnNoEdit.setToggleGroup(tGroupEdit);
        rdoBtnEdit.setSelected(true);

        // Create and Initialize a VBox to store the ToggleGroup of RadioButtons dealing with Setting the TextArea
            // as Editable or not and its Label and then add the appropriate RadioButtons and Set the Padding of the
            // RadioButtons in the VBox
        VBox rdoBtnEditVBoxPane = new VBox();
        rdoBtnEditVBoxPane.getChildren().add(lblTextAreaEdit);
        rdoBtnEditVBoxPane.getChildren().add(rdoBtnEdit);
        rdoBtnEditVBoxPane.getChildren().add(rdoBtnNoEdit);
        rdoBtnEditVBoxPane.setPadding(new Insets(10, 20, 10, 20));

        // Create and Initialize a HBox to store the two RadioButton VBoxes and Set the Spacing of the VBoxes inside
            // this HBox
        HBox rdoBtnGroupsHBoxPane = new HBox();
        rdoBtnGroupsHBoxPane.getChildren().add(rdoBtnSexVBoxPane);
        rdoBtnGroupsHBoxPane.getChildren().add(rdoBtnEditVBoxPane);
        rdoBtnGroupsHBoxPane.setSpacing(10);

        // Initialize CheckBoxes for the user to select parts of the story to add
        chkBoxStartLocation = new CheckBox("Location from");
        chkBoxIntendedChar = new CheckBox("Intended to meet");
        chkBoxActualChar = new CheckBox("Actually met");
        chkBoxTravelMeans = new CheckBox("Travelled by");
        chkBoxEnRouteEvent = new CheckBox("Action en route");
        chkBoxMoral = new CheckBox("Moral of story");

        // Create and Initialize a GridPane to store the CheckBoxes and its Label and Set the Padding of the GridPane
        GridPane chkBoxGridPane = new GridPane();
        chkBoxGridPane.add(lblStoryAspects, 0, 0);
        chkBoxGridPane.add(chkBoxStartLocation, 0, 1);
        chkBoxGridPane.add(chkBoxIntendedChar, 0, 2);
        chkBoxGridPane.add(chkBoxActualChar, 0, 3);
        chkBoxGridPane.add(chkBoxTravelMeans, 1, 1);
        chkBoxGridPane.add(chkBoxEnRouteEvent, 1, 2);
        chkBoxGridPane.add(chkBoxMoral, 1, 3);
        chkBoxGridPane.setPadding(new Insets(10, 20, 10, 20));

        // Create and Initialize Buttons to perform various Actions and Set their Preferred Width and Height
        Button btnNewStory = new Button("Generate");
        btnNewStory.setPrefHeight(30);
        btnNewStory.setPrefWidth(80);
        Button btnSave = new Button("Save");
        btnSave.setPrefHeight(30);
        btnSave.setPrefWidth(80);
        Button btnLoad = new Button("Load");
        btnLoad.setPrefHeight(30);
        btnLoad.setPrefWidth(80);
        Button btnClear = new Button("Clear");
        btnClear.setPrefHeight(30);
        btnClear.setPrefWidth(80);
        Button btnQuit = new Button("Quit");
        btnQuit.setPrefHeight(30);
        btnQuit.setPrefWidth(80);

        // Create and Initialize a TilePane to store the Buttons to perform various Actions and Set the HGap of
            // the Buttons in the TilePane and Set its Orientation to VERTICAL
        TilePane btnTilePane = new TilePane();
        btnTilePane.getChildren().add(btnNewStory);
        btnTilePane.getChildren().add(btnSave);
        btnTilePane.getChildren().add(btnLoad);
        btnTilePane.getChildren().add(btnClear);
        btnTilePane.getChildren().add(btnQuit);
        btnTilePane.setOrientation(Orientation.VERTICAL);
        btnTilePane.setVgap(5);

        // Initialize the TextArea that will be used to output the generated story and Set its Preferred Column
            // and Row Count
        txtAreaGenStory = new TextArea("");
        txtAreaGenStory.setPrefRowCount(6);
        txtAreaGenStory.setPrefColumnCount(26);

        // Create and Initialize a Pane to store the TextArea and Set the Padding of the Pane
        Pane txtAreaPane = new Pane();
        txtAreaPane.getChildren().add(txtAreaGenStory);
        txtAreaPane.setPadding(new Insets(10, 20, 10, 20));

        // Initialize the TextField to specify which file to load or save and Set its Preferred Column Count
        txtFldFileName = new TextField("");
        txtFldFileName.setPrefColumnCount(16);

        // Create and Initialize a VBox to store the Labels lblStatus, lblBlank1 and lblBlank2 and the TextField and
            // Set the Padding of the VBox
        VBox txtFldAndLblsVBoxPane = new VBox();
        txtFldAndLblsVBoxPane.getChildren().add(lblFileName);
        txtFldAndLblsVBoxPane.getChildren().add(txtFldFileName);
        txtFldAndLblsVBoxPane.getChildren().add(lblBlank1);
        txtFldAndLblsVBoxPane.getChildren().add(lblBlank2);
        txtFldAndLblsVBoxPane.getChildren().add(lblStatus);
        txtFldAndLblsVBoxPane.setPadding(new Insets(10, 20, 10, 20));

        // Create and Initialize a VBox for the left side of the main Pane
        VBox leftSideVBox = new VBox();
        leftSideVBox.getChildren().add(chkBoxGridPane);
        leftSideVBox.getChildren().add(txtAreaPane);

        // Create and Initialize a HBox for the Buttons and Labels and TextField
        HBox btnsAndTxtFldHBox = new HBox();
        btnsAndTxtFldHBox.getChildren().add(btnTilePane);
        btnsAndTxtFldHBox.getChildren().add(txtFldAndLblsVBoxPane);

        // Create and Initialize a VBox for the right side of the main Pane
        VBox rightSideVBox = new VBox();
        rightSideVBox.getChildren().add(rdoBtnGroupsHBoxPane);
        rightSideVBox.getChildren().add(btnsAndTxtFldHBox);

        // Create and Initialize the main Pane to add the two VBoxes to in order to add it to the Scene
            // and Set the Padding of the Pane
        HBox mainPane = new HBox();
        mainPane.getChildren().add(leftSideVBox);
        mainPane.getChildren().add(rightSideVBox);
        mainPane.setPadding(new Insets(10, 20, 10, 20));

        // Create and Initialize the Scene, Set the Title of the Stage, Set the Scene to the Stage, Show the Stage
        Scene scene = new Scene(mainPane, 700, 300);
        primaryStage.setTitle("Story Generator: Programming Assignment #4 - Ryan Huffman");
        primaryStage.setScene(scene);
        primaryStage.show();

        // EventHandler for when the RadioButton "Editable" is selected
        EventHandler<ActionEvent> rdoBtnEditHandler = e -> {
            // Set the TextArea to display the story as editable
            txtAreaGenStory.setEditable(true);

            // Clear the String text in the status Label and then Set the String Text
            lblStatus.setText("");
            lblStatus.setText("Text area editable");
        };

        // EventHandler for when the RadioButton "Not Editable" is selected
        EventHandler<ActionEvent> rdoBtnNoEditHandler = e -> {
            // Set the TextArea to display the story as not editable
            txtAreaGenStory.setEditable(false);

            // Clear the String text in the status Label and then Set the String Text
            lblStatus.setText("");
            lblStatus.setText("Text area not editable");
        };

        // Set the ActionEvent EventHandlers for the RadioButtons "Editable" and "Not Editable"
        rdoBtnEdit.setOnAction(rdoBtnEditHandler);
        rdoBtnNoEdit.setOnAction(rdoBtnNoEditHandler);

        // EventHandler for the Generate Button
        btnNewStory.setOnAction(e -> {
            generateStory();
        });

        // EventHandler for the Clear Button
        btnClear.setOnAction(e -> {
            clearStory();
        });

        // EventHandler for the Load Button
        btnLoad.setOnAction(e -> {
            loadStory(txtFldFileName.getText());
        });

        // EventHandler for the Save Button
        btnSave.setOnAction(e -> {
            saveStory(txtFldFileName.getText());
        });

        // EventHandler for the Quit Button
        btnQuit.setOnAction(e -> {
            System.exit(0);
        });
    }


    // Method used to generate a new story
    public void generateStory(){
        // Initialize the Random variable
        generator = new Random();

        // Create and Initialize the needed story String Arrays
        String[] arrayMaleNames = {"Frank Zappa", "Donald Trump", "Samuel L Jackson", "Tim", "Zorro", "Joey Votto"};
        String[] arrayFemaleNames = {"Janis Joplin", "Hillary Clinton", "Meryl Streep", "Jill", "Xena",
                "Serena Williams"};
        String[] arrayOtherNames = {"Hal 9000", "Mickey Mouse", "The Wizard of Oz", "C-3PO", "Stitch", "Johnny 5"};
        String[] arrayStartCities = {"Cincinnati", "Lexington", "Los Angeles", "New York", "Paris", "London"};
        String[] arrayDestinationArea = {"Mexico", "Arizona", "the Moon", "Australia", "Mariana Trench",
                "South Summit of Mount Everest"};
        String[] arrayIntendedChar = {"Dorothy", "Wonder Woman", "Hercules", "Paul McCartney", "E.T.", "Cheshire Cat"};
        String[] arrayActualChar = {"Bill Gates", "Steve Wozniak", "George Clooney", "Sylvester Stallone", "Neil Young",
                "Les Claypool"};
        String[] arrayIntendedDestActivity = {"climb the highest mountain", "go to the theater",
                "eat at a 5-star restaurant", "take some soil samples", "go swimming", "go snowboarding"};
        String[] arrayTravelEvent = {"got mugged", "lost luggage", "broke a leg", "got lost", "got sick",
                "got mauled by a Koala"};
        String[] arrayTravelMeans = {"by plane", "by car", "on a bus", "in a rocket", "on a boat", "on a train"};
        String[] arrayDestEvent = {"had to be wired some money", "had to buy all new clothes", "went to the hospital",
                "had to pay for an extra day", "went to the pharmacy", "got tested for rabies"};
        String[] arrayMoral = {"be careful when travelling", "sometimes the adventure is better than the destination",
                "home is where you make it", "never miss an opportunity", "believe in yourself or no one else will",
                "life is what you make it"};

        // Check if Male RadioButton is Selected and add to the story with a male name if so
        if(rdoBtnMale.isSelected()){
            story += arrayMaleNames[generator.nextInt(arrayMaleNames.length)];
        }
        // Check if Female RadioButton is Selected and add to the story with a female name if so
        else if(rdoBtnFemale.isSelected()){
            story += arrayFemaleNames[generator.nextInt(arrayFemaleNames.length)];
        }
        // Check if Other RadioButton is Selected and add to the story with a other name if so
        else if(rdoBtnOther.isSelected()){
            story += arrayOtherNames[generator.nextInt(arrayOtherNames.length)];
        }

        // Add the location the main character is travelling to
        story += " travelled to ";
        story += arrayDestinationArea[generator.nextInt(arrayDestinationArea.length)];

        // Check if the CheckBox for Starting Location is Selected and add it to the story if so
        if(chkBoxStartLocation.isSelected()){
            story += " from ";
            story += arrayStartCities[generator.nextInt(arrayStartCities.length)];
        }
        // Check if the CheckBox for Intended to meet is Selected and add it to the story if so
        if(chkBoxIntendedChar.isSelected()){
            story += " planning to meet ";
            story += arrayIntendedChar[generator.nextInt(arrayIntendedChar.length)] + " and";
        }

        // Add the planned destination activity
        story += " intending to ";
        story += arrayIntendedDestActivity[generator.nextInt(arrayIntendedDestActivity.length)];

        // Check if the CheckBox for Actually met is Selected and add it to the story if so
        if(chkBoxActualChar.isSelected()){
            story += ". Ended up meeting ";
            story += arrayActualChar[generator.nextInt(arrayActualChar.length)];
        }
        // Check if the CheckBox for Travelled by is Selected and add it to the story if so
        if(chkBoxTravelMeans.isSelected()){
            story += ", having had travelled ";
            story += arrayTravelMeans[generator.nextInt(arrayTravelMeans.length)];
        }
        // Check if the CheckBox for En route action is Selected and add it to the story if so
        if(chkBoxEnRouteEvent.isSelected()){
            story += " but en route ";
            story += arrayTravelEvent[generator.nextInt(arrayTravelEvent.length)];
        }

        // Add to the story the actual event that happened at the destination
        story += ", but at the destination ";
        story += arrayDestEvent[generator.nextInt(arrayDestEvent.length)] + ".";

        // Check if the CheckBox for Moral of story is Selected and add it to the story if so
        if(chkBoxMoral.isSelected()){
            story += " So let the moral of the story be known .... ";
            story += arrayMoral[generator.nextInt(arrayMoral.length)] + ".";
        }

        // Check if the TextArea is Editable before posting the generated story to the TextArea
        if(txtAreaGenStory.isEditable()){
            // Send the String variable story to the insertLineBreak method to add line breaks
            story = insertLineBreak(story);

            // Set the String text of the TextArea to the String variable story
            txtAreaGenStory.setText(story);

            // Clear the String text in the status Label, then Set the String text
            lblStatus.setText("");
            lblStatus.setText("Story created");
        }
        // else block to execute if the TextArea is currently not editable
        else{
            // Clear the String text in the status Label, then Set the String text
            lblStatus.setText("");
            lblStatus.setText("Create failure: TextArea not editable");
        }

        // Clear the String variable story for the next use
        story = "";
    }


    // Method used to Load a story in from a File
    public void loadStory(String filename){
        // Clear the TextArea
        clearStory();

        // Try block to attempt to load the File with the filename passed in as a parameter to the
            // loadStory(String filename) method
        try{
            // if statement block to test that a String filename was passed into the method
            if(filename.length() == 0){
                throw new Exception("No filename entered");
            }
            // else statement block that executes if a filename was passed into the method
            else {
                // Initialize the Scanner with the File with filename passed in as a parameter
                scanner = new Scanner(new File(filename));

                // if statement block to check that the File whose name was passed in as a parameter has some String text
                if (scanner.hasNext()) {

                    // While loop to read all the text in the File
                    while (scanner.hasNext()) {
                        // Read the current String or char and concatenate it onto the story String variable
                        story += scanner.next();
                        story += " ";
                    }

                    // Add a line break every 50 characters
                    story = insertLineBreak(story);

                    if(story.length() == 0){
                        throw new Exception("Error when trying to load \"" + filename + "\"");
                    }

                    // if statement block to check if the TextArea is currently editable or not
                    if(txtAreaGenStory.isEditable()){
                        // Clear all the String text in the TextArea and then Set the String text to that of the File
                            // whose name was passed in as a parameter
                        txtAreaGenStory.setText("");
                        txtAreaGenStory.setText(story);

                        // Clear the String text in the status Label, then Set the String text
                        lblStatus.setText("");
                        lblStatus.setText("Story loaded");

                        // Clear the String text filename from the TextField, to prevent accidentally loading that file
                            // the next time the user wants to load a file
                        txtFldFileName.setText("");
                    }
                    // else block to execute if the TextArea is not editable
                    else {
                        // Clear the String text in the status Label, then Set the String text
                        lblStatus.setText("");
                        lblStatus.setText("Load failure: TextArea not editable");
                    }
                }
            }
        }
        // Catch block to handle IOException
        catch(IOException iOExc){
            // Clear the status Label and then display the IOException in the status Label
            lblStatus.setText("");
            lblStatus.setText(String.valueOf(iOExc));
        }
        // Catch block to handle any other Exceptions
        catch(Exception exc){
            // Clear the String text of the status Label and then Set the String text of the status Label to the
                // Exception that was triggered
            lblStatus.setText("");
            lblStatus.setText(String.valueOf(exc));
        }
        // Finally block to close the Scanner if it is not Null
        finally{
            // if statement block to check if the Scanner is equal to Null
            if(scanner != null){
                scanner.close();
            }
        }
    }


    // Method to save the story to a file
    public void saveStory(String filename){
        // Clear the String variable story in case there is anything left over in it
        story = "";

        // Try block to attempt to write to the File whose filename was passed in as a parameter
        try{
            // if statement block to test that a String filename was passed into the method
            if(filename.length() == 0){
                throw new Exception("No filename entered");
            }
            // else statement block that executes if a filename was passed into the method
            else{
                // Initialize the PrintWriter
                printWriter = new PrintWriter(filename);

                // Get the String text from the TextArea and store it in the String variable story
                story = txtAreaGenStory.getText();

                if(story.length() == 0){
                    throw new Exception("No text to store in the file \"" + filename + "\"");
                }

                // Write the story to the File with the filename that was passed in as a parameter using the
                    // PrintWriter
                printWriter.println(story);

                // Clear the String text in the status Label, then Set the String text
                lblStatus.setText("");
                lblStatus.setText("Story saved");

                // Clear the String text filename from the TextField, to prevent accidentally overwriting that file
                txtFldFileName.setText("");
            }
        }
        // Catch block to handle IOException
        catch(IOException iOExc){
            // Clear the status Label and then display the IOException in the status Label
            lblStatus.setText("");
            lblStatus.setText(String.valueOf(iOExc));
        }
        // Catch block to handle any other Exceptions
        catch(Exception exc){
            // Clear the String text of the status Label and then Set the String text of the status Label to the
                // Exception that was triggered
            lblStatus.setText("");
            lblStatus.setText(String.valueOf(exc));
        }
        // Finally block used to close the PrintWriter if it is not Null
        finally {
            // if statement block used to check that the PrintWriter File is not Null and Close it if it is not Null
            if(printWriter != null){
                printWriter.close();
            }
        }
    }


    // Method to clear the TextArea of the story and any other text
    public void clearStory(){
        // Clear the String text out of the String variable story
        story = "";

        // if statement block to check if the TextArea is currently editable or not
        if(txtAreaGenStory.isEditable()) {
            // if statement block that checks if the TextArea is currently empty or contains no String text
            if (txtAreaGenStory.getText().length() == 0) {
                // Clear all the String text in the status Label and then Set the String text of the status Label as below
                lblStatus.setText("");
                lblStatus.setText("No story to clear");
            }
            // else statement block to execute if the TextArea contains String text
            else {
                // Clear all the String text in the TextArea
                txtAreaGenStory.setText("");

                // Clear all the String text in the status Label and then Set the String text of the status Label as below
                lblStatus.setText("");
                lblStatus.setText("Story cleared");
            }
        }
        // else statement to execute if the TextArea is currently not editable
        else {
            // Clear all the String text in the status Label and then Set the String text of the status Label as below
            lblStatus.setText("");
            lblStatus.setText("Clear failure: TextArea not editable");
        }
    }


    // Method to insert a line break after every 50 characters
    public String insertLineBreak(String str){
        // if statement block used to check that the current parsed String is less than 50 Characters long and
            // if so, return the String with a line break at the end of it
        if(str.length() < 50) {
            return str + "\n";
        }

        // LOCAL VARIABLE(s) declaration(s)
        final int OFF_SET = 49;
        int start = OFF_SET;
        int currentPosition = 0;
        StringBuilder result = new StringBuilder();

        // while loop used to go through the entire String
        while(start <= str.length()) {
            int index = str.indexOf(" ", start);
            if(index != -1) {
                result.append(str.substring(currentPosition, index))
                        .append("\n");
                index++;
                start = index;
                currentPosition = index;
                start += OFF_SET;
            } else {
                start++;
            }
        }

        result.append(str.substring(currentPosition))
                .append("\n");

        // Return the parsed String that has had line breaks inserted into the String
        return result.toString();
    }
}
