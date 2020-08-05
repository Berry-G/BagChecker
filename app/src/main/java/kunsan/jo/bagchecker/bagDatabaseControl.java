package kunsan.jo.bagchecker;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class bagDatabaseControl
{
    // DB 선언
    BagDatabaseHelper bagDBHelper;
    SQLiteDatabase bagDatabase;
    String tableName;

    /*

    public void executeQuery()
    {
        println("executeQuery 호출");

        Cursor cursor = bagDatabase.rawQuery("select uid, name, issat, ismon, istue, iswed, isthur, isfri, issat from bag", null);
        int recordCount = cursor.getCount();
        println("레코드 개수: " + recordCount);

        for(int i = 0; i < recordCount; i++)
        {
            cursor.moveToNext();
            String uid = cursor.getString(0);
            String name = cursor.getString(1);
            int isSun = cursor.getInt(2);
            int isMon = cursor.getInt(3);
            int isTue = cursor.getInt(4);
            int isWed = cursor.getInt(5);
            int isThur = cursor.getInt(6);
            int isFri = cursor.getInt(7);
            int isSat = cursor.getInt(8);

            println("레코드 #" + i + " : " + uid + ", " + name + ", " + isSun + ", " + isMon + ", " + isTue + ", "
            + isWed + ", " + isThur + ", " + isFri + ", " + isSat);
        }

        cursor.close();
    }

    */

    public void println(String Data)
    {
        Log.d("MainActivity", Data);
    }
}
