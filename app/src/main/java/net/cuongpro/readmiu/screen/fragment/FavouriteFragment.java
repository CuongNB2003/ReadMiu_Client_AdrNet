package net.cuongpro.readmiu.screen.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.cuongpro.readmiu.R;


public class FavouriteFragment extends Fragment {



    public static FavouriteFragment newInstance(String param1, String param2) {
        FavouriteFragment fragment = new FavouriteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_favourite, container, false);
        anhXa();
        return view;
    }

    private void anhXa() {

    }


}