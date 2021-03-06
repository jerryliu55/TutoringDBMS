import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.NumberFormatException;

/**
 * A class representing a student in a peer tutoring database, also
 * a node in a linked list.
 * 
 * @author Jerry Liu 
 * @version 1.1 2015-03-09
 */
public class Student
{
    /*
     * class fields
     */
    /**
     * The file with the tutor data.
     */
    public static String fileTutor = "0_Tutor.txt";
    /**
     * The file with the tutee data.
     */
    public static String fileTutee = "0_Tutee.txt";
    
    private static final int INDEX_NUMBER = 3;
    private static final int DAYS_IN_WEEK = 5;
    
    /*
     * instance fields
     */
    private String firstName;
    private String lastName;
    private int studentNumber;
    private char gender;
    private String email;
    private String homeroom;
    private int grade;
    private String courses = "";
    private int tutor;
    private char[] daysFree;
    private int attendance = 0;
    private Student next = null;
    
    private boolean checked = false;
    
    /*
     * constructors
     */
    
    /**
     * Creates a new student with no data.
     */
    public Student()
    {
    
    } // end of constructor Student()
    
    /**
     * Creates a new student given a line of data representing the student.
     * 
     * @param line the string which represents this student in a database
     */
    public Student(String line)
    {
        String[] data = line.split("\\|");
        firstName = data[0];
        lastName = data[1];
        gender = data[2].charAt(0);
        studentNumber = Integer.parseInt(data[3]);
        homeroom = data[4];
        email = data[5];
        grade = Integer.parseInt(data[6]);
        tutor = Integer.parseInt(data[7]);
        daysFree = data[8].toCharArray();
        courses = data[9];
        attendance = Integer.parseInt(data[10]);
    } // end of constuctor Student(String line)
       
    /**
     * Creates a new student given another student.
     * 
     * @param student the student to be copied to this student.
     */
    public Student(Student student)
    {
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.gender = student.getGender();
        this.studentNumber = student.getStudentNumber();
        this.homeroom = student.getHomeroom();
        this.email = student.getEmail();
        this.grade = student.getGrade();
        this.tutor = student.getTutor();
        this.daysFree = student.getDaysFree();
        this.courses = student.getCourses();
        this.attendance = student.getAttendance();
        if (student.next != null)
            this.next = new Student(student.next);
    } // end of method Student(Student student)

    /*
     * static methods
     */    
    
    /**
     * Finds the student in a given list.
     * 
     * @param head the list to be searched
     * @param number the student number of the specific student
     * @return the student found, or null if the specific student
     * is not found
     */
    public static Student find(Student head, int number)
    {
        Student current = head;
        while (current != null)
        {
            if (current.getStudentNumber() == number)
                return current;
            current = current.getNext();
        } // end of method while (current != null)
        return null;
    } // end of method find(Student head, int number)
    
    /**
     * Finds the student in a given list.
     * 
     * @param head the list to be searched
     * @param number the index number of the specific student (starts
     * at 1)
     * @return the student found, or null if the specific student
     * is not found
     */
    public static Student findByIndex(Student head, int number)
    {
        Student current = head;
        int count = 1;
        while (current != null)
        {
            if (count == number)
                return current;
            current = current.getNext();
            count++;
        } // end of method while (current != null)
        return null;
    } // end of method findByIndex(Student head, int number)
    
    /**
     * Removes a student from a list of students given the head of the
     * list and the student number of the student.
     * 
     * @param head the head of the linked list
     * @param number the student number of the student to be removed
     * @return the head of the list of students after removal of 
     * specified student
     */
    public static Student remove(Student head, int number)
    {
        Student current = head;
        // if the first one
        if (current.getStudentNumber() == number) 
        {
            head = current.getNext();
            return head;
        } // end of if (current.getStudentNumber() == number) 
        // not the first
        while (current != null)
        {
            if (current.getNext().getStudentNumber() == number)
            {
                current.add(current.getNext().getNext());
            } // end of if (current.getNext().getStudentNumber() == number)
            current = current.getNext();
        } // end of while (current != null)
        return head;
    } // end of method remove(Student head, int number)
    
