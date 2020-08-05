package kunsan.kim.bagchecker;

public class Data_Book
{
	/*
	String name;	//책의 이름
	String id;		//태그의 아이디

	public Data_Book(String name, String id)
	{
		this.name = name;
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
	*/

	int id;
	String uid;
	String name;
	int isSun;
	int isMon;
	int isTue;
	int isWed;
	int isThur;
	int isFri;
	int isSat;
	int isInBag;

	public Data_Book(int id, String uid, String name, int isSun, int isMon, int isTue, int isWed, int isThur, int isFri, int isSat, int isInBag)
	{
		this.id = id;
		this.uid = uid;
		this.name = name;
		this.isSun = isSun;
		this.isMon = isMon;
		this.isTue = isTue;
		this.isWed = isWed;
		this.isThur = isThur;
		this.isFri = isFri;
		this.isSat = isSat;
		this.isInBag = isInBag;
	}

	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}

	public String getUid()
	{
		return uid;
	}
	public void setUid(String uid)
	{
		this.uid = uid;
	}

	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public int getIsSun()
	{
		return isSun;
	}
	public void setIsSun(int isSun)
	{
		this.isSun = isSun;
	}

	public int getIsMon()
	{
		return isMon;
	}
	public void setIsMon(int isMon)
	{
		this.isMon = isMon;
	}

	public int getIsTue()
	{
		return isTue;
	}
	public void setIsTue(int isTue)
	{
		this.isTue = isTue;
	}

	public int getIsWed()
	{
		return isWed;
	}
	public void setIsWed(int isWed)
	{
		this.isWed = isWed;
	}

	public int getIsThur()
	{
		return isThur;
	}
	public void setIsThur(int isThur)
	{
		this.isThur = isThur;
	}

	public int getIsFri()
	{
		return isFri;
	}
	public void setIsFri(int isFri)
	{
		this.isFri = isFri;
	}

	public int getIsSat()
	{
		return isSat;
	}
	public void setIsSat(int isSat)
	{
		this.isSat = isSat;
	}

	public int getIsInBag()
	{
		return isInBag;
	}
	public void setIsInBag(int isInBag)
	{
		this.isInBag = isInBag;
	}

}
