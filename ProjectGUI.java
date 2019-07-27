package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ProjectGUI extends Application implements Serializable {

	public static LinkedList<Department> open(String path) {

		LinkedList<Department> dpt = new LinkedList<>();

		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
			dpt = (LinkedList<Department>) in.readObject();
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dpt;
	}

	private static final String PATH = "Dept.dat";

	static final Comparator<Student> ID_ORDER = new Comparator<Student>() {

		@Override
		public int compare(Student stu1, Student stu2) {

			int ID1 = stu1.getId();
			int ID2 = stu2.getId();

			if (ID1 == ID2)
				return 0;
			else if (ID1 >= ID2)
				return 1;
			else
				return -1;
		}

	};

	static final Comparator<Student> GPA_ORDER = new Comparator<Student>() {

		@Override
		public int compare(Student stu1, Student stu2) {

			double GPA1 = stu1.getGPA();
			double GPA2 = stu2.getGPA();

			if (GPA1 == GPA2)
				return 0;
			else if (GPA1 >= GPA2)
				return 1;
			else
				return -1;
		}
	};

	static final Comparator<Student> NAME_ORDER = new Comparator<Student>() {

		@Override
		public int compare(Student stu1, Student stu2) {

			String name1 = stu1.getName();
			String name2 = stu2.getName();

			return name1.compareTo(name2);
		}

	};

	public static void orderStudents(LinkedList<Department> dpt) {

		GridPane actionWindow = new GridPane();

		actionWindow.setVgap(5);
		actionWindow.setHgap(5);

		Stage secondaryStage = new Stage();

		final ChoiceBox<Department> departments = new ChoiceBox<Department>(FXCollections.observableList(dpt));
		departments.setValue(dpt.get(0));
		actionWindow.add(departments, 0, 0);

		String[] ordOpt = new String[3];

		ordOpt[0] = "Order students by ID";
		ordOpt[1] = "Order students by name";
		ordOpt[2] = "Order students by GPA";

		final ChoiceBox<String> setOrder = new ChoiceBox<String>(FXCollections.observableArrayList(ordOpt));
		actionWindow.add(setOrder, 0, 1);

		Button back = new Button("Back");
		actionWindow.add(back, 3, 4);

		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				secondaryStage.hide();

			}
		});

		LinkedList<Student> students = (LinkedList<Student>) dpt.get(departments.getSelectionModel().getSelectedIndex()).getStudents();

		Button print = new Button("Print");
		actionWindow.add(print, 2, 4);

		print.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				GridPane result = new GridPane();

				result.setVgap(5);
				result.setHgap(5);

				if (departments.getSelectionModel().getSelectedIndex() == 0) {
					listStudents(result, students, ID_ORDER);

				} else if (departments.getSelectionModel().getSelectedIndex() == 1) {
					listStudents(result, students, NAME_ORDER);

				} else if (departments.getSelectionModel().getSelectedIndex() == 2) {
					listStudents(result, students, GPA_ORDER);

				} else
					actionWindow.add(new Label("Please select an order option"), 3, 5);
			}
		});

		Scene popUp = new Scene(actionWindow, 400, 150);

		secondaryStage.setScene(popUp);
		secondaryStage.show();
	}

	public static void listStudents(GridPane result, LinkedList<Student> students, Comparator<Student> order) {

		Collections.sort(students, order);
		result.add(new Label(students.toString()), 0, 0);

		Scene resultWindow = new Scene(result, 800, 500);

		Stage print = new Stage();
		print.setScene(resultWindow);
		print.show();
	}

	public static void listDepartments(LinkedList<Department> dpt) { // Add students then test method | implemented serializable but haven't tested

		Stage displayWindow = new Stage();

		VBox display = new VBox();
        Button back=new Button("Back");

		
		Label[] numbersOfStu = new Label[dpt.size()];

		for (int i = 0; i < dpt.size(); i++) {
			numbersOfStu[i] = new Label();
			numbersOfStu[i].setText(dpt.get(i).getName() + " " + dpt.get(i).getStudents().size());
			display.getChildren().add(numbersOfStu[i]);

		}
		display.getChildren().add(back);
		back.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				displayWindow.close();

			}
		});

		Scene displayScene = new Scene(display, 500,500);

		displayWindow.setScene(displayScene);
		displayWindow.show();
	}

	public static void addStudent(LinkedList<Department> dpt) {
		ChoiceBox<Department> departments = 
			new ChoiceBox<Department>(FXCollections.observableList(dpt));
		Stage secondaryStage = new Stage();

		GridPane actionWindow = new GridPane();
        RadioButton under=new RadioButton("Undergraduate");
        RadioButton grad=new RadioButton("graduate");
        ToggleGroup btns=new ToggleGroup();
        under.setToggleGroup(btns);
        grad.setToggleGroup(btns);
		actionWindow.setVgap(5);
		actionWindow.setHgap(5);
        actionWindow.add(departments, 3,0);
        actionWindow.add(under,2, 0);
        actionWindow.add(grad,2, 1);
		actionWindow.add(new Label("Enter the student's name: "), 0, 0);
		actionWindow.add(new Label("Enter the student's ID: "), 0, 1);

		TextField name = new TextField();
		TextField ID = new TextField();

		actionWindow.add(name, 1, 0);
		actionWindow.add(ID, 1, 1);

		Button add = new Button("Add");
		Button back = new Button("Back");
		actionWindow.add(add, 0, 2);
		actionWindow.add(back, 0, 3);
		back.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				secondaryStage.close();

			}
		});
       
		add.setOnAction(new EventHandler<ActionEvent>() {
            Label errorName=new Label("Please enter a name");
            Label errorID=new Label("Please enter an ID");
			@Override
		
			public void handle(ActionEvent arg0) {
				if(name.getText().trim().isEmpty()) {
					actionWindow.add(errorName, 0,4);
				}
				else if(ID.getText().trim().isEmpty()) {
					actionWindow.add(errorID, 0, 4);
				}
				else {
					String stuName=name.getText();
					int stuID= Integer.parseInt(ID.getText());
					
					int deptIndex=departments.getSelectionModel().getSelectedIndex();
					
					
					if(grad.isSelected()) {
						dpt.get(deptIndex).addStudent(new Graduate(stuName,stuID,0));
						actionWindow.add(new Label("it has been added"), 0,5);
						
					}
					if(under.isSelected()) {
						dpt.get(deptIndex).addStudent(new Undergraduate(stuName,stuID,0));
						actionWindow.add(new Label("it has been added"), 0,5);
						
					}
					
				}

			}
			

		});

		Scene studentAdd = new Scene(actionWindow, 750, 300);

		secondaryStage.setScene(studentAdd);
		secondaryStage.show();

	}

	public static void deleteStudent(LinkedList<Department> dpt) {
		ChoiceBox<Department> departments = 
				new ChoiceBox<Department>(FXCollections.observableList(dpt));
		
		Stage secondaryStage = new Stage();

		VBox actionWindow = new VBox();
		actionWindow.getChildren().add(departments);
		
		
		Button sub=new Button("submit");
		actionWindow.getChildren().add(sub);
		sub.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				int deptIndex=departments.getSelectionModel().getSelectedIndex();
				
		ChoiceBox<Student> stu =
				new ChoiceBox<Student>(FXCollections.observableList(dpt.get(deptIndex).getStudents()));
		
			actionWindow.getChildren().add(stu);
			
			
		
			Button removing=new Button("remove");
			actionWindow.getChildren().add(removing);
			removing.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					int stuIndex=stu.getSelectionModel().getSelectedIndex();
					dpt.get(deptIndex).getStudents().remove(stuIndex);
					actionWindow.getChildren().add(new Label("it has been removed"));
				}
				
			});
			
			}
		});
	
		Button back=new Button("back");
		actionWindow.getChildren().add(back);
		back.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				secondaryStage.close();

			}
		});
		Scene popUp=new Scene(actionWindow,500,500);
		secondaryStage.setScene(popUp);
		secondaryStage.show();
		
	}
	
	public static LinkedList<Department> retrieveData() { // Refuses to read data

		LinkedList<Department> dpt = null;

		try {
			ObjectInputStream readObj = new ObjectInputStream(new FileInputStream("departments.dat"));
			dpt = (LinkedList<Department>) readObj.readObject();
			readObj.close();

		} catch (FileNotFoundException fe) {

			dpt = new LinkedList<Department>();
			try {
				ObjectOutputStream objPrint = new ObjectOutputStream(new FileOutputStream("departments.dat"));
				objPrint.writeObject(dpt);
				objPrint.close();

			} catch (FileNotFoundException fe2) {
			} catch (IOException ie) {
			}
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
		}

		return dpt;
	}

	public static void printData(LinkedList<Department> dpt) {

		try {
			ObjectOutputStream objReader =
					new ObjectOutputStream(new FileOutputStream(PATH));
			objReader.writeObject(dpt);
			objReader.close();
		} catch (FileNotFoundException createFile) {
			System.out.println("File was not found, creating file with default values");
		} catch (IOException e) {
		}
	}
    
	public static void changeDept(LinkedList<Department> dpt) {
		ChoiceBox<Department> departmentsOut = 
				new ChoiceBox<Department>(FXCollections.observableList(dpt));
		ChoiceBox<Department> departmentsIn = 
				new ChoiceBox<Department>(FXCollections.observableList(dpt));
		
		Stage secondaryStage = new Stage();

		VBox actionWindow = new VBox();
		actionWindow.getChildren().add(new Label("select department"));
		actionWindow.getChildren().add(departmentsOut);
		
		
		Button sub=new Button("submit");
		actionWindow.getChildren().add(sub);
		sub.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				int deptOutIndex=departmentsOut.getSelectionModel().getSelectedIndex();
				
		ChoiceBox<Student> stu =
				new ChoiceBox<Student>(FXCollections.observableList(dpt.get(deptOutIndex).getStudents()));
		actionWindow.getChildren().add(new Label("select Student"));
			actionWindow.getChildren().add(stu);
			actionWindow.getChildren().add(new Label("select new department"));
			actionWindow.getChildren().add(departmentsIn);
			
		
			Button change=new Button("change");
			actionWindow.getChildren().add(change);
			change.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					int deptInIndex=departmentsIn.getSelectionModel().getSelectedIndex();
					int stuIndex=stu.getSelectionModel().getSelectedIndex();
					
					
				dpt.get(deptInIndex).getStudents().add(dpt.get(deptOutIndex).getStudents().get(stuIndex));
				dpt.get(deptOutIndex).getStudents().remove(stuIndex);
					actionWindow.getChildren().add(new Label("changed has been completed"));
				}
				
			});
			
			}
		});
	
		Button back=new Button("back");
		actionWindow.getChildren().add(back);
		back.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				secondaryStage.close();

			}
		});
		Scene popUp=new Scene(actionWindow,500,500);
		secondaryStage.setScene(popUp);
		secondaryStage.show();
	}
	@Override
	public void start(Stage primaryStage) {

		// LinkedList<Department> dpt = retrieveData();
		LinkedList<Department> dpt =open(PATH);
		GridPane mainWindow = new GridPane();

		mainWindow.setVgap(5);
		mainWindow.setHgap(5);

		ToggleGroup choices = new ToggleGroup();
		RadioButton[] options = new RadioButton[5];

		options[0] = new RadioButton("List department students");
		options[1] = new RadioButton("List all departments and number of students");
		options[2] = new RadioButton("Add new student to a department");
		options[3] = new RadioButton("Delete a student from a department");
		options[4] = new RadioButton("Change the department of a student");

		for (int i = 0; i < options.length; i++) {
			options[i].setToggleGroup(choices);
			mainWindow.add(options[i], 0, i);
		}

		Button submit = new Button("Submit");
		Button save = new Button("Save");
		Button exit = new Button("Exit");
		mainWindow.add(submit, 2, 5);
		mainWindow.add(save, 3, 5);
		mainWindow.add(exit, 4, 5);

		submit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (options[0].isSelected()) {

					orderStudents(dpt);

				} else if (options[1].isSelected()) {

					listDepartments(dpt);

				} else if (options[2].isSelected()) {

					addStudent(dpt);

				} else if (options[3].isSelected()) {
					deleteStudent(dpt);
				} else if (options[4].isSelected()) {
					changeDept( dpt);
				} else {
					Label message = new Label("You have not chosen a valid option!");
					mainWindow.add(message, 5, 5);
				}

			}
		});

		save.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				printData(dpt);
				
			}
		});

		exit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				primaryStage.close();

			}
		});

		Scene display = new Scene(mainWindow, 600, 200);

		primaryStage.setScene(display);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}