    /*
     * accessors
     */
    
    /**
     * Converts this student into a string representation.
     * 
     * @return a string representation of this student, suitable to
     * print directly into a text file.
     */
    public String toString()
    {        
        // gather the data
        String data = "";
        data = firstName + "|"
            + lastName + "|"
            + gender + "|"
            + studentNumber + "|"
            + homeroom + "|"
            + email + "|"
            + grade + "|"
            + String.valueOf(tutor) + "|";
        for (int i = 0; i < daysFree.length; i++)
        {
            data += daysFree[i];
        } // end of for (int i = 0; i < daysFree.length; i++)
        data += "|";
        data += courses + "|"
            + attendance;
        return data;
    } // end of method toString() 
    
    /**
     * Returns the length of a linked list starting at this student.
     * 
     * @return the length of the list
     */
    public int length()
    {
        int count = 0;
        Student current = this;
        while (current != null)
        {
            count++;
            current = current.getNext();
        } // end of while (current != null)
        return count;
    } // end of method length()
    
    /**
     * Gets the next student in a linked list.
     * 
     * @return the next student following this student
     */
    public Student getNext()
    {
        return next;
    } // end of method getNext()
    
    /**
     * Gets whether this student has signed in or not.
     * 
     * @return <code>true</code> if this student has signed in, <code>
     * false</code> otherwise
     */
    public boolean getChecked()
    {
        return checked;
    } // end of method getChecked()
     
    /**
     * Gets the first name of this student.
     * 
     * @return the first name of this student
     */
    public String getFirstName()
    {
        return firstName;
    } // end of method getFirstName()
    
    /**
     * Gets the last name of this student.
     * 
     * @return the last name of this student
     */
    public String getLastName()
    {
        return lastName;
    } // end of method getLastName()
    
    /**
     * Gets the student number of this student.
     * 
     * @return the student number of this student
     */
    public int getStudentNumber()
    {
        return studentNumber;
    } // end of method getStudentNumber()
    
    /**
     * Gets the gender of this student.
     * 
     * @return the gender of this student
     */
    public char getGender()
    {
        return gender;
    } // end of method getGender()
    
    /**
     * Gets the grade of this student.
     * 
     * @return the grade of this student
     */
    public int getGrade()
    {
        return grade;
    } // end of method getGrade()
    
    /**
     * Gets the email of this student.
     * 
     * @return the email of this student
     */
    public String getEmail()
    {
        return email;
    } // end of method getEmail()
    
    /**
     * Gets the homeroom teacher of this student.
     * 
     * @return the homeroom teacher of this student
     */
    public String getHomeroom()
    {
        return homeroom;
    } // end of method getHomeroom()
    
    /**
     * Gets whether this student is a tutor or tutee.
     * 
     * @return 1 if this student is a tutor, 0 otherwise
     */
    public int getTutor()
    {
        return tutor;        
    } // end of method getTutor()
    
    /**
     * Gets the courses of this student.
     * 
     * @return the courses of this student
     */
    public String getCourses()
    {
        return courses;
    } // end of method getCourses()
    
    /**
     * Gets the days free of this student.
     * 
     * @return the days free of this student
     */
    public char[] getDaysFree()
    {
        return daysFree;
    } // end of method getDaysFree()
    
    /**
     * Gets the attendance number of this student.
     * 
     * @return the attendance number of this student
     */
    public int getAttendance()
    {
        return attendance;
    } // end of method getAttendance()
        
    /*
     * mutators
     */
    
    /**
     * Sets the next node following this student to a specified student.
     * 
     * @param next the student to follow this student
     */
    public void add(Student next)
    {
        this.next = next;
    } // end of method add(Student next)
    
    /**
     * Sets the next node to be a student with the data (but not next 
     * node) of a specified student.
     * 
     * @param student the student whose data is to be used
     */
    public void nextData(Student student)
    {
        this.next = student;
        next.add(null);
    } // end of method nextData(Student student)
    
