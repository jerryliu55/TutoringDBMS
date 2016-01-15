
/**
 * A tutor(s)-tutee(s) match made in the peer tutoring DBMS project.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Match
{
    /*
     * instance fields
     */
    Tutor[] tutor;
    Tutee[] tutee;

    /**
     * Constructor for objects of class Match
     */
    public Match()
    {
    }

    public void setMatch(Tutor[] tutor, Tutee[] tutee)
    {
        this.tutor = tutor;
        this.tutee = tutee;
    }
}
