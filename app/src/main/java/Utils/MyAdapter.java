package Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wangxiao.R;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiao on 16/6/7.
 */
public class MyAdapter extends BaseAdapter {
    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public MyAdapter(Context context,List<Map<String, Object>> data){
        this.context=context;
        this.data=data;
        this.layoutInflater= LayoutInflater.from(context);
    }

    public final class Items{
        public ImageView image;
        public TextView title;
        public TextView list_content1;
        public TextView list_content2;
        public TextView list_content3;
        public TextView list_content4;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Items items = null;
        if (convertView == null){
            items = new Items();
            convertView = layoutInflater.inflate(R.layout.gw_list, null);
            items.image = (ImageView) convertView.findViewById(R.id.list_image);
            items.title = (TextView) convertView.findViewById(R.id.list_content);
            items.list_content1 = (TextView) convertView.findViewById(R.id.list_text_1);
            items.list_content2 = (TextView) convertView.findViewById(R.id.list_text_2);
            items.list_content3 = (TextView) convertView.findViewById(R.id.list_text_3);
            items.list_content4 = (TextView) convertView.findViewById(R.id.list_text_4);

            convertView.setTag(items);
        } else {
            items = (Items) convertView.getTag();
        }

        items.image.setBackgroundResource((Integer) data.get(position).get("image"));
        items.title.setText((String) data.get(position).get("TITLE"));
        items.list_content1.setText((String)data.get(position).get("content1"));
        items.list_content2.setText((String)data.get(position).get("content2"));
        items.list_content3.setText((String)data.get(position).get("content3"));
        items.list_content4.setText((String)data.get(position).get("content4"));
        return convertView;
    }
}
