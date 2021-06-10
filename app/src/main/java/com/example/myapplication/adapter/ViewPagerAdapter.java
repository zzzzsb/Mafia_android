package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.R;

public class ViewPagerAdapter extends PagerAdapter {

    LayoutInflater inflater;
    int numOfPage;

    public ViewPagerAdapter(LayoutInflater inflater, int numOfPage) {
        // LayoutInflater 를 초기화
        this.inflater=inflater;
        // 프래그먼트들의 갯수를 설정
        this.numOfPage = numOfPage;
    }

    // PagerAdapter 의 개수를 리턴하는 함수
    @Override
    public int getCount() {
        // 프래그먼트들의 개수를 return 한다.
        return numOfPage;
    }

    //ViewPager가 현재 보여질 Item(View객체)를 생성할 필요가 있는 때 자동으로 호출
    //쉽게 말해, 스크롤을 통해 현재 보여져야 하는 View를 만들어냄.
    //첫번째 파라미터 : ViewPager
    //두번째 파라미터 : ViewPager가 보여줄 View의 위치(가장 처음부터 0,1,2,3...)
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=null;//현재 position에서 보여줘야할 View를 생성해서 리턴...

        //새로운 View 객체를 Layoutinflater를 이용해서 생성
        //position마다 다른 View를 생성
        switch( position ){
            case 0: //첫번째 Tab을 선택했을때 보여질 뷰
                view= inflater.inflate(R.layout.fragment_main_chat, null);
                break;
            case 1: //두번째 Tab을 선택했을때 보여질 뷰
                view= inflater.inflate(R.layout.fragment_mafia_chat, null);
                break;
            case 2: //세번째 Tab을 선택했을때 보여질 뷰
                view= inflater.inflate(R.layout.fragment_police_chat, null);
                break;
            case 3: //네번째 Tab을 선택했을때 보여질 뷰
                view= inflater.inflate(R.layout.fragment_docter_chat, null);
                break;
        }
        //ViewPager에 위에서 만들어 낸 View 추가
        if(view != null) container.addView(view);
        //세팅된 View를 리턴
        return view;
    }

    //화면에 보이지 않은 View 를 없앰.
    //첫번째 파라미터 : ViewPager
    //두번째 파라미터 : 파괴될 View의 인덱스(가장 처음부터 0,1,2,3...)
    //세번째 파라미터 : 파괴될 객체(더 이상 보이지 않은 View 객체)
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //ViewPager에서 보이지 않는 View는 제거
        //세번째 파라미터가 View 객체 이지만 데이터 타입이 Object여서 형변환 실시
        container.removeView((View)object);
    }

    //instantiateItem() 메소드에서 리턴된 Ojbect가 View가  맞는지 확인하는 메소드
    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v==obj;
    }
}
