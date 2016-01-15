import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The class with the main method for the running of the database 
 * management program.
 * 
 * @author Jerry Liu
 * @version 1.1 2015-03-09
 */
public class TutoringDBMS
{
    /*
     * class fields
     */
    private static final String[] EXCLUDE =
        {"MR. ", "MR.", "MRS. ", "MRS.", "MS. ", "MS."};
    private static final int SELECTION_MINIMUM = 1;
    private static final int SELECTION_INITIAL_MAXIMUM = 3;
    
    private static final int SELECTION_DAY_MAXIMUM = 6;
    
    private static final int SELECTION_REVIEW = 1;
    private static final int SELECTION_TUTOR = 2;
    private static final int SELECTION_TUTEE = 3;
    private static final int SELECTION_DELETE_TUTOR = 4;
    private static final int SELECTION_DELETE_TUTEE = 5;
    private static final int SELECTION_ADD_MATCH = 6;
    private static final int SELECTION_DELETE_MATCH = 7;
    private static final int SELECTION_SAVE = 8;
    private static final int SELECTION_COORDINATOR_MAXIMUM = 9;
    
    private static final int STUDENT_MINIMUM = 300000000;
    private static final int STUDENT_MAXIMUM = 399999999;
    
    private static final int LENGTH = 30;
    private static final int TWO = 2;
    
    private static final int DAY_MONDAY = 1;
    private static final int DAY_TUESDAY = 2;
    private static final int DAY_WEDNESDAY = 3;
    private static final int DAY_THURSDAY = 4;
    private static final int DAY_FRIDAY = 5;
    
    
    private static final int FIVE = 5;
    
    private static final int HUNDRED_LINES = 100;
    
    public static final int SHOW = 20;
    
    private static final String BAR = 
        "------------------------------------------------------------";
    
    private static String password = "123456789";
    private static Scanner scanner = new Scanner(System.in);
    private static Console console;
    private static String input = "";
    
    public static Student tutorHead;
    public static Student tuteeHead;
    public static Match matchHead;
    
    private static Student tutorDayHead;
    private static Student tuteeDayHead;
    private static Match matchDayHead;
    
    private static boolean[] entered;
    private static boolean daySelected = false;
    private static int day = 0;

    public static void main(String[] argument)
    {
        console = System.console();
        passwordPrompt();
        
        initialMenu();       
    } // end of method main(String[] argument)
    
    private static void passwordPrompt()
    {
        
        char[] inputPassword;
        do
        {
            System.out.print("Password: ");
            input = scanner.nextLine();
        }
        while (!input.equals(password));
        cls();
        readAll();
    } // end of method passwordPrompt()
    
    private static void initialMenu()
    {
        int selection = 0;
        do
        {
            // menu            
            System.out.println(BAR);
            System.out.println("1. Session");
            System.out.println("2. Coordinator Menu");
            System.out.println("3. Exit");
            
            // validation of selection
            do
            {
                System.out.print("Enter selection: ");
                
                // guarding against invalid input
                try
                {
                    selection = Integer.parseInt(scanner.nextLine());
                }
                catch (NumberFormatException error)
                {
                    selection = 0;
                } // end of try-catch block
            }
            while (selection < SELECTION_MINIMUM 
				|| selection > SELECTION_INITIAL_MAXIMUM);
            
            // different selections
            if (selection == SELECTION_MINIMUM)
            {
                if (!daySelected) daySelect();
                else blankPrompt();                
            } // end of if (selection == SELECTION_MINIMUM)
            if (selection == TWO)
            {
                coordinatorMenu();
            } // end of if (selection == TWO)       
        }
        while (!(selection == SELECTION_INITIAL_MAXIMUM));
    } // end of method initialMenu()
    
    private static void daySelect()
    {
        int selection = 0;
        // menu
        System.out.println(BAR);
        System.out.println("1. Monday");
        System.out.println("2. Tuesday");
        System.out.println("3. Wednesday");
        System.out.println("4. Thursday");
        System.out.println("5. Friday");
        System.out.println("6. Back");
        // input and validation
        do
        {
            System.out.print("Enter selection: ");
            input = scanner.nextLine();
            try
            {
                selection = Integer.parseInt(input);
            }
            catch (NumberFormatException error)
            {
                error.printStackTrace();
                selection = 0;
            } // end of try-catch block
        }
        while (selection < SELECTION_MINIMUM 
			|| selection > SELECTION_DAY_MAXIMUM);
        // if none selected, go back
        if (selection == SELECTION_DAY_MAXIMUM) return;
        daySelected = true;
        day = selection;
        // else move on
        readAttendance();
    } // end of method daySelect()
 
