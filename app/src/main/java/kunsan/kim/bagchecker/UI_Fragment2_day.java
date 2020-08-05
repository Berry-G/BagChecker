package kunsan.kim.bagchecker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

public class UI_Fragment2_day extends Fragment {

    UI_Fragment3_mon UIFragment3mon;
    UI_Fragment4_tue UIFragment4tue;
    ToggleButton tb_mon;    //요일 온오프용
    ToggleButton tb_tue;
    ToggleButton tb_wed;
    ToggleButton tb_thu;
    ToggleButton tb_fri;
    ToggleButton tb_sat;
    ToggleButton tb_sun;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment2_by_day, container, false);

        UIFragment3mon = new UI_Fragment3_mon();
        UIFragment4tue = new UI_Fragment4_tue();

        tb_mon = (ToggleButton) rootView.findViewById(R.id.button_mon);
        tb_tue = (ToggleButton) rootView.findViewById(R.id.button_tue);
        tb_wed = (ToggleButton) rootView.findViewById(R.id.button_wed);
        tb_thu = (ToggleButton) rootView.findViewById(R.id.button_thu);
        tb_fri = (ToggleButton) rootView.findViewById(R.id.button_fri);
        tb_sat = (ToggleButton) rootView.findViewById(R.id.button_sat);
        tb_sun = (ToggleButton) rootView.findViewById(R.id.button_sun);

        tb_mon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(tb_mon.isChecked())      //토글이 켜져있을 때
                {
                    tb_mon.setEnabled(false);       //월요일 비활성화
                    tb_tue.setChecked(false);       //나머지요일 체크해제 + 활성화
                    tb_tue.setEnabled(true);
                    tb_wed.setChecked(false);
                    tb_wed.setEnabled(true);
                    tb_thu.setChecked(false);
                    tb_thu.setEnabled(true);
                    tb_fri.setChecked(false);
                    tb_fri.setEnabled(true);
                    tb_sat.setChecked(false);
                    tb_sat.setEnabled(true);
                    tb_sun.setChecked(false);
                    tb_sun.setEnabled(true);
                                                    //월요일 해당하는 자료들 읽어오도록 제어z
                }
                else{                               //토글이 꺼져있을 때 ==> 비활성화 시킬꺼라 구현 필요 X
//                    tv.setText("토클 버튼 상태 : 꺼짐");
                    //tb_mon.setEnabled(true);
                }


            }
        });

        tb_tue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(tb_tue.isChecked())
                {
                    tb_tue.setEnabled(false);
                    tb_mon.setChecked(false);
                    tb_mon.setEnabled(true);
                    tb_wed.setChecked(false);
                    tb_wed.setEnabled(true);
                    tb_thu.setChecked(false);
                    tb_thu.setEnabled(true);
                    tb_fri.setChecked(false);
                    tb_fri.setEnabled(true);
                    tb_sat.setChecked(false);
                    tb_sat.setEnabled(true);
                    tb_sun.setChecked(false);
                    tb_sun.setEnabled(true);
                }

                else{                               //토글이 꺼져있을 때 ==> 비활성화 시킬꺼라 구현 필요 X
//                    tv.setText("토클 버튼 상태 : 꺼짐");
                    //tb_tue.setEnabled(true);
                }
            }
        });

        tb_wed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(tb_tue.isChecked())
                {
                    tb_wed.setEnabled(false);
                    tb_mon.setChecked(false);
                    tb_mon.setEnabled(true);
                    tb_tue.setChecked(false);
                    tb_tue.setEnabled(true);
                    tb_thu.setChecked(false);
                    tb_thu.setEnabled(true);
                    tb_fri.setChecked(false);
                    tb_fri.setEnabled(true);
                    tb_sat.setChecked(false);
                    tb_sat.setEnabled(true);
                    tb_sun.setChecked(false);
                    tb_sun.setEnabled(true);
                }

                else{                               //토글이 꺼져있을 때 ==> 비활성화 시킬꺼라 구현 필요 X
//                    tv.setText("토클 버튼 상태 : 꺼짐");
                    //tb_tue.setEnabled(true);
                }
            }
        });

        tb_thu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(tb_thu.isChecked())
                {
                    tb_thu.setEnabled(false);
                    tb_mon.setChecked(false);
                    tb_mon.setEnabled(true);
                    tb_tue.setChecked(false);
                    tb_tue.setEnabled(true);
                    tb_wed.setChecked(false);
                    tb_wed.setEnabled(true);
                    tb_fri.setChecked(false);
                    tb_fri.setEnabled(true);
                    tb_sat.setChecked(false);
                    tb_sat.setEnabled(true);
                    tb_sun.setChecked(false);
                    tb_sun.setEnabled(true);
                }

                else{                               //토글이 꺼져있을 때 ==> 비활성화 시킬꺼라 구현 필요 X
//                    tv.setText("토클 버튼 상태 : 꺼짐");
                    //tb_tue.setEnabled(true);
                }
            }
        });

        tb_fri.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(tb_fri.isChecked())
                {
                    tb_fri.setEnabled(false);
                    tb_mon.setChecked(false);
                    tb_mon.setEnabled(true);
                    tb_tue.setChecked(false);
                    tb_tue.setEnabled(true);
                    tb_wed.setChecked(false);
                    tb_wed.setEnabled(true);
                    tb_thu.setChecked(false);
                    tb_thu.setEnabled(true);
                    tb_sat.setChecked(false);
                    tb_sat.setEnabled(true);
                    tb_sun.setChecked(false);
                    tb_sun.setEnabled(true);
                }

                else{                               //토글이 꺼져있을 때 ==> 비활성화 시킬꺼라 구현 필요 X
//                    tv.setText("토클 버튼 상태 : 꺼짐");
                    //tb_tue.setEnabled(true);
                }
            }
        });

        tb_sat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(tb_sat.isChecked())
                {
                    tb_sat.setEnabled(false);
                    tb_mon.setChecked(false);
                    tb_mon.setEnabled(true);
                    tb_tue.setChecked(false);
                    tb_tue.setEnabled(true);
                    tb_wed.setChecked(false);
                    tb_wed.setEnabled(true);
                    tb_thu.setChecked(false);
                    tb_thu.setEnabled(true);
                    tb_fri.setChecked(false);
                    tb_fri.setEnabled(true);
                    tb_sun.setChecked(false);
                    tb_sun.setEnabled(true);
                }

                else{                               //토글이 꺼져있을 때 ==> 비활성화 시킬꺼라 구현 필요 X
//                    tv.setText("토클 버튼 상태 : 꺼짐");
                    //tb_tue.setEnabled(true);
                }
            }
        });

        tb_sun.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(tb_sun.isChecked())
                {
                    tb_sun.setEnabled(false);
                    tb_mon.setChecked(false);
                    tb_mon.setEnabled(true);
                    tb_tue.setChecked(false);
                    tb_tue.setEnabled(true);
                    tb_wed.setChecked(false);
                    tb_wed.setEnabled(true);
                    tb_thu.setChecked(false);
                    tb_thu.setEnabled(true);
                    tb_fri.setChecked(false);
                    tb_fri.setEnabled(true);
                    tb_sat.setChecked(false);
                    tb_sat.setEnabled(true);
                }

                else{                               //토글이 꺼져있을 때 ==> 비활성화 시킬꺼라 구현 필요 X
//                    tv.setText("토클 버튼 상태 : 꺼짐");
                    //tb_tue.setEnabled(true);
                }
            }
        });


        return rootView;
    }

}