    /**
     * Sets the data of this student to the same data as a specified
     * student (but not the next node)
     * 
     * @param student the student whose data is to be copied
     */
    public void copyData(Student student)
    {
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.gender = student.getGender();
        this.studentNumber = student.getStudentNumber();
        this.homeroom = student.getHomeroom();
        this.email = student.getEmail();
        this.grade = student.getGrade();
        this.tutor = student.getTutor();
        this.daysFree = student.getDaysFree();
        this.courses = student.getCourses();
        this.attendance = student.getAttendance();
        next = null;
    } // end of method copyData(Student student)
    
    /**
     * Sets the first name of this student.
     * 
     * @param firstName the new first name of this student
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    } // end of method setFirstName(String firstName)
    
    /**
     * Sets the last name of this student.
     * 
     * @param lastName the new last name of this student
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    } // end of method setLastName(String lastName)
    
    /**
     * Sets the student number of this student.
     * 
     * @param studentNumber the new student number of this student
     */
    public void setStudentNumber(int studentNumber)
    {
        this.studentNumber = studentNumber;
    } // end of method setStudentNumber(int studentNumber)
    
    /**
     * Sets the gender of this student.
     * 
     * @param gender the new gender of this student
     */
    public void setGender(char gender)
    {
        this.gender = gender;
    } // end of method setGender(char gender)
    
    /**
     * Sets the tutor/tutee designation of this student.
     * 
     * @param tutor <code>true</code> if this student is to be a tutor,
     * <code>false</code> if tutee
     */
    public void setTutor(int tutor)
    {
        this.tutor = tutor;
    } // end of method setTutor(boolean tutor)
    
    /**
     * Sets the grade of this student.
     * 
     * @param grade the new grade of this student
     */
    public void setGrade(int grade)
    {
        this.grade = grade;
    } // end of method setGrade(int grade)
    
    /**
     * Sets the email of this student.
     * 
     * @param email the new email of this student
     */
    public void setEmail(String email)
    {
        this.email = email;
    } // end of method setEmail(String email)
    
    /**
     * Sets the homeroom teacher of this student.
     * 
     * @param homeroom the new homeroom teacher of this student
     */
    public void setHomeroom(String homeroom)
    {
        this.homeroom = homeroom;
    } // end of method setHomeroom(String homeroom)
    
    /**
     * Sets the courses of this student.
     * 
     * @param courses the new courses of this student
     */
    public void setCourses(String courses)
    {
        this.courses = courses;
    } // end of method setCourses(String courses)
    
    /**
     * Sets the days free of this student.
     * 
     * @param daysFree the new days free of this student
     */
    public void setDaysFree(char[] daysFree)
    {
        this.daysFree = daysFree;
    } // end of method setDaysFree(char[] daysFree)
    
    /**
     * Sets the attendance number of this student.
     * 
     * @param attendance the new attendance number of this student
     */
    public void setAttendance(int attendance)
    {
        this.attendance = attendance;
    } // end of method setAttendance(int attendance)
    
    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }
    
    /**
     * Increments the attendance number of this student by one.
     */
    public void increaseAttendance()
    {
        attendance++;
    } // end of method increaseAttendance()
} // end of class Student

/*
 * unused code 
 */

    /*public static Student findStudent(int number, boolean tutor)
    {
        try
        {
            // tutor or tutee file
            BufferedReader studentReader;
            if (tutor)
                studentReader = new BufferedReader(new FileReader(fileTutor));
            else
                studentReader = new BufferedReader(new FileReader(fileTutee));
            
            String input = "";
            // count lines
            int count = 0;
            String[] data;
            while ((input = studentReader.readLine()) != null)
            {
                data = input.split("\\|");
                if (data[INDEX_NUMBER].equals(String.valueOf(number)))
                {
                    return new Student(number, tutor);
                }
            }
            
            
            studentReader.close();
            
        }
        catch (IOException error)
        {
            
        }
        return new Student();
    }*/