    private static void readAll()
    {
        // tutors
        try
        {
            BufferedReader reader 
				= new BufferedReader(new FileReader(Student.fileTutor));
            boolean first = true;
            Student current = tutorHead;
            
            String input = "";
            while ((input = reader.readLine()) != null)
            {
                if (first)
                {
                    tutorHead = new Student(input);
                    first = false;
                    current = tutorHead;
                    
                }
                else
                {
                    current.add(new Student(input));
                    current = current.getNext();
                } // end of if (first)
            } // end of while ((input = matchReader.readLine()) != null)
            
            reader.close();
        }
        catch (IOException error)
        {
            error.printStackTrace();
        }
        catch (NumberFormatException error1)
        {
            error1.printStackTrace();
        } // end of try-catch block
        
        // tutees
        try
        {
            BufferedReader reader 
				= new BufferedReader(new FileReader(Student.fileTutee));
            boolean first = true;
            Student current = tuteeHead;
            
            String input = "";
            while ((input = reader.readLine()) != null)
            {
                if (first)
                {
                    tuteeHead = new Student(input);
                    first = false;
                    current = tuteeHead;
                }
                else
                {
                    current.add(new Student(input));
                    current = current.getNext();
                } // end of if (first)
            } // end of while ((input = matchReader.readLine()) != null)
            
            reader.close();
        }
        catch (IOException error)
        {
            error.printStackTrace();
        }
        catch (NumberFormatException error1)
        {
            error1.printStackTrace();
        } // end of try-catch block
        
        // matches
        try
        {
            BufferedReader matchReader 
				= new BufferedReader(new FileReader(Match.fileMatch));
            boolean first = true;
            Match current = matchHead;
            
            String input = "";
            while ((input = matchReader.readLine()) != null)
            {
                if (first)
                {
                    matchHead = new Match(input);
                    first = false;
                    current = matchHead;
                }
                else
                {
                    current.add(new Match(input));
                    current = current.getNext();
                } // end of if (first)
            } // end of while ((input = matchReader.readLine()) != null)
            
            matchReader.close();
        }
        catch (IOException error)
        {
            error.printStackTrace();
        }
        catch (NumberFormatException error1)
        {
            error1.printStackTrace();
        } // end of try-catch block       
    } // end of method readAll()
    
    private static void readAttendance()
    {
        // read into new linked list the matches/tutor/tutees for the day
        boolean first = true;
        matchDayHead = new Match();
        tutorDayHead = new Student();
        tuteeDayHead = new Student();
        Match currentMatch = matchHead;
        
        int index = 1;
        while (currentMatch != null)
        {
            // only if meeting on the day selected
            if (currentMatch.getMeetDays()[day - 1] == '1')
            {
                // if the first match
                if (first)
                {
                    // tutor
                    tutorDayHead.copyData(currentMatch.getTutor());
                    // tutee
                    tuteeDayHead.copyData(currentMatch.getTutee());
                    // match
                    matchDayHead.copyData(currentMatch);
                    first = false;
                }
                // not the first match
                else
                {
                    tutorDayHead.nextData(currentMatch.getTutor());
                    tuteeDayHead.nextData(currentMatch.getTutee());
                    matchDayHead.nextData(currentMatch);
                } // end of if (first)     
            } // end of if (currentMatch.getMeetDays()[day - 1] == '1')
            currentMatch = currentMatch.getNext();
            index++;
        } // end of while (currentMatch != null)
        blankPrompt();
    } // end of  method readAttendance()

    private static void blankPrompt()
    {
        String input = "";
        System.out.println(BAR);
        do
        {
            // prompt for student number
            System.out.print("Student Number: ");
            input = scanner.nextLine();
            try
            {                
                // if the password is not entered (and thus a student number
                // is entered)
                int number = Integer.parseInt(input);
                
                if (!input.equals(password))
                {
                    boolean found = false;
                    
                    // check if already signed in that day                                                               
                    try
                    {
                        // if already signed in (check tutors)...   
                        if (Student.find(tutorHead, number).getChecked())
                        {
                            System.out.println("You have already signed in!\n");
                        }
                        // not signed in
                        else
                        {
                            Student.find(tutorHead, number).setChecked(true);
                            Student.find(tutorHead, number)
								.increaseAttendance();
                            // welcome message
                            System.out.println("Welcome, " 
                                + Student.find(tutorHead, number).getFirstName() 
								+ "\n");
                        } // end of if (Student.find(tutorHead, number)...)
                    }
                    catch (NullPointerException error)
                    {
                        // checking tutees
                        try
                        {
                            // signed in...
                            if (Student.find(tuteeHead, number).getChecked())
                            {
                                System.out.println
									("You have already signed in!\n");
                            }
                            // not signed in
                            else
                            {
                                Student.find(tuteeHead, number)
									.setChecked(true);
                                Student.find(tuteeHead, number)
									.increaseAttendance();
                                // welcome message
                                System.out.println("Welcome, " 
                                    + Student.find(tuteeHead, number)
										.getFirstName() + "\n");
                            } // end of if (Student.find(tuteeHead, number)...)
                        }
                        catch (NullPointerException error1)
                        {
                            // if not in tutor or tutee list for the day, then invalid
                            System.out.println("Not recognized.\n");
                        } // end of try-catch block
                    } // end of try-catch block        
                } // end of if (!input.equals(password))
            }
            catch (NumberFormatException error)
            {} // end of try-catch block            
        }
        while (!(input.equals(password)));
        cls();
    } // end of method blankPrompt()
    
