
public class Validation
{
	public static boolean isFilenameProvided(String filename)
	{
		if (filename.length() == 0)
		{
			return false;
		}
		
		return true;
	}
}
