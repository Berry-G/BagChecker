package kunsan.kim.bagchecker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

import kunsan.jo.bagchecker.Bag;
import kunsan.jo.bagchecker.BagDatabaseHelper;

public class Data_BookAdapter extends RecyclerView.Adapter<Data_BookAdapter.ViewHolder>
{
	ArrayList<Data_Book> items = new ArrayList<Data_Book>();


	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)            //인플레이션을 통해 만든 뷰 객체
	{
		LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());            //p.423 참조
		View itemView = inflater.inflate(R.layout.book_item, viewGroup, false);
		return new ViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position)
	{
		Data_Book item = items.get(position);
		viewHolder.setItem(item);
	}

	@Override
	public int getItemCount()
	{
		return items.size();
	}

	public void addItem(Data_Book item)        //Book 객체를 각각 ArrayList 안에 넣어서 관리하는 코드 425쪽
	{
		items.add(item);
	}

	public void setItems(ArrayList<Data_Book> items)
	{
		this.items = items;
	}

	public Data_Book getItem(int position)
	{
		return items.get(position);
	}

	public void setItem(int position, Data_Book item)
	{
		items.set(position, item);
	}

	static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener        //뷰 홀더 클래스의 정의 421쪽
	{
		TextView textView;
		TextView textView2;
		Button buttonSun;
		Button buttonMon;
		Button buttonTue;
		Button buttonWed;
		Button buttonThur;
		Button buttonFri;
		Button buttonSat;
		ToggleButton toggleButtonInBag;



		public ViewHolder(View itemView)
		{
			super(itemView);

			itemView.setOnClickListener(this);
			textView = itemView.findViewById(R.id.textView);
			textView2 = itemView.findViewById(R.id.textView2);
			buttonSun = itemView.findViewById(R.id.buttonSun);
			buttonMon = itemView.findViewById(R.id.buttonMon);
			buttonTue = itemView.findViewById(R.id.buttonTue);
			buttonWed = itemView.findViewById(R.id.buttonWed);
			buttonThur = itemView.findViewById(R.id.buttonThur);
			buttonFri = itemView.findViewById(R.id.buttonFri);
			buttonSat = itemView.findViewById(R.id.buttonSat);
			toggleButtonInBag = itemView.findViewById(R.id.buttonInBag);
		}

		public void setItem(Data_Book item)
		{
			boolean b;

			textView.setText(item.getName());
			textView2.setText(item.getUid());

			b = (item.getIsSun() != 0);
			buttonSun.setEnabled(b);
			b = (item.getIsMon() != 0);
			buttonMon.setEnabled(b);
			b = (item.getIsTue() != 0);
			buttonTue.setEnabled(b);
			b = (item.getIsWed() != 0);
			buttonWed.setEnabled(b);
			b = (item.getIsThur() != 0);
			buttonThur.setEnabled(b);
			b = (item.getIsFri() != 0);
			buttonFri.setEnabled(b);
			b = (item.getIsSat() != 0);
			buttonSat.setEnabled(b);
			b = (item.getIsInBag() != 0);
			toggleButtonInBag.setChecked(b);
		}
		/*

		public Data_book getItem(View itemView)
		{

		}
		*/

		@Override
		public void onClick(final View v) {

			final BagDatabaseHelper bagDBHelper;
			SQLiteDatabase bagDatabase;

			bagDBHelper = new BagDatabaseHelper(v.getContext());
			//bagDatabase = bagDBHelper.getWritableDatabase();

			String Name = textView.getText().toString();
			final String uid = textView2.getText().toString();
			// Log.d("this", Name + ", "+ uid + " TEEST", null);

			LinearLayout layout = new LinearLayout(v.getContext());
			layout.setOrientation(LinearLayout.VERTICAL);

			final EditText etName= new EditText(v.getContext());
			etName.setHint("이름을 변경하세요");

			final CheckBox chSun=new CheckBox(v.getContext());
			chSun.setHint("일요일");
			final CheckBox chMon=new CheckBox(v.getContext());
			chMon.setHint("월요일");
			final CheckBox chTue=new CheckBox(v.getContext());
			chTue.setHint("화요일");
			final CheckBox chWed=new CheckBox(v.getContext());
			chWed.setHint("수요일");
			final CheckBox chThur=new CheckBox(v.getContext());
			chThur.setHint("목요일");
			final CheckBox chFri=new CheckBox(v.getContext());
			chFri.setHint("금요일");
			final CheckBox chSat=new CheckBox(v.getContext());
			chSat.setHint("토요일");
			final CheckBox chBag=new CheckBox(v.getContext());
			chBag.setHint("가방속 유무");

			layout.addView(etName);
			layout.addView(chSun);
			layout.addView(chMon);
			layout.addView(chTue);
			layout.addView(chWed);
			layout.addView(chThur);
			layout.addView(chFri);
			layout.addView(chSat);
			layout.addView(chBag);

			AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
			dialog.setTitle("정보를 입력하세요")
					.setView(layout)
					.setPositiveButton("수정", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							String name=etName.getText().toString();
							int sun;
							if(chSun.isChecked()==true) sun=1;
							else sun=0;
							int mon;
							if(chMon.isChecked()==true) mon=1;
							else mon=0;
							int tue;
							if(chTue.isChecked()==true) tue=1;
							else tue=0;
							int wed;
							if(chWed.isChecked()==true) wed=1;
							else wed=0;
							int thur;
							if(chThur.isChecked()==true) thur=1;
							else thur=0;
							int fri;
							if(chFri.isChecked()==true) fri=1;
							else fri=0;
							int sat;
							if(chSat.isChecked()==true) sat=1;
							else sat=0;
							int inbag;
							if(chBag.isChecked()==true) inbag=1;
							else inbag=0;

							Bag bag = new Bag();
							bag.setUid(uid);
							bag.setName(name);
							bag.setIsSun(sun);
							bag.setIsMon(mon);
							bag.setIsTue(tue);
							bag.setIsWed(wed);
							bag.setIsThur(thur);
							bag.setIsFri(fri);
							bag.setIsSat(sat);
							bag.setIsInBag(inbag);

							Log.d("test","레코드 : "  + ", " + bag.getUid() + ", " + bag.getName());

							bagDBHelper.updateBag(bag);

						}

					})
					.setNeutralButton("취소", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					})
					.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							LinearLayout layout1 = new LinearLayout(v.getContext());
							AlertDialog.Builder dialog2 = new AlertDialog.Builder(v.getContext());
							dialog2.setTitle("정말 삭제하시겠습니까?")
									.setView(layout1)
									.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											Bag bag = new Bag();
											bag.setUid(uid);
											bagDBHelper.deleteBag(bag);

										}
									})
									.setNegativeButton("취소", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {

										}
									})
									.create()
									.show();
						}
					})
					.create()
					.show();

		}
	}

}