    private static void coordinatorMenu()
    {
        boolean exit = false;
        do
        {
            // menu
            System.out.println(BAR);
            System.out.println("1. Review current session");
            System.out.println("2. New tutor");
            System.out.println("3. New tutee");
            System.out.println("4. View all tutors / Delete tutor");
            System.out.println("5. View all tutees / Delete tutee");
            System.out.println("6. Add a match");
            System.out.println("7. View all matches / Delete a match");
            System.out.println("8. Save changes");
            System.out.println("9. Back");
            
            boolean valid = false;
            int selection = 0;
            // prompt until proper input
            do
            {
                System.out.print("Enter selection: ");
                input = scanner.nextLine();
                try
                {
                    selection = Integer.parseInt(input);
                }
                catch (NumberFormatException error)
                {} // end of try-catch block
                
                if (!(selection < SELECTION_MINIMUM 
					|| selection > SELECTION_COORDINATOR_MAXIMUM))
                {
                    valid = true;
                } // end of if (!(selection < SELECTION_MINIMUM...)
            }
            while (!valid);
            
            // all of the selections
            if (selection == SELECTION_REVIEW)
            {
                review();
            } // end of if (selection == SELECTION_REVIEW)
            if (selection == SELECTION_TUTOR)
            {
                newTutor();
            } // end of if (selection == SELECTION_TUTOR)
            if (selection == SELECTION_TUTEE)
            {
                newTutee();
            } // end of if (selection == SELECTION_TUTEE)
            if (selection == SELECTION_DELETE_TUTOR)
            {
                deleteTutor();
            } // end of if (selection == SELECTION_DELETE_TUTOR)
            if (selection == SELECTION_DELETE_TUTEE)
            {
                deleteTutee();
            } // end of if (selection == SELECTION_DELETE_TUTEE)
            if (selection == SELECTION_ADD_MATCH)
            {
                addMatch();
            } // end of if (selection == SELECTION_ADD_MATCH)
            if (selection == SELECTION_DELETE_MATCH)
            {
                deleteMatch();
            } // end of if (selection == SELECTION_DELETE_MATCH)
            if (selection == SELECTION_SAVE)
            {
                save();
            } // end of if (selection == SELECTION_DELETE_MATCH)
            if (selection == SELECTION_COORDINATOR_MAXIMUM)
            {
                exit = true;
            } // end of if (selection == SELECTION_COORDINATOR_MAXIMUM)
        }
        while (!exit);
    } // end of method coordinatorMenu()
    
    private static void review()
    {
        // if the day of the week has not been selected yet
        if (!daySelected)
        {
            System.out.println("\nSession not started.");
            System.out.print("ENTER to continue: ");
            scanner.nextLine();
            return;
        } // end of if (!daySelected)
        
        // otherwise, review the session
        int length = LENGTH;
        System.out.println(BAR);
        // day
        System.out.print("Day: ");
        if (day == DAY_MONDAY)
            System.out.println("MONDAY");
        if (day == DAY_TUESDAY)
            System.out.println("TUESDAY");
        if (day == DAY_WEDNESDAY)
            System.out.println("WEDNESDAY");
        if (day == DAY_THURSDAY)
            System.out.println("THURSDAY");
        if (day == DAY_FRIDAY)
            System.out.println("FRIDAY");
        // headers for tutor, tutee, courses
        System.out.print("TUTOR");
        for (int i = 0; i < length - 5; i++)
        {
            System.out.print(" ");
        } // end of for (int i = 0; i < length - 5; i++) 
        System.out.print("TUTEE");
        for (int i = 0; i < length - 5; i++)
        {
            System.out.print(" ");
        } // end of for (int i = 0; i < length - 5; i++)
        System.out.println("COURSE(S)");
        
        // print information for each match
        Match current = matchDayHead;
        while (current != null)
        {            
            String output = "";
            // print tutor
            // if not present
            if (!current.getTutor().getChecked())
                output = "!";
            output += current.getTutor().getFirstName() + " " 
                + current.getTutor().getLastName();
            int initialLength = output.length();
            for (int j = 0; j < length - initialLength; j++)
            {
                output += " ";
            } // end of for (int j = 0; j < length - initialLength; j++)
            System.out.print(output);
            
            // print tutee
            output = "";
            // if not present
            if (!current.getTutee().getChecked())
                output = "!";
            output += current.getTutee().getFirstName() + " "
                + current.getTutee().getLastName();
            initialLength = output.length();
            for (int j = 0; j < length - initialLength; j++)
            {
                output += " ";
            } // end of for (int j = 0; j < length - initialLength; j++)
            System.out.print(output);
            
            // print courses
            System.out.println(current.getCourses());
            
            current = current.getNext();
        } // end of while (current != null)
        System.out.print("ENTER to continue: ");
        scanner.nextLine();
    } // end of method review()
    
