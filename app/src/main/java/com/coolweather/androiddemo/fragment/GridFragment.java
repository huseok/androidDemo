package com.coolweather.androiddemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.coolweather.androiddemo.MainActivity;
import com.coolweather.androiddemo.R;

import java.util.List;
import java.util.Map;

/**
 * @author: seok hzl
 * @date: 2021/12/19
 */
public class GridFragment extends Fragment {
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_grid, container, false);
//        recyclerView.setAdapter(new GridAdapter(this));
        prepareTransitions();
        //这个方法赋予Fragment延迟Fragment动画的能力，直到所有数据被加载。
        postponeEnterTransition();
        return recyclerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scrollToPosition();
    }

    /**
     *
     */
    private void scrollToPosition() {
        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                recyclerView.removeOnLayoutChangeListener(this);
                final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                View viewByPosition = layoutManager.findViewByPosition(MainActivity.currentPosition);
                //滚动到位置，如果当前位置的视图是null(当前不是//布局管理器子节点的一部分)，或者它不是完全可见的。
                if (viewByPosition == null || layoutManager.isViewPartiallyVisible(viewByPosition, false, true)) {
                    //原scrollToPosition 为 定位到指定项，就是把你想显示的项显示出来，但是在屏幕的什么位置是不管的
                    recyclerView.post(() -> layoutManager.scrollToPosition(MainActivity.currentPosition));
                }
            }
        });
    }

    /**
     *
     */
    private void prepareTransitions() {
        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                RecyclerView.ViewHolder selectedViewHolder = recyclerView.findViewHolderForAdapterPosition(MainActivity.currentPosition);
                if (selectedViewHolder ==null){
                    return;
                }
                sharedElements.put(names.get(0),selectedViewHolder.itemView.findViewById(R.id.card_image));

            }
        });
    }
}
