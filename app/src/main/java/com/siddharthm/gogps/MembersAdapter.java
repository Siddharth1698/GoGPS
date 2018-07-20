package com.siddharthm.gogps;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public  class MembersAdapter {
    ArrayList<CreateUsers> nameList;
    Context context;

    public MembersAdapter(ArrayList<CreateUsers> nameList,Context context) {
        this.nameList = nameList;
        this.context = context;

    }
}