    private static void newTutor()
    {
        boolean exit = false;
        
        Student student = new Student();
        student.setTutor(1);
        System.out.println(BAR);
        // prompt for all properties
        int number = 0;
        do
        {
            System.out.print("Student number: ");
            input = scanner.nextLine();
            
            try 
            {
                number = Integer.parseInt(input);
            }
            catch (NumberFormatException error)
            {} // end of try-catch block
        }
        while (number < STUDENT_MINIMUM || number > STUDENT_MAXIMUM);
        // check if already registered
        if (Student.find(tutorHead, number) != null)
        {
            System.out.println("\nAlready registered.");
            System.out.print("ENTER to continue: ");
            scanner.nextLine();
            return;
        } // end of if (Student.find(tutorHead, number) == null)

        student.setStudentNumber(number);
        System.out.print("First name: ");
        student.setFirstName(scanner.nextLine().toUpperCase());
        System.out.print("Last name: ");
        student.setLastName(scanner.nextLine().toUpperCase());
        char gender = 'a';
        do
        {
            exit = false;
            System.out.print("Gender: ");
            input = scanner.nextLine();
            if (input.toUpperCase().charAt(0) == 'M' 
				|| input.toUpperCase().charAt(0) == 'F')
            {
                gender = input.toUpperCase().charAt(0);
                exit = true;
            } // end of if (input.toUpperCase().charAt(0) == 'M'...)
            
        }
        while (!exit);
        student.setGender(gender);
        
        System.out.print("Homeroom Teacher: ");
        input = scanner.nextLine().toUpperCase();
        // exclude titles from teacher name
        for (int i = 0; i < EXCLUDE.length; i++)
        {
            input.replace(EXCLUDE[i], "");
        } // end of for (int i = 0; i < EXCLUDE.length; i++)
        student.setHomeroom(input);
        System.out.print("Email: ");
        student.setEmail(scanner.nextLine().toUpperCase());
        System.out.print("Grade: ");
        try
        {
            student.setGrade(Integer.parseInt(scanner.nextLine()));
        }
        catch (NumberFormatException error)
        {} // end of try-catch block
        System.out.print("Days free (eg. 10100 for Mon. and Wed. free): ");
        student.setDaysFree(scanner.nextLine().toCharArray());
        System.out.print("Courses to tutor (eg. FSF3,ENG4 - no spaces): ");
        student.setCourses(scanner.nextLine());
        System.out.print("Type 1 to create, else to discard: ");
        if (scanner.nextLine().equals("1"))
        {
            // add to head of linked list
            student.add(tutorHead);
            tutorHead = student;
            System.out.println("\nSuccessfully created.");
            System.out.print("ENTER to continue: ");
            scanner.nextLine();
        } // end of if (scanner.nextLine().equals("1"))
        else
        {
            System.out.println("\nCancelled.");
            System.out.print("ENTER to continue: ");
            scanner.nextLine();
        } // end of if (scanner.nextLine().equals("1"))
    } // end of method newTutor()
    
    private static void newTutee()
    {
        boolean exit = false;
        
        Student student = new Student();
        student.setTutor(0);
        System.out.println(BAR);
        // prompt for all properties
        int number = 0;
        do
        {
            System.out.print("Student number: ");
            input = scanner.nextLine();
            
            try 
            {
                number = Integer.parseInt(input);
            }
            catch (NumberFormatException error)
            {} // end of try-catch block
        }
        while (number < STUDENT_MINIMUM || number > STUDENT_MAXIMUM);
        // check if already registered
        if (Student.find(tutorHead, number) == null)
        {
            System.out.println("\nAlready registered.");
            System.out.print("ENTER to continue: ");
            scanner.nextLine();
            return;
        } // end of if (Student.find(tutorHead, number) == null)

        student.setStudentNumber(number);
        System.out.print("First name: ");
        student.setFirstName(scanner.nextLine().toUpperCase());
        System.out.print("Last name: ");
        student.setLastName(scanner.nextLine().toUpperCase());
        char gender = 'a';
        do
        {
            exit = false;
            System.out.print("Gender: ");
            input = scanner.nextLine();
            if (input.toUpperCase().charAt(0) == 'M' 
				|| input.toUpperCase().charAt(0) == 'F')
            {
                gender = input.toUpperCase().charAt(0);
                exit = true;
            } // end of if (input.toUpperCase().charAt(0) == 'M'...)
            
        }
        while (!exit);
        student.setGender(gender);
        
        System.out.print("Homeroom Teacher: ");
        input = scanner.nextLine().toUpperCase();
        // exclude titles from teacher name
        for (int i = 0; i < EXCLUDE.length; i++)
        {
            input.replace(EXCLUDE[i], "");
        } // end of for (int i = 0; i < EXCLUDE.length; i++)
        student.setHomeroom(input);
        System.out.print("Email: ");
        student.setEmail(scanner.nextLine().toUpperCase());
        System.out.print("Grade: ");
        try
        {
            student.setGrade(Integer.parseInt(scanner.nextLine()));
        }
        catch (NumberFormatException error)
        {} // end of try-catch block
        System.out.print("Days free (eg. 10100 for Mon. and Wed. free): ");
        student.setDaysFree(scanner.nextLine().toCharArray());
        System.out.print("Courses to tutor (eg. FSF3,ENG4 - no spaces): ");
        student.setCourses(scanner.nextLine());
        System.out.print("Type 1 to create, else to discard: ");
        if (scanner.nextLine().equals("1"))
        {
            // add to head of linked list
            student.add(tuteeHead);
            tuteeHead = student;
            System.out.println("\nSuccessfully created.");
            System.out.print("ENTER to continue: ");
            scanner.nextLine();
        }
        else
        {
            System.out.println("\nCancelled.");
            System.out.print("ENTER to continue: ");
            scanner.nextLine();
        } // end of if (scanner.nextLine().equals("1"))
    } // end of method newTutee()
    
