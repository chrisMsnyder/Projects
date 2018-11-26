/**
 *
 *
 * @author Chris Snyder
 */
public class ClockGetterSetter
{
    private int iHour;
    private int iMin;

    public ClockGetterSetter(int iHour, int iMin)
    {
        this.iHour = iHour;
        this.iMin = iMin;
    }

    public String getTime()
    {
        if (this.iMin < 10)
            return this.iHour + ":0" + this.iMin;
        else
            return this.iHour + ":" + this.iMin;
    }


    public void setTime(int iHour, int iMin)
    {

        this.iHour = iHour;
        this.iMin = iMin;
    }

    public int getiHour()
    {
        return this.iHour;
    }

    public int getiMin()
    {
        return  this.iMin;
    }
}
