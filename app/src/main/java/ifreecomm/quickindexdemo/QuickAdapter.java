package ifreecomm.quickindexdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ifreecomm.quickindexdemo.bean.Friend;

/**
 * Created by IT小蔡 on 2018-5-15.
 */

public class QuickAdapter extends BaseAdapter {

    private Context mContext;
    private List<Friend> mData;

    public QuickAdapter(Context context, List<Friend> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.layout_adapter_item, null);
        }
        ViewHolder viewHolder = ViewHolder.getViewHolder(convertView);

        Friend friend = mData.get(position);
        viewHolder.letterTv.setText(friend.getPinyin());
        viewHolder.nameTv.setText(friend.getName());

        if (position > 0) {
            Friend lastFriend = mData.get(position - 1);
            if (lastFriend.getPinyin().equals(friend.getPinyin())) {
                if (viewHolder.letterTv.getVisibility() != View.GONE) {
                    viewHolder.letterTv.setVisibility(View.GONE);
                }
            }else{
                if (viewHolder.letterTv.getVisibility() != View.VISIBLE) {
                    viewHolder.letterTv.setVisibility(View.VISIBLE);
                }
            }
        }else{
            if (viewHolder.letterTv.getVisibility() != View.VISIBLE) {
                viewHolder.letterTv.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }

    static class ViewHolder {
        private TextView letterTv;
        private TextView nameTv;

        public ViewHolder(View convertView) {
            letterTv = (TextView) convertView.findViewById(R.id.letterTv);
            nameTv = (TextView) convertView.findViewById(R.id.nameTv);
        }

        public static ViewHolder getViewHolder(View convertView) {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            if (viewHolder == null) {
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            return viewHolder;
        }


    }
}