    private static void deleteTutor()
    {
        Student current = tutorHead;
        int count = 0;
        int index = 1;
        
        // show tutors
        System.out.println("All tutors:");
        while (current != null)
        {
            if (count == SHOW)
            {
                System.out.print("Press ENTER to show more: ");
                scanner.nextLine();
                count = 0;
            }
            else
            {
                System.out.println(index + " " + current);
                count++;
                index++;
                current = current.getNext();
            } // end of if (count == SHOW)
        } // end of while (current != null)
        
        // get input for which tutor
        int selection = -1;
        do
        {
            System.out.print("Delete which tutor? (0 to cancel) ");
            try
            {
                selection = Integer.parseInt(scanner.nextLine());
            }
            catch (NumberFormatException error)
            {
                selection = -1;
            } // end of try-catch block
            if (selection == 0)
            {
                System.out.println("\nCancelled.");
                System.out.print("ENTER to continue: ");
                scanner.nextLine();
                return;
            } // end of if (selection == 0)
        }
        while (selection < 0 || selection > tutorHead.length());
        
        // delete the tutor
        count = 1;
        current = tutorHead;
        // if deleting first node
        if (selection == 1)
        {
            tutorHead = tutorHead.getNext();
            System.out.println("\nDeleted.");
            System.out.print("ENTER to continue: ");
            scanner.nextLine();
            return;
        }
        // not first ndoe
        while (current != null)
        {
            if (count == selection - 1)
            {
                // if deleting the last node
                if (current.getNext().getNext() == null)
                {
                    current.getNext().add(null);
                }
                // not the last node
                else
                {
                    current.add(current.getNext().getNext());
                } // end of if (current.getNext().getNext() == null)
                System.out.println("\nDeleted.");
                System.out.print("ENTER to continue: ");
                scanner.nextLine();
                return;
            } // end of if (count == selection - 1)
            current = current.getNext();
            count++;
        } // end of while (current != null)
    } // end of method deleteTutor()
    
    private static void deleteTutee()
    {
        Student current = tuteeHead;
        int count = 0;
        int index = 1;
        
        // show tutees
        System.out.println("All tutees:");
        while (current != null)
        {
            if (count == SHOW)
            {
                System.out.print("Press ENTER to show more: ");
                scanner.nextLine();
                count = 0;
            }
            else
            {
                System.out.println(index + " " + current);
                count++;
                index++;
                current = current.getNext();
            } // end of (count == SHOW)
        } // end of while (current != null)
        
        // get input for which tutee
        int selection = -1;
        do
        {
            System.out.print("Delete which tutee? (0 to cancel) ");
            try
            {
                selection = Integer.parseInt(scanner.nextLine());
            }
            catch (NumberFormatException error)
            {
                selection = -1;
            } // end of try-catch block
            if (selection == 0)
            {
                System.out.println("\nCancelled.");
                System.out.print("ENTER to continue: ");
                scanner.nextLine();
                return;
            } // end of if (selection == 0)
        }
        while (selection < 0 || selection > tutorHead.length());
        
        // delete the tutee
        count = 1;
        current = tuteeHead;
        // if deleting first node
        if (selection == 1)
        {
            tuteeHead = tuteeHead.getNext();
            System.out.println("\nDeleted.");
            System.out.print("ENTER to continue: ");
            scanner.nextLine();
            return;
        } // end of if (selection == 1)
        while (current != null)
        {
            if (count == selection - 1)
            {
                // if deleting the last node
                if (current.getNext().getNext() == null)
                {
                    current.getNext().add(null);
                }
                // not the last node
                else
                {
                    current.add(current.getNext().getNext());
                } // end of if (current.getNext().getNext() == null)
                System.out.println("\nDeleted.");
                System.out.print("ENTER to continue: ");
                scanner.nextLine();
                return;
            } // end of if (count == selection - 1)
            current = current.getNext();
            count++;
        } // end of while (current != null)
    } // end of method deleteTutee()
    
