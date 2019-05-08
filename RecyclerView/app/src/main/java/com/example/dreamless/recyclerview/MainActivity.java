package com.example.dreamless.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.provider.CalendarContract;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = this.findViewById(R.id.recyclerView1);

        //单行或单列 参数取决于（setOrientation）
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//        mRecyclerView.setLayoutManager(linearLayoutManager);

        //默认的下划线
//        DividerItemDecoration dec = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        dec.setDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.colorAccent)));
//        mRecyclerView.addItemDecoration(dec);
        //九宫格
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        //自定义的边框
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(this, 3));

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(new RecyclerAdapter(this, getData()));
    }

    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("5");
        data.add("6");
        data.add("7");
        data.add("8");
        data.add("9");
        data.add("10");
        data.add("11");
        data.add("12");
        data.add("13");
        data.add("14");
        data.add("15");
        data.add("16");
        data.add("17");
        data.add("18");
        data.add("19");
        data.add("20");
        data.add("21");
        data.add("22");
        data.add("23");
        data.add("24");
        data.add("25");
        data.add("26");
        data.add("27");
        data.add("28");
        data.add("29");
        data.add("30");
        return data;
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

        private Context _context;
        private ArrayList<String> _data;

        public RecyclerAdapter(Context context, ArrayList<String> data) {
            _context = context;
            _data = data;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(_context).inflate(R.layout.adapter_recycler, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            myViewHolder.mTextView.setText(_data.get(i));
        }

        @Override
        public int getItemCount() {
            return _data.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView mTextView;

            public MyViewHolder(View itemView) {
                super(itemView);
                mTextView = itemView.findViewById(R.id.tv_item);
            }
        }
    }

    /**
     *
     */
    private class RecyclerViewDivider extends RecyclerView.ItemDecoration {

        private Context mContext;
        private Paint mPaint;
        private Drawable mDivider;
        private int mDividerHeight = 2;//分割线高度，默认为1px
        private int mCol;
        private final int[] mAttrs = new int[]{android.R.attr.listDivider};

        public RecyclerViewDivider(Context context, int col) {
            mContext = context;
            mCol = col;
            TypedArray typedArray = mContext.obtainStyledAttributes(mAttrs);
            mDivider = typedArray.getDrawable(0);
            typedArray.recycle();
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 0, 0, mDividerHeight);
        }

        @Override
        public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
            drawHorizontal(c, parent);
            drawVertical(c, parent);
        }


        /**
         * 绘制横向下划线
         *
         * @param canvas
         * @param parent
         */
        private void drawHorizontal(Canvas canvas, RecyclerView parent) {
            int top = parent.getPaddingTop();
            int childCount = parent.getChildCount();
            int left = 0;
            int bottom = 0;
            for (int i = 0; i < childCount; i = i + mCol) {
                View child = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                left = child.getRight() + params.rightMargin;
                bottom = child.getBottom() + params.bottomMargin;
                top = child.getTop() + params.topMargin;
                for (int j = 0; j <= mCol; j++) {
                    int mLeft = left * j;
                    int right = mLeft + mDivider.getIntrinsicHeight();
                    mDivider.setBounds(mLeft, top, right, bottom);
                    mDivider.draw(canvas);
                }
            }
        }

        /**
         * 绘制纵向下划线
         *
         * @param canvas
         * @param parent
         */
        public void drawVertical(Canvas canvas, RecyclerView parent) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            int childCount = parent.getChildCount();
            int top = 0;
            for (int i = 0; i < childCount; i += mCol) {
                View child = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
        }
    }

}

