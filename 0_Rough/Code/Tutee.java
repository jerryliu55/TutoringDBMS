
/**
 * A tutee in the peer tutoring DBMS project.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tutee
{
    /*
     * instance fields
     */
    int attendance = 0;
    String name;
    int studentNumber;
    char gender;
    String email;
    String homeroomTeacher;
    String subject = "";
    
    boolean[] daysFree;
    
    /*
     * constructors
     */

    /**
     * Constructs a tutor with default values.
     */
    public Tutee()
    {
    }

    /*
     * accessors
     */
    
    public String getName()
    {
        return name;
    }
    
    public int getStudentNumber()
    {
        return studentNumber;
    }
    
    public char getGender()
    {
        return gender;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public String getHomeroomTeacher()
    {
        return homeroomTeacher;
    }
    
    public String getSubject()
    {
        return subject;
    }
    
    public boolean[] getDaysFree()
    {
        return daysFree;
    }
    
    public String toString()
    {
        // summarize the days this tutor is free
        String days = "";
        if (daysFree[0]) days += "Monday ";
        if (daysFree[1]) days += "Tuesday ";
        if (daysFree[2]) days += "Wednesday ";
        if (daysFree[3]) days += "Thursday ";
        if (daysFree[4]) days += "Friday ";
        
        return "Tutee:\n" 
            + "Name: " + name + "\n"
            + "Student Number: " + studentNumber + "\n"
            + "Gender: " + gender + "\n"
            + "Email: " + email + "\n"
            + "Homeroom Teacher: " + homeroomTeacher + "\n"
            + "Subject(s): " + subject + "\n"
            + "Days Free: " + days + "\n"
            + "Attendance: " + attendance + "\n";
    }
    
    /*
     * mutators
     */
    
    /**
     * 
     */
    public void setName(String name)
    {
        this.name = name.toUpperCase();
    }
    
    public void setStudentNumber(int studentNumber)
    {
        this.studentNumber = studentNumber;
    }
    
    public void setGender(char gender)
    {
        this.gender = gender;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public void setHomeroomTeacher(String homeroomTeacher)
    {
        this.homeroomTeacher = homeroomTeacher;
    }
    
    public void setSubject(String subject)
    {
        this.subject = subject;
    }
    
    public void setDaysFree(boolean[] daysFree)
    {
        this.daysFree = daysFree;
    }
}