    private static void addMatch()
    {
        System.out.println(BAR);
        int count = 0;
        int index = 1;
        // show tutees
        Student currentTutee = tuteeHead;
        while (currentTutee != null)
        {
            if (count == SHOW) 
            {
                System.out.print("Press ENTER to show more:");
                scanner.nextLine();
                count = 0;
            }
            else
            {
                System.out.println(index + " " + currentTutee);
                count++;
                index++;
                currentTutee = currentTutee.getNext();
            } // end of if (count == SHOW) 
        } // end of while (currentTutee != null)
        // tutee selection
        int number = -1;
        String input = "";
        do 
        {
            System.out.print("Which tutee? (0 to cancel) ");
            try
            {
                number = Integer.parseInt(scanner.nextLine());
            }
            catch (NumberFormatException error)
            {
                number = -1;
            } // end of try-catch block
        }
        while (number < 0 || number > tuteeHead.length());
               
        if (number == 0) 
        {
            System.out.println("\nCancelled.");
            System.out.print("ENTER to continue: ");
            scanner.nextLine();
            return;
        } // end of if (number == 0) 
        
        // work with the selected tutee
        Student tuteeSelected = Student.findByIndex(tuteeHead, number);
        Student tutorFiltered = new Student(tutorHead);
        
        // filter compatible tutors by day        
        count = 0;
        // check first one
        for (int j = 0; j < tuteeSelected.getDaysFree().length; j++)
        {
            // for free days of the tutee
            if (tutorFiltered.getDaysFree()[j] == '1' &&
                    tuteeSelected.getDaysFree()[j] == '1')
            {
                count++;
            } // end of if (tutorFiltered.getDaysFree()[j] == '1'...)
        } // end of for (int j = 0; j < tuteeSelected.getDaysFree().length; j++)
        if (count == 0)
        {
            tutorFiltered = tutorFiltered.getNext();
        } // end of if (count == 0)
        // check others
        Student currentTutor = tutorFiltered;
        while (currentTutor != null && currentTutor.getNext() != null)
        {
            count = 0;
            for (int j = 0; j < tuteeSelected.getDaysFree().length; j++)
            {
                // for free days of the tutee
                if (currentTutor.getNext().getDaysFree()[j] == '1' &&
                    tuteeSelected.getDaysFree()[j] == '1')
                {
                    count++;
                } // end of if (currentTutor.getDaysFree()[j] == '1'...)
            } // end of for (int j = 0; j < tuteeSelected.getDaysFree()...)
            // delete the tutor from compatible ones if no free days overlap
            if (count == 0)
            {
                currentTutor.add(currentTutor.getNext().getNext());
            } // end of if (count == 0)
            currentTutor = currentTutor.getNext();
        } // end of while (currentTutor != null && currentTutor.getNext()...)

        // filter compatible tutors by course
        count = 0;        
        String[] course = tuteeSelected.getCourses().split(",");
        // check first tutor
        for (int j = 0; j < course.length; j++)
        {
            // see if any of the same courses
            if (tutorFiltered.getCourses().contains(course[j]))
            {
                count++;
            } // end of if (tutorDayFiltered[i].getCourses()...)
        } // end of for (int j = 0; j < course.length; j++)
        if (count == 0) 
        {
            tutorFiltered = tutorFiltered.getNext();
        } // end of if (count == 0) 
        // check others
        currentTutor = tutorFiltered;
        while (currentTutor != null && currentTutor.getNext() != null)
        {
            count = 0;            
            for (int j = 0; j < course.length; j++)
            {
                // see if any of the same courses
                if (currentTutor.getNext().getCourses().contains(course[j]))
                {
                    count++;
                } // end of if (tutorDayFiltered[i].getCourses()...)
            } // end of for (int j = 0; j < course.length; j++)
            // delete the tutor from compatible ones if no courses overlap
            if (count == 0)
            {
                currentTutor.add(currentTutor.getNext().getNext());
            } // end of if (count == 0)
            currentTutor = currentTutor.getNext();
        } // end of for (int i = 0; i < tutorDayFiltered.length; i++)
        
        // prioritize tutors
        tutorFiltered = orderTutors(tuteeSelected, tutorFiltered);
        
        // show compatible tutors
        System.out.println("Compatible tutors: ");
        String output = "";
        count = 0;
        Student current = tutorFiltered;
        while (current != null)
        {            
            output = "";
            output += (count + 1) + " ";
            output += current.getFirstName() + " "
                + current.getLastName() + " free on "
                + new String(current.getDaysFree()) + " for "
                + current.getCourses();
            System.out.println(output);
            current = current.getNext();
            count++;
        } // end of for (int i = 0; i < tutorFiltered.length; i++)
        int selection = -1;
        
        // choose which tutor
        do
        {
            System.out.print("Pair " + tuteeSelected.getFirstName() 
				+ " with which tutor? (0 to cancel) ");
            try
            {
                input = scanner.nextLine();
                selection = Integer.parseInt(input);
            }
            catch (NumberFormatException error)
            {} // end of try-catch block
        }
        while (selection < 0 || selection > tutorFiltered.length());
        
        if (selection == 0) 
        {
            System.out.println("\nCancelled.");
            System.out.print("ENTER to continue: ");
            scanner.nextLine();
            return;
        } // end of if (selection == 0) 
        newMatch(selection, tutorFiltered, tuteeSelected);
        System.out.println("\nMatch created.");
        System.out.print("ENTER to continue: ");
        scanner.nextLine();
    } // end of method addMatch()
    
    private static void deleteMatch()
    {
        System.out.println(BAR);
        // show tutees
        int count = 0;
        int index = 1;
        Match currentMatch = matchHead;
        while (currentMatch != null)
        {
            if (count == SHOW) 
            {
                System.out.print("Press ENTER to show more:");
                scanner.nextLine();
                count = 0;
            }
            else
            {
                System.out.println(index + " " + currentMatch);
                count++;
                index++;
                currentMatch = currentMatch.getNext();
            } // end of if (count == SHOW) 
        } // end of while (currentMatch != null)
        // get input for which to delete
        int number = -1;
        String input = "";
        boolean exit = false;
        do
        {
            System.out.print("Delete which match? (0 to cancel) ");
            input = scanner.nextLine();
            
            // validate input
            try
            {
                number = Integer.parseInt(input);
                
            }
            catch (NumberFormatException error)
            {} // end of try-catch block
        }
        while (number < 0 || number > matchHead.length());
        if (number == 0) 
        {
            System.out.println("\nCancelled.");
            System.out.print("ENTER to continue: ");
            scanner.nextLine();
            return;
        } // end of if (number == 0) 
        // delete the match
        Match deleted = new Match();
        // if deleting first match        
        if (number == 1)
        {
            deleted.copyData(matchHead);
            matchHead = matchHead.getNext();            
        }
        // else
        else
        {
            count = 1;
            currentMatch = matchHead;
            while (currentMatch != null)
            {
                if (count == number - 1)
                {
                    deleted.copyData(currentMatch.getNext());
                    currentMatch.add(currentMatch.getNext().getNext());
                } // end of if (count == number - 1)
                currentMatch = currentMatch.getNext();
            } // end of while (currentMatch != null)
        } // end of if (number == 1)
        
        // edit days free of the pair
        char[] daysMatch = deleted.getMeetDays();
        char[] daysTutor = deleted.getTutor().getDaysFree();
        char[] daysTutee = deleted.getTutee().getDaysFree();
        for (int i = 0; i < daysTutor.length; i ++)
        {
            // make the tutor/tutee free again on the days where the pair met
            if (daysMatch[i] == '1') 
            {
                daysTutor[i] = '1';
                daysTutee[i] = '1';
            } // end of if (daysMatch[i] == '1') 
        } // end of for (int i = 0; i < daysTutor.length; i ++)
        Student.find(tutorHead, 
            deleted.getTutor().getStudentNumber()).setDaysFree(daysTutor);
        Student.find(tuteeHead, 
            deleted.getTutee().getStudentNumber()).setDaysFree(daysTutee);
            
        System.out.println("\nMatch deleted.");
        System.out.print("ENTER to continue: ");
        scanner.nextLine();
    } // end of method deleteMatch()  
    
    private static Student orderTutors(Student tutee, Student tutor)
    {
        int count1 = 0;
        int count2 = 0;
        boolean done = true;
        Student current;
        do
        {
            done = true;
            current = tutor;
            // first two
            for (int j = 0; j < tutee.getDaysFree().length; j++)
            {
                // for free days of the tutee
                if (current.getDaysFree()[j] == '1' &&
                    tutee.getDaysFree()[j] == '1')
                {
                    count1++;
                } // end of if (current.getDaysFree()[j] == '1'...)
                if (current.getNext().getDaysFree()[j] == '1' &&
                    tutee.getDaysFree()[j] == '1')
                {
                    count2++;
                } // end of if (current.getNext().getDaysFree()[j] == '1'...)
                
            } // end of for (int j = 0; j < tuteeSelected.getDaysFree()...)
            if (count2 > count1)
            {
                Student temp = current.getNext();
                
                current.add(current.getNext().getNext());
                tutor = temp;
                tutor.add(current);            
            } // end of if (count2 > count1)
            
            // others
            current = tutor;
            while (current != null && current.getNext() != null 
                && current.getNext().getNext() != null)
            {
                count1 = 0;
                count2 = 0;
                for (int j = 0; j < tutee.getDaysFree().length; j++)
                {
                    // for free days of the tutee
                    if (current.getNext().getDaysFree()[j] == '1' &&
                        tutee.getDaysFree()[j] == '1')
                    {
                        count1++;
                    } // end of if (current.getDaysFree()[j] == '1'...)
                    if (current.getNext().getNext().getDaysFree()[j] == '1' &&
                        tutee.getDaysFree()[j] == '1')
                    {
                        count2++;
                    } // end of if (current.getNext().getDaysFree()[j] == '1'..)
                    
                } // end of for (int j = 0; j < tuteeSelected.getDaysFree()...)
                if (count2 > count1)
                {
                    Student temp = current.getNext().getNext().getNext();
                    Student temp1 = current.getNext();
                    current.add(current.getNext().getNext());
                    current.getNext().add(temp1);
                    current.getNext().add(temp);
                    done = false;
                } // end of if (count2 > count1)
                current = current.getNext();
            } // end of for (int i = 0; i < tutorSelection.length; i++)
        }while (!done);
        return tutor;
    } // end of method orderTutors(Student tutee, Student tutor)
    
    private static void newMatch(int number, Student tutorFiltered, 
		Student tutee)
    {
        // match days
        Student tutor = Student.findByIndex(tutorFiltered, number);
        char[] days = new char[tutor.getDaysFree().length];
        
        for (int i = 0; i < days.length; i ++)
        {
            if (tutor.getDaysFree()[i] == tutee.getDaysFree()[i])
            {
                days[i] = tutor.getDaysFree()[i];
            }
            else
            {
                days[i] = '0';
            } // end of if (tutor.getDaysFree()[i] == tutee.getDaysFree()[i])
        } // end of for (int i = 0; i < days.length; i ++)
        
        // select which days
        Scanner scanner = new Scanner(System.in);
        char[] attempt = new char[tutor.getDaysFree().length];
        boolean good = true;
        do
        {
            good = true;
            System.out.print
				("Which days? (eg. 10100 for Mon and Wed meet days) ");
            attempt = scanner.nextLine().toCharArray();
            if (attempt.length == tutor.getDaysFree().length)
            {
                // check to see if the entered days work
                for (int i = 0; i < attempt.length; i++)
                {
                    if (attempt[i] == '1' && days[i] != '1')
                    {
                        good = false;
                    } // end of if (attempt[i] == '1' && days[i] != '1')
                } // end of for (int i = 0; i < attempt.length; i++)
            }
            else 
            {
                good = false;
            } // end of if (attempt.length == tutor.getDaysFree().length)
        }
        while (!good);
        
        // make tutor/tutee not free anymore on the match days
        char[] newDaysTutor = tutor.getDaysFree();
        char[] newDaysTutee = tutee.getDaysFree();
        for (int i = 0; i < days.length; i++)
        {
            if (attempt[i] == '1')
            {
                newDaysTutor[i] = '0';
                newDaysTutee[i] = '0';
            } // end of if (days[i] == '1')
        } // end of for (int i = 0; i < days.length; i++)
        tutor.setDaysFree(newDaysTutor);
        tutee.setDaysFree(newDaysTutee);
        
        // match courses
        String[] course1 = tutor.getCourses().split(",");
        String[] course2 = tutee.getCourses().split(",");
        
        String courses = null;
        
        for (int i = 0; i < course1.length; i++)
        {
            for (int j = 0; j < course2.length; j++)
            {
                if (course1[i].equals(course2[j]))
                {
                    if (courses == null) 
                    {
                        courses = course1[i];
                    }
                    else
                    {
                        courses += "," + course1[i];
                    } // end of if (courses == null) 
                } // end of if (course1[i].equals(course2[j]))
            } // end of for (int j = 0; j < course2.length; j++)
        } // end of for (int i = 0; i < course1.length; i++)
        
        // put in linked list
        String output = new String(attempt) + "|"
            + tutor.getStudentNumber() + "|"
            + tutee.getStudentNumber() + "|"
            + courses;
        Match newMatch = new Match(output);
        newMatch.add(matchHead);
        matchHead = newMatch;
    } // end of method newMatch(Student tutor, Student tutee)
    
    private static void save()
    {
        try
        {
            // write to tutor file
            PrintWriter writer = new PrintWriter(Student.fileTutor);
            Student current = tutorHead;
            while (current != null)
            {
                writer.println(current.toString());
                current = current.getNext();
            } // end of while (current != null)
            writer.close();
            //write to tutee file
            writer = new PrintWriter(Student.fileTutee);
            current = tuteeHead;
            while (current != null)
            {
                writer.println(current.toString());
                current = current.getNext();
            } // end of while (current != null)
            writer.close();
            // write to match file
            writer = new PrintWriter(Match.fileMatch);
            Match currentMatch = matchHead;
            while (currentMatch != null)
            {
                writer.print(new String(currentMatch.getMeetDays()) + "|"
                    + currentMatch.getTutor().getStudentNumber() + "|"
                    + currentMatch.getTutee().getStudentNumber() + "|"
                    + currentMatch.getCourses());
                writer.println();
                currentMatch = currentMatch.getNext();
            } // end of while (currentMatch != null)
            writer.close();
        }
        catch (IOException error)
        {} // end of try-catch block
    } // end of method save()
    
    //easiest way to clear screen
    private static void cls()
    {
        for (int i = 0; i < HUNDRED_LINES; i++)
        {
            System.out.println();
        } // end of for (int i = 0; i < HUNDRED_LINES; i++)
    } // end of method cls()
} // end of class TutoringDBMS
